package br.edu.tasima.rotatracker.database;

import android.provider.BaseColumns;

public final class RotaTrackerDatabaseContract {
    private RotaTrackerDatabaseContract() {
    }

    public static final class LocationInfoEntry implements BaseColumns {
        public static final String TABLE_NAME = "location_info";
        public static final String COLUMN_PROVIDER = "provider";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_ACCURACY = "accuracy";
        public static final String COLUMN_TIME = "time";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_PROVIDER + " TEXT NOT NULL, " +
                        COLUMN_LATITUDE + " REAL NOT NULL," +
                        COLUMN_LONGITUDE + " REAL NOT NULL" +
                        COLUMN_ACCURACY + " REAL NOT NULL" +
                        COLUMN_TIME + " TEXT NOT NULL)";
    }
}












