package com.example.family_artifact_register.FoundationLayer.MapModel;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.storage.StorageReference;

public class MapLocationManager {
    private static final MapLocationManager ourInstance = new MapLocationManager();

    public static MapLocationManager getInstance() {
        return ourInstance;
    }

    /**
     * The database reference used.
     */
    private CollectionReference mUserCollection;

    /**
     * Storage reference for storing photo
     */
    private StorageReference mPhotoStorageReference;

    private MapLocationManager() {
    }
}
