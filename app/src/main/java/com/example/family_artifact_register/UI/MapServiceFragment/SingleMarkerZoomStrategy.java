package com.example.family_artifact_register.UI.MapServiceFragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

public class SingleMarkerZoomStrategy extends BaseMakerZoomStrategy {
    private static final float ZOOM = 8;

    @Override
    CameraUpdate makeCameraUpdate(List<Marker> markers) {
        assert markers.size() == 1;
        Marker marker = markers.get(0);
        CameraPosition cp = CameraPosition
                .builder()
                .zoom(ZOOM)
                .target(marker.getPosition())
                .build();
        return CameraUpdateFactory.newCameraPosition(cp);
    }
}
