package com.unimelb.family_artifact_register.UI.NewArtifact.Util;

import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocation;

/**
 * activity's interface for fragment to access artifact stored location data
 */
public interface StoredLocationListener {
    void setStoredLocation(MapLocation storedLocation);

    MapLocation getStoredLocation();
}

