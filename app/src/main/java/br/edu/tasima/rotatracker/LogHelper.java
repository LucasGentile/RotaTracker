package br.edu.tasima.rotatracker;

import android.annotation.SuppressLint;
import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class LogHelper {
    static final String _timeStampFormat = "yyyy-MM-dd'T'HH:mm:ss";
    static final String _timeStampTimeZoneId = "UTC";

    @SuppressLint("DefaultLocale")
    public static String FormatLocationInfo(String provider, double lat, double lng, float accuracy, long time) {
        return String.format("%s | lat/lng=%f/%f | accuracy=%f | Time=%s",
                provider, lat, lng, accuracy, formatTimeStamp(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatTimeStamp(long time) {
        SimpleDateFormat timeStampFormatter = new SimpleDateFormat(_timeStampFormat);
        timeStampFormatter.setTimeZone(TimeZone.getTimeZone(_timeStampTimeZoneId));

        return timeStampFormatter.format(time);
    }

    public static String FormatLocationInfo(Location location) {

        if (location == null)
            return "<NULL Location Value>";

        String provider = location.getProvider();
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        float accuracy = location.getAccuracy();
        long time = location.getTime();
        return LogHelper.FormatLocationInfo(provider, lat, lng, accuracy, time);
    }

}
