package br.edu.tasima.rotatracker;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import br.edu.tasima.rotatracker.model.LocationInfo;

public class LogHelper {
    static final String _timeStampFormat = "yyyy-MM-dd'T'HH:mm:ss";
    static final String _timeStampTimeZoneId = "UTC";

    @SuppressLint("DefaultLocale")
    public static String FormatLocationInfo(double lat, double lng, int power, long time) {
        return String.format("lat/lng=%f/%f | power=%d | time=%s",
                lat, lng, power, formatTimeStamp(time));
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

        double lat = location.getLat();
        double lng = location.getLng();
        int power = location.getPower();
        LocalDateTime localDateTime = location.getTime();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of(_timeStampTimeZoneId));
        return LogHelper.FormatLocationInfo(lat, lng, power, zonedDateTime.toInstant().getEpochSecond());
    }

}
