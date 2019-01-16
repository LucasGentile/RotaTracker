package br.edu.tasima.rotatracker;

import android.app.Application;
import android.content.Context;

public class RotaTrackerApp extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        RotaTrackerApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return RotaTrackerApp.context;
    }
}
