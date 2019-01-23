package br.edu.tasima.rotatracker.model;

import java.util.Map;

public class LocationInfoConverter {
    public static LocationInfo convertMap(Map locationHashMap){
        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setBatteryLevel((long) locationHashMap.get(LocationInfo.BATTERY_LEVEL));
        locationInfo.setLatitude((double) locationHashMap.get(LocationInfo.LATITUDE));
        locationInfo.setLongitude((double) locationHashMap.get(LocationInfo.LONGITUDE));
        locationInfo.setTime((long) locationHashMap.get(LocationInfo.TIME));

        return locationInfo;
    }
}
