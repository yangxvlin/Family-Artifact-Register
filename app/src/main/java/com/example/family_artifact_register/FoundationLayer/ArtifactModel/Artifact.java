package com.example.family_artifact_register.FoundationLayer.ArtifactModel;

import java.util.List;

/**
 * Data Class for abstract artifacts
 */
public abstract class Artifact {
    // Post id used for storing in database
    private String postId;

    // User id associated to this artifact
    private String uid;
    // DateTime when this artifact is uploaded
    private String uploadDateTime;

    // TODO (Will be DateTime when user last update the artifact contents)
    private String lastUpdateDateTime;

    public Artifact() {
        // Empty constructor needed by firestore
    }

    /**
     * Constructor used by firestore, but this is abstract class, won't be used directly,
     * only for setting up the interfaces
     */
    public Artifact(String postId, String uid, int mediaType, List<String> mediaDataUrls,
                    String description, String uploadDateTime, String lastUpdateDateTime) {
        // Full Constructor need by firestore
        this.postId = postId;
        this.uid = uid;
        this.uploadDateTime = uploadDateTime;
        this.lastUpdateDateTime = lastUpdateDateTime;
    }

    public String getPostId() {
        return postId;
    }

    void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUid() {
        return uid;
    }

    void setUid(String uid) {
        this.uid = uid;
    }

    public String getUploadDateTime() {
        return uploadDateTime;
    }

    public void setUploadDateTime(String uploadDateTime) {
        this.uploadDateTime = uploadDateTime;
    }

    public String getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }

    public void setLastUpdateDateTime(String lastUpdateDateTime) {
        this.lastUpdateDateTime = lastUpdateDateTime;
    }
}
