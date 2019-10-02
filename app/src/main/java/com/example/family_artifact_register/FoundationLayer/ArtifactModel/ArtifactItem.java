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

    // Title of this artifact
    private String title;

    // Description of this artifact
    private String description;

    // MapLocationId where the upload happens
    private String locationUploadedId;

    // MapLocationId where the event unfolded
    private String locationHappenedId;

    // MapLocationId where the artifact is stored (physically)
    private String locationStoredId;

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
    public ArtifactItem(String postId, String uid, String uploadDateTime, String lastUpdateDateTime,
                        int mediaType, List<String> mediaDataUrls, String title, String description,
                        String locationUploadedId, String locationHappenedId,
                        String locationStoredId, String happenedDateTime,
                        ArtifactTimeline artifactTimeline) {
        super(postId, uid, uploadDateTime, lastUpdateDateTime);
        this.mediaType = mediaType;
        this.mediaDataUrls = mediaDataUrls;
        this.title = title;
        this.description = description;
        this.locationUploadedId = locationUploadedId;
        this.locationHappenedId = locationHappenedId;
        this.locationStoredId = locationStoredId;
        this.happenedDateTime = happenedDateTime;
        this.artifactTimeline = artifactTimeline;
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
        mediaDataUrls.remove(mediaDataUrl);
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
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

    public String getLocationUploadedId() {
        return locationUploadedId;
    }

    public void setLocationUploadedId(String locationUploadedId) {
        this.locationUploadedId = locationUploadedId;
    }

    public String getLocationHappenedId() {
        return locationHappenedId;
    }

    void setLocationHappenedId(String locationHappenedId) {
        this.locationHappenedId = locationHappenedId;
    }

    public String getLocationStoredId() {
        return locationStoredId;
    }

    void setLocationStored(MapLocation locationStored) {
        this.locationStoredId = locationStoredId;
    }

    public ArtifactTimeline getArtifactTimeline() {
        return artifactTimeline;
    }

    void setArtifactTimeline(ArtifactTimeline artifactTimeline) {
        this.artifactTimeline = artifactTimeline;
    }

    public static ArtifactItem newInstance(String uploadDateTime,
                                           String lastUpdateDateTime,
                                           int mediaType,
                                           List<String> mediaDataUrls,
                                           String title,
                                           String description,
                                           String locationUploadedId,
                                           String locationHappenedId,
                                           String locationStoredId,
                                           String happenedDateTime,
                                           ArtifactTimeline artifactTimeline
                                           ) {
        return new ArtifactItem(null,
                null,
                uploadDateTime,
                lastUpdateDateTime,
                mediaType,
                mediaDataUrls,
                title,
                description,
                locationUploadedId,
                locationHappenedId,
                locationStoredId,
                happenedDateTime,
                artifactTimeline
        );
    }
}
