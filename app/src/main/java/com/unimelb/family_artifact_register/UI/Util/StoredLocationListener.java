package com.unimelb.family_artifact_register.UI.Util;

import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocation;

/**
 * activity's interface for fragment to access artifact stored location data
 */
public interface StoredLocationListener {
    MapLocation getStoredLocation();

    void setStoredLocation(MapLocation storedLocation);
}

