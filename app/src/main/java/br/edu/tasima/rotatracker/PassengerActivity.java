package br.edu.tasima.rotatracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.edu.tasima.rotatracker.model.LocationInfo;
import br.edu.tasima.rotatracker.model.LocationInfoConverter;

public class PassengerActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HashMap<String, Marker> mMarkers = new HashMap<>();

    private SharedPreferences mPrefs;

    private CardView mAuthCard;

    private Button mStartButton;
    private EditText mTransportIdEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private DatabaseReference mFirebaseTransportRef;
    private static final int CONFIG_CACHE_EXPIRY = 600;  // 10 minutes.
    private static final String TAG = PassengerActivity.class.getSimpleName();


    /**
     * Configures UI elements, and starts validation if inputs have previously been entered.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

        mStartButton = findViewById(R.id.button_start);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkInputFields();
            }
        });

        mTransportIdEditText = findViewById(R.id.transport_id);
        mEmailEditText = findViewById(R.id.email);
        mPasswordEditText = findViewById(R.id.password);

        mPrefs = getSharedPreferences(getString(R.string.prefs), MODE_PRIVATE);
        String transportID = mPrefs.getString(getString(R.string.rota_id), "");
        String email = mPrefs.getString(getString(R.string.email), "");
        String password = mPrefs.getString(getString(R.string.password), "");

        mTransportIdEditText.setText(transportID);
        mEmailEditText.setText(email);
        mPasswordEditText.setText(password);

    }

    /**
     * First validation check - ensures that required inputs have been
     * entered, and if so, store them and runs the next check.
     */
    private void checkInputFields() {
        if (mTransportIdEditText.length() == 0 || mEmailEditText.length() == 0 ||
                mPasswordEditText.length() == 0) {
            Toast.makeText(PassengerActivity.this, R.string.missing_inputs, Toast.LENGTH_SHORT).show();
        } else {
            // Store values.
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(getString(R.string.rota_id), mTransportIdEditText.getText().toString());
            editor.putString(getString(R.string.email), mEmailEditText.getText().toString());
            editor.putString(getString(R.string.password), mPasswordEditText.getText().toString());
            editor.apply();
            authenticateFirebase(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
        }
    }

    private void authenticateFirebase(String email, String password) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        Log.i(TAG, "authenticate: " + task.isSuccessful());
                        if (task.isSuccessful()) {
                            fetchRemoteConfig();
                            Toast.makeText(PassengerActivity.this, R.string.auth_success,
                                    Toast.LENGTH_SHORT).show();
                            loadMap();

                        } else {
                            Toast.makeText(PassengerActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loadMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_passenger_maps);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        mAuthCard = findViewById(R.id.auth_driver);
        mAuthCard.setVisibility(View.GONE);

        subscribeToUpdates();
    }

    private void fetchRemoteConfig() {
        long cacheExpiration = CONFIG_CACHE_EXPIRY;
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "Remote config fetched");
                        mFirebaseRemoteConfig.activateFetched();
                    }
                });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMaxZoomPreference(16);
    }

    private void subscribeToUpdates() {
        String transportId = mPrefs.getString(getString(R.string.rota_id), "");
        FirebaseAnalytics.getInstance(this).setUserProperty("transportID", transportId);
        String path = getString(R.string.firebase_path) + transportId;
        mFirebaseTransportRef = FirebaseDatabase.getInstance().getReference(path);
        mFirebaseTransportRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setMarker(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setMarker(DataSnapshot dataSnapshot) {
        // When a location update is received, put or update
        // its value in mMarkers, which contains all the markers
        // for locations received, so that we can build the
        // boundaries required to show them all on the map at once
        String key = dataSnapshot.getKey();
        ArrayList dataSnapshots = (ArrayList) dataSnapshot.getValue();
        LocationInfo locationInfo = LocationInfoConverter.convertMap((Map) dataSnapshots.get(0));
        LatLng coordinates = new LatLng(locationInfo.getLatitude(), locationInfo.getLongitude());
        if (!mMarkers.containsKey(key)) {
            mMarkers.put(key, mMap.addMarker(
                    new MarkerOptions()
                            .title(key)
                            .position(coordinates)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_round_marker)))
            );
        } else {
            mMarkers.get(key).setPosition(coordinates);
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : mMarkers.values()) {
            builder.include(marker.getPosition());
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 300));
    }
}
