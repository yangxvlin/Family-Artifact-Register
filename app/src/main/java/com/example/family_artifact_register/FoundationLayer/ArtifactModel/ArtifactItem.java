package com.example.family_artifact_register.FoundationLayer.ArtifactModel;

import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description artifact data type for each family artifact
 */
public class ArtifactItem extends Artifact {
    public static final String TAG = ArtifactItem.class.getSimpleName();

    // Media type of the media urls
    private int mediaType;

    // List of Urls of medias stored in database (homogeneous)
    private List<String> mediaDataUrls;

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

    // Number of likes item has
    private Map<String, Boolean> likes;

    // The associated timeline
    private String artifactTimelineId;

    // The associated comments
    private  List<String> artifactCommentIds;

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
                        int mediaType, List<String> mediaDataUrls, String description,
                        String locationUploadedId, String locationHappenedId,
                        String locationStoredId, String happenedDateTime,
                        Map<String, Boolean> likes, String artifactTimelineId,
                        List<String> artifactCommentIds) {
        super(postId, uid, uploadDateTime, lastUpdateDateTime);
        this.mediaType = mediaType;
        this.mediaDataUrls = mediaDataUrls;
        this.description = description;
        this.locationUploadedId = locationUploadedId;
        this.locationHappenedId = locationHappenedId;
        this.locationStoredId = locationStoredId;
        this.happenedDateTime = happenedDateTime;
        this.likes = likes;
        this.artifactTimelineId = artifactTimelineId;
        if (artifactCommentIds == null) {
            artifactCommentIds = new ArrayList<>();
        }
        this.artifactCommentIds = artifactCommentIds;
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

    @Deprecated
    public void setMediaDataUrls(List<String> userDeviceMediaUris) { this.mediaDataUrls = userDeviceMediaUris; }

    void addMediaDataUrls(String mediaDataUrl) {
        mediaDataUrls.add(mediaDataUrl);
    }

    void removeMediaDataUrls(String mediaDataUrl) {
        mediaDataUrls.remove(mediaDataUrl);
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

    public Map<String, Boolean> getlikes() {return likes;}

    void addLike(String uid) {
        this.likes.putIfAbsent(uid, true);
    }

    void removeLike(String uid) {
        if (likes.containsKey(uid)) {
            this.likes.remove(uid);
        }
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

    public String getArtifactTimelineId() {
        return artifactTimelineId;
    }

    void setArtifactTimelineId(String artifactTimelineId) {
        this.artifactTimelineId = artifactTimelineId;
    }

    public List<String> getArtifactCommentIds() { return artifactCommentIds; }

    void addArtifactCommentIds(String artifactComment) {
        if (artifactCommentIds == null) {
            artifactCommentIds = new ArrayList<>();
        }
        this.artifactCommentIds.add(artifactComment);
    }

    public static ArtifactItem newInstance(String uploadDateTime,
                                           String lastUpdateDateTime,
                                           int mediaType,
                                           List<String> mediaDataUrls,
                                           String description,
                                           String locationUploadedId,
                                           String locationHappenedId,
                                           String locationStoredId,
                                           String happenedDateTime,
                                           String artifactTimelineId
                                           ) {
        return new ArtifactItem(null,
                null,
                uploadDateTime,
                lastUpdateDateTime,
                mediaType,
                mediaDataUrls,
                description,
                locationUploadedId,
                locationHappenedId,
                locationStoredId,
                happenedDateTime,
                new HashMap<>(),
                artifactTimelineId,
                new ArrayList<>()
        );
    }
}
