package com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener;

import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocation;

/**
 * activity's interface for fragment to access artifact happened location data
 */
public interface HappenedLocationListener {
    /**
     * @return artifact item happened location
     */
    MapLocation getHappenedLocation();

    /**
     * @param happenedLocation artifact item happened location
     */
    void setHappenedLocation(MapLocation happenedLocation);
}
