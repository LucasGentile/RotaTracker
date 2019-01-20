package br.edu.tasima.rotatracker.model;


import android.location.Location;
import android.support.annotation.NonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class LocationInfoConverter {

    public static LocationInfo convert(@NonNull Location location) {
        return new LocationInfo(location.getProvider(), location.getLatitude(), location.getLongitude(), location.getAccuracy(), getLocalDateTime(location.getTime()));
    }

    private static LocalDateTime getLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                TimeZone.getDefault().toZoneId());
    }
}
