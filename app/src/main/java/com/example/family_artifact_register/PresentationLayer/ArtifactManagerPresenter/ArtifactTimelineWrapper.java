package com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;

import java.util.ArrayList;
import java.util.List;

/**
 * wrapper class for ArtifactTimeline
 */
public class ArtifactTimelineWrapper {

//    private ArtifactTimeline artifactTimeline;
    private String postID;
    private String title;
    private String uploadDateTime;
    private List<ArtifactItemWrapper> artifacts;

    public ArtifactTimelineWrapper(ArtifactTimeline artifactTimeline) {
        postID = artifactTimeline.getPostId();
        title = artifactTimeline.getTitle();
        uploadDateTime = artifactTimeline.getUploadDateTime();
        artifacts = new ArrayList<>();
    }

    public List<ArtifactItemWrapper> getArtifacts() { return artifacts; }

    public String getPostID() {
        return postID;
    }

    public String getTitle() {
        return title;
    }

    public String getUploadDateTime() {
        return uploadDateTime;
    }
}
