package br.edu.tasima.rotatracker;

import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import br.edu.tasima.rotatracker.database.DatabaseDataWorker;
import br.edu.tasima.rotatracker.database.RotaTrackerOpenHelper;

public class CurrentLocationListener implements LocationListener {
    private RotaTrackerOpenHelper mDbOpenHelper;

    final String _logTag = "Monitor Location";

    public CurrentLocationListener(RotaTrackerOpenHelper mDbOpenHelper) {
        this.mDbOpenHelper = mDbOpenHelper;
    }

    public void onLocationChanged(Location location) {
        String provider = location.getProvider();
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        float accuracy = location.getAccuracy();
        long time = location.getTime();

        storeCoordinates(provider, lat, lng, accuracy, time);

        String logMessage = LogHelper.FormatLocationInfo(provider, lat, lng, accuracy, time);

        Log.d(_logTag, "Monitor Location: " + logMessage);
    }

    public void onStatusChanged(String s, int i, Bundle bundle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onProviderEnabled(String s) {
        Log.d(_logTag, "Monitor Location - Provider Enabled:" + s);
    }

    public void onProviderDisabled(String s) {
        Log.d(_logTag, "Monitor Location - Provider DISabled:" + s);
    }

    private void storeCoordinates(String provider, double latitude, double longitude, float accuracy, long time) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();

        DatabaseDataWorker worker = new DatabaseDataWorker(db);
        worker.insertLocation(provider, latitude, longitude, accuracy, time);
    }
}
