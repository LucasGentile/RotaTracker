package br.edu.tasima.rotatracker;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import br.edu.tasima.rotatracker.database.StoreToDB;
import br.edu.tasima.rotatracker.model.LocationInfo;

public class CurrentLocationListener implements LocationListener {
    final String _logTag = "Monitor Location";

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

        try {
            new StoreToDB().execute(new LocationInfo(new Random().nextInt(), provider, latitude, longitude, accuracy, time)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }}
