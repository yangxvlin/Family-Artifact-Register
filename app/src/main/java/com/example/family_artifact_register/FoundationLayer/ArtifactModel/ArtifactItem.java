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
//    private ArrayList<> photos;

    private int id;

    private List<Uri> images;

    private List<Uri> videos;

    private String timeHappened;

    private MapLocation locationUploaded;

    private MapLocation locationHappened;

    private MapLocation locationStored;

    private ArtifactTimeline artifactTimeline;

    private String description;

    public ArtifactItem() {
        super();
    }

    public ArtifactItem(int id,
                        List<Uri> images,
                        List<Uri> videos,
                        String timeHappened,
                        MapLocation locationUploaded,
                        MapLocation locationHappened,
                        MapLocation locationStored,
                        ArtifactTimeline artifactTimeline,
                        String description,
                        String timeCreated) {
        super(timeCreated);
        this.id = id;
        this.images = images;
        this.videos = videos;
        this.timeHappened = timeHappened;
        this.locationUploaded = locationUploaded;
        this.locationHappened = locationHappened;
        this.locationStored = locationStored;
        this.artifactTimeline = artifactTimeline;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Uri> getImages() {
        return images;
    }

    public void setImages(List<Uri> images) {
        this.images = images;
    }

    public List<Uri> getVideos() {
        return videos;
    }

    public void setVideos(List<Uri> videos) {
        this.videos = videos;
    }

    public String getTimeHappened() {
        return timeHappened;
    }

    public void setTimeHappened(String timeHappened) {
        this.timeHappened = timeHappened;
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

    public void setLocationHappened(MapLocation locationHappened) {
        this.locationHappened = locationHappened;
    }

    public MapLocation getLocationStored() {
        return locationStored;
    }

    public void setLocationStored(MapLocation locationStored) {
        this.locationStored = locationStored;
    }

    public ArtifactTimeline getArtifactTimeline() {
        return artifactTimeline;
    }

    public void setArtifactTimeline(ArtifactTimeline artifactTimeline) {
        this.artifactTimeline = artifactTimeline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
