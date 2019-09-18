package com.example.family_artifact_register.FoundationLayer.ArtifactModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-14 20:09:27
 * @description artifact data type for timelined artifact i.e.: multiple artifact indexed by time
 */
public class ArtifactTimeline extends Artifact {

    /**
     * family artifacts stores in the timeline
     */
    private List<ArtifactItem> artifactItems;

    /**
     * the title for the timeline which describe about the timeline
     */
    private String title;

    public ArtifactTimeline(String title) {
        super();

        this.title = title;
        artifactItems = new ArrayList<>();
    }

    public ArtifactTimeline(String title, List<ArtifactItem> artifactItems) {
        super();

        this.title = title;
        this.artifactItems = artifactItems;
    }

    public void addArtifact(ArtifactItem artifactItem) { artifactItems.add(artifactItem); }

    /**
     * @return the string for the title
     */
    public String getTitle() { return this.title; }
}
