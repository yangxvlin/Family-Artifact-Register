package com.unimelb.family_artifact_register.UI.MapServiceFragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

class ZeroMarkerZoomStrategy extends MarkerZoomStrategy {
    private static final LatLng DEFAULT_LATLNG = new LatLng(0, 0);

    /**
     * Returns a CameraUpdate object that can be used my GoogleMap to update the camera location
     *
     * @param markers The Markers to be displayed
     * @return A CameraUpdate object used to update the location of camera view.
     */
    @Override
    CameraUpdate makeCameraUpdate(List<Marker> markers) {
        CameraPosition cp = CameraPosition
                .builder()
                .zoom(DEFAULT_ZOOM)
                .target(DEFAULT_LATLNG)
                .build();
        return CameraUpdateFactory.newCameraPosition(cp);
    }
}
