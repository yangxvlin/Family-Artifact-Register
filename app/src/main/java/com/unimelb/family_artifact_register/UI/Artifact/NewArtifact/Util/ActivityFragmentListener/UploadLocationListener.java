package com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener;

import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocation;

/**
 * interface to let activity to communicate with fragment w.r.t. artifact's upload location
 */
public interface UploadLocationListener {
    /**
     * @return artifact item's upload location
     */
    MapLocation getUploadLocation();

    /**
     * @param uploadLocation artifact item's upload location
     */
    void setUploadLocation(MapLocation uploadLocation);
}
