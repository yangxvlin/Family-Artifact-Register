package com.example.family_artifact_register.UI.MapServiceFragment;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

public class MultipleMarkerZoomStrategy extends BaseMakerZoomStrategy {
    @Override
    CameraUpdate makeCameraUpdate(List<Marker> markers) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        int padding = 100; // offset from edges of the map in pixels
        LatLngBounds bounds = builder.build();
        return CameraUpdateFactory.newLatLngBounds(bounds, padding);
    }
}
