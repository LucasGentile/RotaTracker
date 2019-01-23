package br.edu.tasima.rotatracker;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import br.edu.tasima.rotatracker.model.LocationInfo;

public class LogHelper {
    static final String _timeStampFormat = "yyyy-MM-dd'T'HH:mm:ss";
    static final String _timeStampTimeZoneId = "UTC";

    @SuppressLint("DefaultLocale")
    public static String FormatLocationInfo(double latitude, double longitude, float batteryLevel, long time) {
        return String.format("lat/lng=%f/%f | power=%f | time=%s",
                latitude, longitude, batteryLevel, formatTimeStamp(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatTimeStamp(long time) {
        SimpleDateFormat timeStampFormatter = new SimpleDateFormat(_timeStampFormat);
        timeStampFormatter.setTimeZone(TimeZone.getTimeZone(_timeStampTimeZoneId));

        return timeStampFormatter.format(time);
    }

    public static String FormatLocationInfo(LocationInfo location) {
        if (location == null)
            return "<NULL Location Value>";

        return LogHelper.FormatLocationInfo(location.getLatitude(), location.getLongitude(), location.getBatteryLevel(), location.getTime());
    }

}
