package com.example.family_artifact_register.UI.MapServiceFragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

/**
 * Abstract Class defining the strategy handling the request of displaying markers on map based on
 * the length of markers.
 */
abstract class MarkerZoomStrategy {
    // The default Zoom level to use if there are not enough markers to generate a proper zoom.
    static final float DEFAULT_ZOOM = 9;

    /**
     * Returns a CameraUpdate object that can be used my GoogleMap to update the camera location
     * @param markers The Markers to be displayed
     * @return A CameraUpdate object used to update the location of camera view.
     */
    abstract CameraUpdate makeCameraUpdate(List<Marker> markers);
}
