package br.edu.tasima.rotatracker.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.tasima.rotatracker.model.LocationInfo;
import br.edu.tasima.rotatracker.database.RotaTrackerDatabaseContract.LocationInfoEntry;

public class DataManager {
    private static DataManager ourInstance = null;

    private List<LocationInfo> mLocations = new ArrayList<>();

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    public static void loadFromDatabase(RotaTrackerOpenHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        final String[] locationColumns = {
                LocationInfoEntry.COLUMN_PROVIDER,
                LocationInfoEntry.COLUMN_LATITUDE,
                LocationInfoEntry.COLUMN_LONGITUDE,
                LocationInfoEntry.COLUMN_ACCURACY,
                LocationInfoEntry.COLUMN_TIME,
                LocationInfoEntry._ID};
        final Cursor locationCursor = db.query(LocationInfoEntry.TABLE_NAME, locationColumns,
                null, null, null, null, LocationInfoEntry.COLUMN_TIME + " DESC");
        loadLocationsFromDatabase(locationCursor);
    }

    private static void loadLocationsFromDatabase(Cursor cursor) {
        int locationProviderPos = cursor.getColumnIndex(LocationInfoEntry.COLUMN_PROVIDER);
        int locationLatitudePos = cursor.getColumnIndex(LocationInfoEntry.COLUMN_LATITUDE);
        int locationLongitudePos = cursor.getColumnIndex(LocationInfoEntry.COLUMN_LONGITUDE);
        int locationAccuracyPos = cursor.getColumnIndex(LocationInfoEntry.COLUMN_ACCURACY);
        int locationTimePos = cursor.getColumnIndex(LocationInfoEntry.COLUMN_TIME);
        int idPos = cursor.getColumnIndex(LocationInfoEntry._ID);

        DataManager dm = getInstance();
        dm.mLocations.clear();
        while (cursor.moveToNext()) {
            String locationProvider = cursor.getString(locationProviderPos);
            double locationLatitude = cursor.getDouble(locationLatitudePos);
            double locationLongitude = cursor.getDouble(locationLongitudePos);
            float locationAccuracy = cursor.getFloat(locationAccuracyPos);
            long locationTime = cursor.getLong(locationTimePos);
            int id = cursor.getInt(idPos);

            LocationInfo location = new LocationInfo(id, locationProvider, locationLatitude, locationLongitude, locationAccuracy, locationTime);
            dm.mLocations.add(location);
        }
        cursor.close();
    }

    public List<LocationInfo> getLocations() {
        return mLocations;
    }
}
