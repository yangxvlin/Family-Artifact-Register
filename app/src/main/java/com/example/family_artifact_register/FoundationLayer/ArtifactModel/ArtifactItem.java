package com.example.family_artifact_register.FoundationLayer.ArtifactModel;

import android.net.Uri;

import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocation;

import java.util.List;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-14 20:10:15
 * @description artifact data type for each family artifact
 */
public class ArtifactItem extends Artifact {
    // Location where the upload happens
    private MapLocation locationUploaded;

    // Location where the event unfolded
    private MapLocation locationHappened;

    // Location where the artifact is stored (physically)
    private MapLocation locationStored;

    // The associated timeline
    private ArtifactTimeline artifactTimeline;

    public ArtifactItem() {
        super();
    }

    public ArtifactItem(String postId, String uid, int mediaType, List<String> mediaDataUrls,
                        String description, String uploadDateTime, String happenedDateTime,
                        String lastUpdateDateTime) {
        super(postId, uid, mediaType, mediaDataUrls, description, uploadDateTime, happenedDateTime,
                lastUpdateDateTime);
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
    public int getMediaType() {
        return super.getMediaType();
    }

    @Override
    public List<String> getMediaDataUrls() {
        return super.getMediaDataUrls();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public String getUploadDateTime() {
        return super.getUploadDateTime();
    }

    @Override
    public String getHappenedDateTime() {
        return super.getHappenedDateTime();
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
