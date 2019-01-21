//package br.edu.tasima.rotatracker;
//
//import android.annotation.SuppressLint;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.ebanx.swipebtn.OnActiveListener;
//import com.ebanx.swipebtn.SwipeButton;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//
//public class DriverActivity extends FragmentActivity implements OnMapReadyCallback {
//
//    final String _logTag = "Monitor Location";
//
//    private final LatLng mDefaultLocation = new LatLng(-30.0067164, -51.317259);
//    private static final int DEFAULT_ZOOM = 15;
//    private static final int REQUEST_PERMISSION_FINE_LOCATION = 1;
//    private boolean mLocationPermissionGranted;
//
//    private GoogleMap mMap;
//    private SwipeButton startRouteSwipe;
//
//    private FusedLocationProviderClient mFusedLocationProviderClient;
//    private Location mLastKnownLocation;
//
//    private LocationListener _networkListener;
//    private LocationListener _gpsListener;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_driver);
//
//        // Construct a FusedLocationProviderClient.
//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.fragment_driver_maps);
//
//        mapFragment.getMapAsync(this);
//        mapFragment.getView().setVisibility(View.GONE);
//
//        loadSwipeButton(mapFragment);
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        getLocationPermission();
//
//        getDeviceLocation();
//    }
//
//    private void loadSwipeButton(final SupportMapFragment mapFragment) {
//        startRouteSwipe = findViewById(R.id.startRoute);
//        startRouteSwipe.setOnActiveListener(new OnActiveListener() {
//            @Override
//            public void onActive() {
//                Toast.makeText(RotaTrackerApp.getAppContext(), "Enjoy your ride!", Toast.LENGTH_LONG).show();
//                mapFragment.getView().setVisibility(View.VISIBLE);
//                new Handler().postDelayed(
//                        new Runnable() {
//                            public void run() {
//                                startRouteSwipe.setVisibility(View.GONE);
//                                startListeningLocation();
//                            }
//                        },
//                        400);
//            }
//        });
//    }
//
//    private void getDeviceLocation() {
//        /*
//         * Get the best and most recent location of the device, which may be null in rare
//         * cases when a location is not available.
//         */
//        Log.d(_logTag, "Monitor - Current Location");
//        try {
//            if (mLocationPermissionGranted) {
//                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
//                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        if (task.isSuccessful()) {
//                            // Set the map's camera position to the current location of the device.
//                            mLastKnownLocation = task.getResult();
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                    new LatLng(mLastKnownLocation.getLatitude(),
//                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
//                        } else {
//                            Log.d(_logTag, "Current location is null. Using defaults.");
//                            Log.e(_logTag, "Exception: %s", task.getException());
//                            mMap.moveCamera(CameraUpdateFactory
//                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
//                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
//                        }
//                    }
//                });
//            }
//        } catch (SecurityException e) {
//            Log.e("Exception: %s", e.getMessage());
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    public void startListeningLocation() {
//        Log.d(_logTag, "Monitor Location - Start Listening");
//
//        try {
//            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//            getLocationPermission();
//
//            if(mLocationPermissionGranted){
//                _networkListener = new CurrentLocationListener();
//                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, _networkListener);
//
//                _gpsListener = new CurrentLocationListener();
//                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, _gpsListener);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void getLocationPermission() {
//
//        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            mLocationPermissionGranted = true;
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_PERMISSION_FINE_LOCATION);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String permissions[],
//                                           @NonNull int[] grantResults) {
//        mLocationPermissionGranted = false;
//        switch (requestCode) {
//            case REQUEST_PERMISSION_FINE_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    mLocationPermissionGranted = true;
//                }
//            }
//        }
//    }
////
////    /**
////     * Manipulates the map once available.
////     * This callback is triggered when the map is ready to be used.
////     * This is where we can add markers or lines, add listeners or move the camera. In this case,
////     * we just add a marker near Sydney, Australia.
////     * If Google Play services is not installed on the device, the user will be prompted to install
////     * it inside the SupportMapFragment. This method will only be triggered once the user has
////     * installed Google Play services and returned to the app.
////     */
////    @Override
////    public void onMapReady(GoogleMap googleMap) {
////        mMap = googleMap;
////        List<LocationInfo> locations = new ArrayList<>();
////        try {
////            locations = new ReadFromDB().execute().get();
////        } catch (ExecutionException | InterruptedException e) {
////            e.printStackTrace();
////        }
////
////        LinkedList<LatLng> coordinates = new LinkedList<>();
////
////        for (LocationInfo location : locations){
////            coordinates.add(new LatLng(location.getLatitude(), location.getLongitude()));
////        }
////
////        mMap.addPolyline((new PolylineOptions())
////                .clickable(true)
////                .addAll(coordinates));
////
////        mMap.addMarker(new MarkerOptions().position(coordinates.getFirst()).title("Begin"));
////        mMap.addMarker(new MarkerOptions().position(coordinates.getLast()).title("Finish"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates.getFirst()));
////    }
//}
