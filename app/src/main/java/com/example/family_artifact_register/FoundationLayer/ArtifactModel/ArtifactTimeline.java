package com.example.family_artifact_register.FoundationLayer.ArtifactModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @description artifact data type for timelined artifact i.e.: multiple artifact indexed by time
 */
public class ArtifactTimeline extends Artifact {

    /**
     * family artifacts stores in the timeline
     */
    private List<String> artifactItemPostIds;

    /**
     * the title for the timeline which describe about the timeline
     */
    private String title;

    /**
     * Create new artifact timeline. This constructor is used by firestore and shouldn't be accessed
     * externally
     *
     * @deprecated Use manager/newInstance method to create new artifact. Don't use constructor
     */
    public ArtifactTimeline(String postId, String uid, String uploadDateTime,
                            String lastUpdateDateTime, List<String> artifactItemPostIds,
                            String title) {
        super(postId, uid, uploadDateTime, lastUpdateDateTime);
        this.artifactItemPostIds = artifactItemPostIds;
        this.title = title;
    }

    public ArtifactTimeline() {
        super();
    }

    public ArtifactTimeline(String title) {
        super();

        this.title = title;
        artifactItemPostIds = new ArrayList<>();
    }

    public List<String> getArtifactItemPostIds() {
        return artifactItemPostIds;
    }

    public void addArtifactPostId(String artifactItemPostId) {
        artifactItemPostIds.add(artifactItemPostId);
    }

    public void removeArtifactPostId(String artifactItemPostId) {
        artifactItemPostIds.remove(artifactItemPostId);
    }

    /**
     * @return the string for the title
     */
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getPostId() {
        return super.getPostId();
    }

    @Override
    public String getUid() {
        return super.getUid();
    }

    @Override
    public String getUploadDateTime() {
        return super.getUploadDateTime();
    }

    @Override
    public String getLastUpdateDateTime() {
        return super.getLastUpdateDateTime();
    }
}
