///*
//  Copyright 2017 Google Inc. All Rights Reserved.
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
// */
//
//package br.edu.tasima.rotatracker;
//
//import android.annotation.SuppressLint;
//import android.app.NotificationManager;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.SharedPreferences;
//import android.location.Location;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.BatteryManager;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.IBinder;
//import android.os.PowerManager;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.content.LocalBroadcastManager;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.gcm.GcmNetworkManager;
//import com.google.android.gms.gcm.OneoffTask;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.analytics.FirebaseAnalytics;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
//import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.LinkedList;
//import java.util.Map;
//
//import br.edu.tasima.rotatracker.model.LocationInfo;
//import br.edu.tasima.rotatracker.model.LocationInfoConverter;
//
//public class PassengerMapService extends Service implements LocationListener {
//
//    private static final String TAG = PassengerMapService.class.getSimpleName();
//    public static final String STATUS_INTENT = "status";
//
//    private static final int NOTIFICATION_ID = 1;
//    private static final int FOREGROUND_SERVICE_ID = 1;
//    private static final int CONFIG_CACHE_EXPIRY = 600;  // 10 minutes.
//
//    private GoogleApiClient mGoogleApiClient;
//    private DatabaseReference mFirebaseTransportRef;
//    private FirebaseRemoteConfig mFirebaseRemoteConfig;
//    private LinkedList<LocationInfo> mTransportStatuses = new LinkedList<>();
//    private NotificationManager mNotificationManager;
//    private NotificationCompat.Builder mNotificationBuilder;
//    private PowerManager.WakeLock mWakelock;
//
//    private SharedPreferences mPrefs;
//
//    public PassengerMapService() {
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setDeveloperModeEnabled(BuildConfig.DEBUG)
//                .build();
//        mFirebaseRemoteConfig.setConfigSettings(configSettings);
//        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
//
//        mPrefs = getSharedPreferences(getString(R.string.prefs), MODE_PRIVATE);
//        String email = mPrefs.getString(getString(R.string.email), "");
//        String password = mPrefs.getString(getString(R.string.password), "");
//        authenticateFirebase(email, password);
//
//    }
//
//    @Override
//    public void onDestroy() {
//        // Set activity title to not tracking.
//        setStatusMessage(R.string.not_tracking);
//        // Stop the persistent notification.
//        mNotificationManager.cancel(NOTIFICATION_ID);
//        // Stop receiving location updates.
//        if (mGoogleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
//                    PassengerMapService.this);
//        }
//        // Release the wakelock
//        if (mWakelock != null) {
//            mWakelock.release();
//        }
//        super.onDestroy();
//    }
//
//    private void authenticateFirebase(String email, String password) {
//        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(Task<AuthResult> task) {
//                        Log.i(TAG, "authenticate: " + task.isSuccessful());
//                        if (task.isSuccessful()) {
//                            fetchRemoteConfig();
//                            loadPreviousStatuses();
//                        } else {
//                            Toast.makeText(PassengerMapService.this, R.string.auth_failed,
//                                    Toast.LENGTH_SHORT).show();
//                            stopSelf();
//                        }
//                    }
//                });
//    }
//
//    private void fetchRemoteConfig() {
//        long cacheExpiration = CONFIG_CACHE_EXPIRY;
//        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
//            cacheExpiration = 0;
//        }
//        mFirebaseRemoteConfig.fetch(cacheExpiration)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.i(TAG, "Remote config fetched");
//                        mFirebaseRemoteConfig.activateFetched();
//                    }
//                });
//    }
//
//    /**
//     * Loads previously stored statuses from Firebase, and once retrieved,
//     * start location tracking.
//     */
//    private void loadPreviousStatuses() {
//        String transportId = mPrefs.getString(getString(R.string.rota_id), "");
//        FirebaseAnalytics.getInstance(this).setUserProperty("transportID", transportId);
//        String path = getString(R.string.firebase_path) + transportId;
//        mFirebaseTransportRef = FirebaseDatabase.getInstance().getReference(path);
//        mFirebaseTransportRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                if (snapshot != null) {
//                    for (DataSnapshot transportStatus : snapshot.getChildren()) {
//                        mTransportStatuses.add(Integer.parseInt(transportStatus.getKey()),
//                                LocationInfoConverter.convertMap((Map) transportStatus.getValue()));
//                    }
//                }
//                startLocationTracking();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // TODO: Handle gracefully
//            }
//        });
//    }
//
//    private void logStatusToStorage(LocationInfo transportStatus) {
//        try {
//            File path = new File(Environment.getExternalStoragePublicDirectory(""),
//                    "transport-tracker-log.txt");
//            if (!path.exists()) {
//                path.createNewFile();
//            }
//            FileWriter logFile = new FileWriter(path.getAbsolutePath(), true);
//            logFile.append(transportStatus.toString()).append("\n");
//            logFile.close();
//        } catch (Exception e) {
//            Log.e(TAG, "Log file error", e);
//        }
//    }
//
//    /**
//     * Sets the current status message (connecting/tracking/not tracking).
//     */
//    private void setStatusMessage(int stringId) {
//
//        mNotificationBuilder.setContentText(getString(stringId));
//        mNotificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.build());
//
//        // Also display the status message in the activity.
//        Intent intent = new Intent(STATUS_INTENT);
//        intent.putExtra(getString(R.string.status), stringId);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//    }
//
//}
