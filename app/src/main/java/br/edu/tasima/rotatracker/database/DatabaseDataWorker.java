package br.edu.tasima.rotatracker.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseDataWorker {
    private SQLiteDatabase mDb;

    public DatabaseDataWorker(SQLiteDatabase db) {
        mDb = db;
    }

    public void insertLocations() {
//        insertLocation("android_intents", "Android Programming with Intents");
//        insertLocation("android_async", "Android Async Programming and Services");
//        insertLocation("java_lang", "Java Fundamentals: The Java Language");
//        insertLocation("java_core", "Java Fundamentals: The Core Platform");
    }

    private void insertLocation(String provider, double latitude, double longitude, float accuracy, String time) {
        ContentValues values = new ContentValues();
        values.put(RotaTrackerDatabaseContract.LocationInfoEntry.COLUMN_PROVIDER, provider);
        values.put(RotaTrackerDatabaseContract.LocationInfoEntry.COLUMN_LATITUDE, latitude);
        values.put(RotaTrackerDatabaseContract.LocationInfoEntry.COLUMN_LONGITUDE, longitude);
        values.put(RotaTrackerDatabaseContract.LocationInfoEntry.COLUMN_ACCURACY, accuracy);
        values.put(RotaTrackerDatabaseContract.LocationInfoEntry.COLUMN_TIME, time);

        long newRowId = mDb.insert(RotaTrackerDatabaseContract.LocationInfoEntry.TABLE_NAME, null, values);
    }
}
