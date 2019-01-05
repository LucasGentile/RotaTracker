package br.edu.tasima.rotatracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.edu.tasima.rotatracker.database.RotaTrackerOpenHelper;

public class MainActivity extends AppCompatActivity {

    final String _logTag = "Monitor Location";
    final int REQUEST_PERMISSION_FINE_LOCATION = 1;


    FloatingActionButton fab, fab1, fab2, fab3, fab4;
    boolean isFABOpen = false;

    LocationListener _networkListener;
    LocationListener _gpsListener;

    RotaTrackerOpenHelper mDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDbOpenHelper = new RotaTrackerOpenHelper(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION_FINE_LOCATION);
        }

        fab = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        fab3 = findViewById(R.id.fab3);
        fab4 = findViewById(R.id.fab4);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        displayLocationCoordinates();
    }


    private void displayLocationCoordinates() {
        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
    }

    private void showFABMenu() {
        isFABOpen = true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        fab4.animate().translationY(-getResources().getDimension(R.dimen.standard_205));
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
        fab4.animate().translationY(0);
    }

    @Override
    public void onBackPressed() {
        if (!isFABOpen) {
            super.onBackPressed();
        } else {
            closeFABMenu();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.onExit) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onStartListening(View item) {
        Log.d(_logTag, "Monitor Location - Start Listening");

        try {
            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSION_FINE_LOCATION);
            } else {
                _networkListener = new CurrentLocationListener(mDbOpenHelper);
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, _networkListener);

                _gpsListener = new CurrentLocationListener(mDbOpenHelper);
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, _gpsListener);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onStopListening(View item) {
        Log.d(_logTag, "Monitor Location - Stop Listening");

        doStopListening();

        finish();
    }

    public void onRecentLocation(View item) {
        Log.d(_logTag, "Monitor - Recent Location");

        Location networkLocation;

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION_FINE_LOCATION);
        } else {
            networkLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            String networkLogMessage = LogHelper.FormatLocationInfo(networkLocation);
            Log.d(_logTag, "Monitor Location: " + networkLogMessage);
        }
    }

    public void onSingleLocation(View item) {
        Log.d(_logTag, "Monitor - Single Location");

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION_FINE_LOCATION);
        } else {
            _networkListener = new CurrentLocationListener(mDbOpenHelper);
            lm.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, _networkListener, null);
        }
    }

    public void onExit(MenuItem item) {
        Log.d(_logTag, "Monitor Location Exit");

        doStopListening();

        finish();
    }

    void doStopListening() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (_networkListener != null) {
            lm.removeUpdates(_networkListener);
            _networkListener = null;
        }

        if (_gpsListener != null) {
            lm.removeUpdates(_gpsListener);
            _gpsListener = null;
        }
    }

    @Override
    protected void onDestroy() {
        mDbOpenHelper.close();
        super.onDestroy();
    }
}
