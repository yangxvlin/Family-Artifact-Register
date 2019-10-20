package com.unimelb.family_artifact_register.UI.Util;

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;

import java.util.List;

public interface NewTimelineListener {

    int NEW_ARTIFACT_TIMELINE = 0;

    int EXISTING_ARTIFACT_TIMELINE = 1;

    void setTimeline(int type, String timelineTitle);

    int getTimelineType();

    String getTimelineTitle();

    List<ArtifactTimeline> getArtifactTimelines();

    void setSelectedTimeline(ArtifactTimeline selectedTimeline);
}
