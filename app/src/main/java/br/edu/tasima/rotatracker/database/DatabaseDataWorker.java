package br.edu.tasima.rotatracker.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import br.edu.tasima.rotatracker.database.RotaTrackerDatabaseContract.LocationInfoEntry;

public class DatabaseDataWorker {
    private SQLiteDatabase mDb;

    public DatabaseDataWorker(SQLiteDatabase db) {
        mDb = db;
    }

    public void insertLocation(String provider, double latitude, double longitude, float accuracy, long time) {
        ContentValues values = new ContentValues();
        values.put(LocationInfoEntry.COLUMN_PROVIDER, provider);
        values.put(LocationInfoEntry.COLUMN_LATITUDE, latitude);
        values.put(LocationInfoEntry.COLUMN_LONGITUDE, longitude);
        values.put(LocationInfoEntry.COLUMN_ACCURACY, accuracy);
        values.put(LocationInfoEntry.COLUMN_TIME, time);

        long newRowId = mDb.insert(LocationInfoEntry.TABLE_NAME, null, values);
    }
}
