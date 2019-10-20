package com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;

import java.util.ArrayList;
import java.util.Comparator;
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

    private Comparator<ArtifactItemWrapper> wrapperComparator;

    public ArtifactTimelineWrapper(ArtifactTimeline artifactTimeline) {
        postID = artifactTimeline.getPostId();
        title = artifactTimeline.getTitle();
        uploadDateTime = artifactTimeline.getUploadDateTime();
        artifacts = new ArrayList<>();
        wrapperComparator = new Comparator<ArtifactItemWrapper>() {
            @Override
            public int compare(ArtifactItemWrapper wrapper, ArtifactItemWrapper t1) {
                return wrapper.getHappenedDateTime().compareTo(t1.getHappenedDateTime());
            }
        };
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

    public Comparator<ArtifactItemWrapper> getWrapperComparator() {
        return wrapperComparator;
    }
}
