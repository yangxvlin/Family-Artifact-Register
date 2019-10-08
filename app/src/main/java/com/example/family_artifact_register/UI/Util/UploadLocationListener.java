package com.example.family_artifact_register.UI.Util;

import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocation;

/**
 * interface to let activity to communicate with fragment w.r.t. artifact's upload location
 */
public interface UploadLocationListener {
    MapLocation getUploadLocation();

    void setUploadLocation(MapLocation uploadLocation);
}
