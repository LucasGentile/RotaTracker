package br.edu.tasima.rotatracker;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class PassengerActivity extends FragmentActivity implements OnMapReadyCallback {

    final String _logTag = "Monitor Location";
//    final int REQUEST_PERMISSION_FINE_LOCATION = 1;

    private GoogleMap mMap;
    //
//    LocationListener _networkListener;
//    LocationListener _gpsListener;

    //
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.fragment_driver_maps);
//        if (mapFragment != null) {
//            mapFragment.getMapAsync(this);
//        }

//        if (Activity.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_PERMISSION_FINE_LOCATION);
//        }


    }

    //
//    private void setupButtons() {
//        fab = findViewById(R.id.fab);
//        fab1 = findViewById(R.id.fab1);
//        fab2 = findViewById(R.id.fab2);
//        fab3 = findViewById(R.id.fab3);
//        fab4 = findViewById(R.id.fab4);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!isFABOpen) {
//                    showFABMenu();
//                } else {
//                    closeFABMenu();
//                }
//            }
//        });
//    }
//
//    private void showFABMenu() {
//        isFABOpen = true;
//        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
//        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
//        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
//        fab4.animate().translationY(-getResources().getDimension(R.dimen.standard_205));
//    }
//
//    private void closeFABMenu() {
//        isFABOpen = false;
//        fab1.animate().translationY(0);
//        fab2.animate().translationY(0);
//        fab3.animate().translationY(0);
//        fab4.animate().translationY(0);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (!isFABOpen) {
//            super.onBackPressed();
//        } else {
//            closeFABMenu();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.onExit) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void onStartListening(View item) {
//        Log.d(_logTag, "Monitor Location - Start Listening");
//
//        try {
//            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        REQUEST_PERMISSION_FINE_LOCATION);
//            } else {
//                _networkListener = new CurrentLocationListener();
//                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, _networkListener);
//
//                _gpsListener = new CurrentLocationListener();
//                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, _gpsListener);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void onStopListening(View item) {
//        Log.d(_logTag, "Monitor Location - Stop Listening");
//
//        doStopListening();
//
//        finish();
//    }
//
//    public void onRecentLocation(View item) {
//        Log.d(_logTag, "Monitor - Recent Location");
//
//        Location networkLocation;
//
//        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_PERMISSION_FINE_LOCATION);
//        } else {
//            networkLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            String networkLogMessage = LogHelper.FormatLocationInfo(networkLocation);
//            Log.d(_logTag, "Monitor Location: " + networkLogMessage);
//        }
//    }
//
//    public void onSingleLocation(View item) {
//        Log.d(_logTag, "Monitor - Single Location");
//
//        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_PERMISSION_FINE_LOCATION);
//        } else {
//            _networkListener = new CurrentLocationListener();
//            lm.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, _networkListener, null);
//        }
//    }
//
//    public void onExit(MenuItem item) {
//        Log.d(_logTag, "Monitor Location Exit");
//
//        doStopListening();
//
//        finish();
//    }
//
//    void doStopListening() {
//        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        if (_networkListener != null) {
//            lm.removeUpdates(_networkListener);
//            _networkListener = null;
//        }
//
//        if (_gpsListener != null) {
//            lm.removeUpdates(_gpsListener);
//            _gpsListener = null;
//        }
//    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-50, 51);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        List<LocationInfo> locations = new ArrayList<>();
//        try {
//            locations = new ReadFromDB().execute().get();
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        LinkedList<LatLng> coordinates = new LinkedList<>();
//
//        for (LocationInfo location : locations){
//            coordinates.add(new LatLng(location.getLatitude(), location.getLongitude()));
//        }
//
//        mMap.addPolyline((new PolylineOptions())
//                .clickable(true)
//                .addAll(coordinates));
//
//        mMap.addMarker(new MarkerOptions().position(coordinates.getFirst()).title("Begin"));
//        mMap.addMarker(new MarkerOptions().position(coordinates.getLast()).title("Finish"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates.getFirst()));
//    }
}
