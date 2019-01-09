package br.edu.tasima.rotatracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.edu.tasima.rotatracker.database.RotaTrackerDatabaseContract.LocationInfoEntry;

public class RotaTrackerOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "RotaTracker.db";
    public static final int DATABASE_VERSION = 1;

    public RotaTrackerOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LocationInfoEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
