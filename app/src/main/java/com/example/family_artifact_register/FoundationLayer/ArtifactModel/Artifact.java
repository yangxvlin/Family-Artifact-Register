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

    // Media type of the media urls
    private int mediaType;

    // List of Urls of medias stored in database (homogeneous)
    private List<String> mediaDataUrls;

    // Description of this artifact
    private String description;

    // DateTime when this artifact is uploaded
    private String uploadDateTime;

    // DateTime when the event unfolded
    private String happenedDateTime;

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
                    String description, String uploadDateTime, String happenedDateTime,
                    String lastUpdateDateTime) {
        // Full Constructor need by firestore
        this.postId = postId;
        this.uid = uid;
        this.mediaType = mediaType;
        this.mediaDataUrls = mediaDataUrls;
        this.description = description;
        this.uploadDateTime = uploadDateTime;
        this.happenedDateTime = happenedDateTime;
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

    public int getMediaType() {
        return mediaType;
    }

    void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public List<String> getMediaDataUrls() {
        return mediaDataUrls;
    }

    void addMediaDataUrls(String mediaDataUrl) {
        mediaDataUrls.add(mediaDataUrl);
    }

    void removeMediaDataUrls(String mediaDataUrl) {
        mediaDataUrls.add(mediaDataUrl);
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public String getUploadDateTime() {
        return uploadDateTime;
    }

    public void setUploadDateTime(String uploadDateTime) {
        this.uploadDateTime = uploadDateTime;
    }

    public String getHappenedDateTime() {
        return happenedDateTime;
    }

    public void setHappenedDateTime(String happenedDateTime) {
        this.happenedDateTime = happenedDateTime;
    }

    public String getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }

    public void setLastUpdateDateTime(String lastUpdateDateTime) {
        this.lastUpdateDateTime = lastUpdateDateTime;
    }
}
