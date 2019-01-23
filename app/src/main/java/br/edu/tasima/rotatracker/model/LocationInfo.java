package br.edu.tasima.rotatracker.model;

import br.edu.tasima.rotatracker.LogHelper;

public final class LocationInfo {
    public static String BATTERY_LEVEL = "batteryLevel";
    public static String LATITUDE = "latitude";
    public static String LONGITUDE = "longitude";
    public static String TIME = "time";

    private double latitude;
    private double longitude;
    private long batteryLevel;
    private long time;

    public LocationInfo() {
    }

    public LocationInfo(double latitude, double longitude, long batteryLevel, long time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.batteryLevel = batteryLevel;
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(long batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return LogHelper.FormatLocationInfo(this);
    }

}
