package com.example.family_artifact_register.UI.MapServiceFragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

public class MultipleMarkerZoomStrategy extends BaseMakerZoomStrategy {
    @Override
    CameraUpdate makeCameraUpdate(List<Marker> markers) {
        return null;// TODO (Dovermore, 2019-09-22): Implement makeCameraUpdate
    }
}
