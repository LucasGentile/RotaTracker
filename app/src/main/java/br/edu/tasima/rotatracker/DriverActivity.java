package br.edu.tasima.rotatracker;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.ebanx.swipebtn.OnActiveListener;
import com.ebanx.swipebtn.SwipeButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DriverActivity extends FragmentActivity implements OnMapReadyCallback {

    final String _logTag = "Monitor Location";
//    final int REQUEST_PERMISSION_FINE_LOCATION = 1;

    private GoogleMap mMap;
    private SwipeButton startRouteSwipe;
    //
//    LocationListener _networkListener;
//    LocationListener _gpsListener;

    //
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_driver_maps);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        startRouteSwipe = findViewById(R.id.startRoute);
        startRouteSwipe.setOnActiveListener(new OnActiveListener() {
            @Override
            public void onActive() {
                Toast.makeText(RotaTrackerApp.getAppContext(), "Enjoy your ride!", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                startRouteSwipe.setVisibility(View.GONE);
                            }
                        },
                        400);
            }
        });

//        if (Activity.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_PERMISSION_FINE_LOCATION);
//        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
