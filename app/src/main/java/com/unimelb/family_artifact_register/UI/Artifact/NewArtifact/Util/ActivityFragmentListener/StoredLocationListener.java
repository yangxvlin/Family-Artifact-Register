package com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener;

import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocation;

/**
 * activity's interface for fragment to access artifact stored location data
 */
public interface StoredLocationListener {
    /**
     * @param storedLocation artifact item's stored location
     */
    void setStoredLocation(MapLocation storedLocation);

    /**
     * @return artifact item's stored location
     */
    MapLocation getStoredLocation();
}

