package com.example.family_artifact_register.PresentationLayer.MapPresenter;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;
import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.PresentationLayer.Util.Pair;

import java.util.ArrayList;
import java.util.List;

public class TimelineMapWrapper {
    private ArtifactTimeline artifactTimeline;

//    private List<ArtifactItemWrapper> artifactItemWrapperList;
//
//    private List<MapLocation> storeLocationList;

    private List<Pair<ArtifactItemWrapper, MapLocation>> item;

//    private List<ArtifactItemWrapper> artifactItemWrapperList;
//    private List<MapLocation> storeLocationList;

    public TimelineMapWrapper(ArtifactTimeline artifactTimeline,
                              List<ArtifactItemWrapper> artifactItemWrapperList,
                              List<MapLocation> storeLocationList) {
        this.artifactTimeline = artifactTimeline;
//        this.artifactItemWrapperList = artifactItemWrapperList;
//        this.storeLocationList = storeLocationList;
        item = new ArrayList<>();
        for (int i = 0; i < artifactItemWrapperList.size(); i++) {
            item.add(new Pair<>(artifactItemWrapperList.get(i), storeLocationList.get(i)));
        }
//        this.artifactItemWrapperList = artifactItemWrapperList;
//        this.storeLocationList = storeLocationList;
    }

    public ArtifactTimeline getArtifactTimeline() {
        return artifactTimeline;
    }

    public Pair<ArtifactItemWrapper, MapLocation> getPairItem(int i) {
        return item.get(i);
    }

    public List<Pair<ArtifactItemWrapper, MapLocation>> getAllPairs() {
        return item;
    }

    public int getTimelineSize() {
        return item.size();
    }

//    public List<ArtifactItemWrapper> getArtifactItemWrapperList() {
//        return artifactItemWrapperList;
//    }
//
//    public List<MapLocation> getStoreLocationList() {
//        return storeLocationList;
//    }

    //    public List<ArtifactItemWrapper> getArtifactItemWrapperList() {
//        return artifactItemWrapperList;
//    }
//
//    public List<MapLocation> getStoreLocationList() {
//        return storeLocationList;
//    }
}
