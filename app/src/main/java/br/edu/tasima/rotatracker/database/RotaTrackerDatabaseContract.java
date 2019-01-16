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

    }
}












