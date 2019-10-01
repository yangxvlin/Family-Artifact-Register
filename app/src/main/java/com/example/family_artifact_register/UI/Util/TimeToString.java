package com.example.family_artifact_register.UI.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * helper class to get formatted time
 */
public class TimeToString {

    public static String calendarToFormattedString(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = format.format(calendar.getTime());
        return time;
    }

    public static String getCurrentTimeFormattedString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = format.format(Calendar.getInstance().getTime());
        return time;
    }
}
