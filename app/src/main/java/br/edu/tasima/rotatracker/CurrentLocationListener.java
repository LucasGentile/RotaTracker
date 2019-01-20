package br.edu.tasima.rotatracker;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import br.edu.tasima.rotatracker.model.LocationInfo;
import br.edu.tasima.rotatracker.model.LocationInfoConverter;

public class CurrentLocationListener implements LocationListener {
    final String _logTag = "Monitor Location";

    public void onLocationChanged(Location location) {
        LocationInfo locationInfo = LocationInfoConverter.convert(location);

        storeCoordinates(locationInfo);

        String logMessage = LogHelper.FormatLocationInfo(locationInfo);

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

    private void storeCoordinates(LocationInfo location) {
        FirebaseFirestore.getInstance().collection("locations")
                .add(location)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(_logTag, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(_logTag, "Error adding document", e);
                    }
                });

    }
}
