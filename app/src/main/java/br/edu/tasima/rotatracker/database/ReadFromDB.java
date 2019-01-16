package br.edu.tasima.rotatracker.database;

import android.os.AsyncTask;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.SelectRequest;

import java.util.ArrayList;
import java.util.List;

import br.edu.tasima.rotatracker.database.RotaTrackerDatabaseContract.LocationInfoEntry;
import br.edu.tasima.rotatracker.model.LocationInfo;

public class ReadFromDB extends AsyncTask<Void, Void, List<LocationInfo>> {

    public static List<LocationInfo> getAllLocations() throws Exception {
        SelectRequest selectRequest = new SelectRequest("select * from location_info").withConsistentRead(true);

        List<Item> items = SimpleDBConnection.getAwsSimpleDB().select(selectRequest).getItems();

        try {
            List<LocationInfo> locations = new ArrayList<>();

            for (Item item : items) {

                List<Attribute> tempAttributes = item.getAttributes();
                LocationInfo locationInfo = new LocationInfo();
                for (Attribute attribute : tempAttributes) {
                    switch (attribute.getName()) {
                        case LocationInfoEntry.COLUMN_PROVIDER:
                            locationInfo.setProvider(attribute.getValue());
                            break;
                        case LocationInfoEntry.COLUMN_LATITUDE:
                            locationInfo.setLatitude(Double.valueOf(attribute.getValue()));
                            break;
                        case LocationInfoEntry.COLUMN_LONGITUDE:
                            locationInfo.setLongitude(Double.valueOf(attribute.getValue()));
                            break;
                        case LocationInfoEntry.COLUMN_ACCURACY:
                            locationInfo.setAccuracy(Float.valueOf(attribute.getValue()));
                            break;
                        case LocationInfoEntry.COLUMN_TIME:
                            locationInfo.setTime(Long.valueOf(attribute.getValue()));
                            break;
                    }
                }

                locations.add(locationInfo);
            }
            return locations;
        } catch (Exception eex) {
            throw new Exception("FIRST EXCEPTION", eex);
        }
    }

    @Override
    protected List<LocationInfo> doInBackground(Void... params) {
        // TODO Auto-generated method stub
        try {
            return getAllLocations();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}