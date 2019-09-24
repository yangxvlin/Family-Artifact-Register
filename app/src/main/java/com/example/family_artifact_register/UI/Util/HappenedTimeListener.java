package com.example.family_artifact_register.UI.Util;

import java.util.Calendar;

/**
 * activity and its fragment communicate about the time of the artifact happened
 */
public interface HappenedTimeListener {
    Calendar getHappenedTimeCalender();

    void setHappenedTimeCalender(Calendar calender);
}
