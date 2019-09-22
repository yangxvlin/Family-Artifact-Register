package com.example.family_artifact_register.UI.MapServiceFragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

abstract class BaseMakerZoomStrategy {
    abstract CameraUpdate makeCameraUpdate(List<Marker> markers);
}
