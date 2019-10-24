package com.unimelb.family_artifact_register.UI.Artifact.ArtifactMap.Util.MarkerStrategy;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

public class SingleMarkerZoomStrategy extends MarkerZoomStrategy {
    /**
     * Returns a CameraUpdate object that can be used my GoogleMap to update the camera location
     * @param markers The Markers to be displayed
     * @return A CameraUpdate object used to update the location of camera view.
     */
    @Override
    public CameraUpdate makeCameraUpdate(List<Marker> markers) {
        assert markers.size() == 1;
        Marker marker = markers.get(0);
        CameraPosition cp = CameraPosition
                .builder()
                .zoom(DEFAULT_ZOOM)
                .target(marker.getPosition())
                .build();
        return CameraUpdateFactory.newCameraPosition(cp);
    }
}
