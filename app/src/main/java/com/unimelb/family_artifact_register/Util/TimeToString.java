package com.unimelb.family_artifact_register.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * helper class to get formatted time by pure fabricate design pattern
 */
public class TimeToString {

    /**
     * default time string format
     */
    public static final SimpleDateFormat standardDateFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * @param calendar calendar object
     * @return string formatted calendar time
     */
    public static String calendarToFormattedString(Calendar calendar) {
        SimpleDateFormat format = standardDateFormat;
        String time = format.format(calendar.getTime());
        return time;
    }

    /**
     * @return string formatted time stamp
     */
    public static String getCurrentTimeFormattedString() {
        String time = standardDateFormat.format(Calendar.getInstance().getTime());
        return time;
    }

    /**
     * @return string formatted time stamp without space
     */
    public static String getCurrentTimeFormattedStringForFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        String time = format.format(Calendar.getInstance().getTime());
        return time;
    }
}
