package com.unimelb.family_artifact_register.UI.Artifact.ArtifactMap.Util.MarkerStrategy;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

public class MultipleMarkerZoomStrategy extends MarkerZoomStrategy {
    /**
     * Returns a CameraUpdate object that can be used my GoogleMap to update the camera location
     * @param markers The Markers to be displayed
     * @return A CameraUpdate object used to update the location of camera view.
     */
    @Override
    public CameraUpdate makeCameraUpdate(List<Marker> markers) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        int padding = 0; // offset from edges of the map in pixels
        LatLngBounds bounds = builder.build();
        return CameraUpdateFactory.newLatLngBounds(bounds, 10, 10, padding);
    }
}
