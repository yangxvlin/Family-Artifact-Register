package com.example.family_artifact_register.FoundationLayer.ArtifactModel;

import android.util.Pair;

import com.example.family_artifact_register.FoundationLayer.DBConstant;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

/**
 * Singleton manager for managing firebase related access with artifacts.
 */
public class ArtifactManager {
    private static final ArtifactManager ourInstance = new ArtifactManager();

    public static ArtifactManager getInstance() {
        return ourInstance;
    }

    /**
     * The database reference used artifact timeline.
     */
    private CollectionReference mArtifactItemCollection;

    /**
     * The database reference used for artifact item.
     */
    private CollectionReference mArtifactTimelineCollection;

    /**
     * Storage reference for storing images
     */
    private StorageReference mArtifactMediaStorageReference;

    /**
     * Active listeners used (this should be cleared if not used)
     */
    private Map<String, Pair<ListenerRegistration, Integer>> mListenerRegistrationMap;

    private ArtifactManager() {
        mArtifactItemCollection = FirebaseFirestore
                .getInstance()
                .collection(DBConstant.ARTIFACT_ITEM);
        mArtifactTimelineCollection = FirebaseFirestore
                .getInstance()
                .collection(DBConstant.ARTIFACT_TIMELINE);
        mArtifactMediaStorageReference = FirebaseStorage
                .getInstance()
                .getReference(DBConstant.ARTIFACT_ITEM_MEDIA);
    }
}
