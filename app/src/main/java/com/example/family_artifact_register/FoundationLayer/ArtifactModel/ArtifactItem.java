package com.example.family_artifact_register.FoundationLayer.ArtifactModel;

import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocation;

import java.util.List;

/**
 * @description artifact data type for each family artifact
 */
public class ArtifactItem extends Artifact {
    // Media type of the media urls
    private int mediaType;

    // List of Urls of medias stored in database (homogeneous)
    private List<String> mediaDataUrls;

    // Description of this artifact
    private String description;

    // Location where the upload happens
    private MapLocation locationUploaded;

    // Location where the event unfolded
    private MapLocation locationHappened;

    // Location where the artifact is stored (physically)
    private MapLocation locationStored;

    // DateTime when the event unfolded
    private String happenedDateTime;

    // The associated timeline
    private ArtifactTimeline artifactTimeline;

    public ArtifactItem() {
        super();
    }

    /**
     * Create new artifact item. This constructor is used by firestore and shouldn't be accessed
     * externally
     * @deprecated Use manager/newInstance method to create new artifact. Don't use constructor
     */
    @Deprecated
    public ArtifactItem(String postId, String uid, int mediaType, List<String> mediaDataUrls,
                        String description, String uploadDateTime, String happenedDateTime,
                        String lastUpdateDateTime) {
        super(postId, uid, uploadDateTime, lastUpdateDateTime);
        this.happenedDateTime = happenedDateTime;
        this.mediaType = mediaType;
        this.mediaDataUrls = mediaDataUrls;
        this.description = description;
    }

    @Override
    public String getPostId() {
        return super.getPostId();
    }

    @Override
    public String getUid() {
        return super.getUid();
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


    @Override
    public String getUploadDateTime() {
        return super.getUploadDateTime();
    }

    public String getHappenedDateTime() {
        return happenedDateTime;
    }

    public void setHappenedDateTime(String happenedDateTime) {
        this.happenedDateTime = happenedDateTime;
    }


    @Override
    public String getLastUpdateDateTime() {
        return super.getLastUpdateDateTime();
    }

    public MapLocation getLocationUploaded() {
        return locationUploaded;
    }

    public void setLocationUploaded(MapLocation locationUploaded) {
        this.locationUploaded = locationUploaded;
    }

    public MapLocation getLocationHappened() {
        return locationHappened;
    }

    void setLocationHappened(MapLocation locationHappened) {
        this.locationHappened = locationHappened;
    }

    public MapLocation getLocationStored() {
        return locationStored;
    }

    void setLocationStored(MapLocation locationStored) {
        this.locationStored = locationStored;
    }

    public ArtifactTimeline getArtifactTimeline() {
        return artifactTimeline;
    }

    void setArtifactTimeline(ArtifactTimeline artifactTimeline) {
        this.artifactTimeline = artifactTimeline;
    }

}
