package com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener;

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;

import java.util.List;

public interface NewTimelineListener {
    /**
     * associate with new artifact timeline
     */
    int NEW_ARTIFACT_TIMELINE = 0;

    /**
     * associate with existing new artifact timeline
     */
    int EXISTING_ARTIFACT_TIMELINE = 1;

    /**
     * @param type          new or existing timeline
     * @param timelineTitle timeline's title
     */
    void setTimeline(int type, String timelineTitle);

    /**
     * @return NEW_ARTIFACT_TIMELINE or EXISTING_ARTIFACT_TIMELINE
     */
    int getTimelineType();

    /**
     * @return artifact timeline title
     */
    String getTimelineTitle();

    /**
     * @return artifact timelines
     */
    List<ArtifactTimeline> getArtifactTimelines();

    /**
     * @param selectedTimeline user choice of existing timeline
     */
    void setSelectedTimeline(ArtifactTimeline selectedTimeline);
}
