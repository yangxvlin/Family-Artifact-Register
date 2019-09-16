package com.example.family_artifact_register.FoundationLayer.ArtifactModel;

import android.graphics.Bitmap;

import com.example.family_artifact_register.FoundationLayer.MapLocation;

import java.util.Date;
import java.util.List;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-14 20:10:15
 * @description artifact data type for each family artifact
 */
public class ArtifactItem extends Artifact {
//    private ArrayList<> photos;

    private int id;

    private List<Bitmap> images;

    private Date timeHappened;

    private MapLocation locationUploaded;

    private MapLocation locationHappened;

    private MapLocation locationStored;

    private ArtifactTimeline artifactTimeline;

    public ArtifactItem() {
        super();
    }
}
