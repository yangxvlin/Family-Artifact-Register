package com.example.family_artifact_register.FoundationLayer.ArtifactModel;

import android.util.Pair;

import com.example.family_artifact_register.FoundationLayer.Util.DBConstant;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
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

    private UserInfoManager userInfoManager;

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

    /**
     * Instantiate artifact manager, also give it a user manager for updating current user.
     */
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
        userInfoManager = UserInfoManager.getInstance();
    }

    public void addArtifact(Artifact artifact) {
        // Upload, if successful, store it to user

    }

    private void storeArtifact(Artifact artifact) {
    }
}
