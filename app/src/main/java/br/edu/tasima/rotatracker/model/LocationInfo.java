package br.edu.tasima.rotatracker.model;

import java.time.LocalDateTime;

import br.edu.tasima.rotatracker.LogHelper;

public final class LocationInfo {
    private double lat;
    private double lng;
    private int power;
    private LocalDateTime time;

    public LocationInfo() {
    }

    public LocationInfo(double lat, double lng, int power, LocalDateTime time) {
        this.lat = lat;
        this.lng = lng;
        this.power = power;
        this.time = time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return LogHelper.FormatLocationInfo(this);
    }

}
