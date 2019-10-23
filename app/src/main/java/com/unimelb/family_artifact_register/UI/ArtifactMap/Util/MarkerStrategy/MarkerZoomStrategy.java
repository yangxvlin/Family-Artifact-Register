package com.unimelb.family_artifact_register.UI.ArtifactMap.Util.MarkerStrategy;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

/**
 * Abstract Class defining the strategy handling the request of displaying markers on map based on
 * the length of markers.
 */
public abstract class MarkerZoomStrategy {
    // The default Zoom level to use if there are not enough markers to generate a proper zoom.
    public static final float DEFAULT_ZOOM = 9;

    /**
     * Returns a CameraUpdate object that can be used my GoogleMap to update the camera location
     * @param markers The Markers to be displayed
     * @return A CameraUpdate object used to update the location of camera view.
     */
    public abstract CameraUpdate makeCameraUpdate(List<Marker> markers);
}
