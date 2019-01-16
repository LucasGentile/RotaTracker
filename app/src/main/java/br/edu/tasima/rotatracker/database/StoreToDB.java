package br.edu.tasima.rotatracker.database;

import android.os.AsyncTask;

import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import java.util.ArrayList;
import java.util.List;

import br.edu.tasima.rotatracker.database.RotaTrackerDatabaseContract.LocationInfoEntry;
import br.edu.tasima.rotatracker.model.LocationInfo;

public class StoreToDB extends AsyncTask<LocationInfo, Void, Void> {
    public static void saveLocation(int mId, String mProvider, double mLatitude, double mLongitude, float mAccuracy, long mTime) {
        try {

            SimpleDBConnection.getAwsSimpleDB().createDomain(new CreateDomainRequest(LocationInfoEntry.TABLE_NAME));
            List<ReplaceableAttribute> attribute = new ArrayList<>(1);
            attribute.add(new ReplaceableAttribute().withName(LocationInfoEntry.COLUMN_PROVIDER).withValue(mProvider));
            attribute.add(new ReplaceableAttribute().withName(LocationInfoEntry.COLUMN_LATITUDE).withValue(Double.toString(mLatitude)));
            attribute.add(new ReplaceableAttribute().withName(LocationInfoEntry.COLUMN_LONGITUDE).withValue(Double.toString(mLongitude)));
            attribute.add(new ReplaceableAttribute().withName(LocationInfoEntry.COLUMN_ACCURACY).withValue(Float.toString(mAccuracy)));
            attribute.add(new ReplaceableAttribute().withName(LocationInfoEntry.COLUMN_TIME).withValue(Long.toString(mTime)));
            SimpleDBConnection.awsSimpleDB.putAttributes(new PutAttributesRequest(LocationInfoEntry.TABLE_NAME, Integer.toString(mId), attribute));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected Void doInBackground(LocationInfo... locationInfoList) {
        for (LocationInfo locationInfo : locationInfoList) {
            saveLocation(locationInfo.getId(), locationInfo.getProvider(), locationInfo.getLatitude(), locationInfo.getLongitude(), locationInfo.getAccuracy(), locationInfo.getTime());
        }
        return null;
    }
}
