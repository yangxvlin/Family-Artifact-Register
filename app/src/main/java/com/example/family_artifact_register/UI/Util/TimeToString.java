package com.example.family_artifact_register.UI.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * helper class to get formatted time
 */
public class TimeToString {

    public static final SimpleDateFormat standardDateFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String calendarToFormattedString(Calendar calendar) {
        SimpleDateFormat format = standardDateFormat;
        String time = format.format(calendar.getTime());
        return time;
    }

    public static String getCurrentTimeFormattedString() {
        String time = standardDateFormat.format(Calendar.getInstance().getTime());
        return time;
    }

    public static String getCurrentTimeFormattedStringForFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        String time = format.format(Calendar.getInstance().getTime());
        return time;
    }
}
