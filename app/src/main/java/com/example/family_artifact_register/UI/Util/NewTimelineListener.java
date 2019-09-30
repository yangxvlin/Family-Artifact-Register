package com.example.family_artifact_register.UI.Util;

public interface NewTimelineListener {

    int NEW_ARTIFACT_TIMELINE = 0;

    int EXISTING_ARTIFACT_TIMELINE = 1;

    void setTimeline(int type, String timelineTitle);

    int getTimelineType();

    String getTimelineTitle();
}
