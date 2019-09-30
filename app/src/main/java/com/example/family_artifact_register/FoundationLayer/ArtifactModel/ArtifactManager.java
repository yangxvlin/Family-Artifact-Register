package com.example.family_artifact_register.FoundationLayer.ArtifactModel;

import android.net.Uri;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.family_artifact_register.FoundationLayer.Util.DBConstant;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.example.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;
import com.example.family_artifact_register.FoundationLayer.Util.LiveDataListDispatchHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Singleton manager for managing firebase related access with artifacts.
 */
public class ArtifactManager {
    /**
     Tag for logging
     */
    private static final String TAG = ArtifactManager.class.getSimpleName();

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
    private Map<String, ListenerRegistration> mListenerRegistrationMap;

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
        // Upload, store it to user
        if (artifact instanceof ArtifactItem) {
            storeArtifact((ArtifactItem) artifact);
        } else if (artifact instanceof ArtifactTimeline) {
            storeArtifact((ArtifactTimeline) artifact);
        }

        UserInfoManager.getInstance().addArtifactId(artifact.getPostId());
    }

    private void storeArtifact(ArtifactItem artifact) {
        // 0. Set post id not not have one
        DocumentReference artifactReference;
        if (artifact.getPostId() == null) {
            artifactReference = mArtifactItemCollection.document(String.valueOf(System.currentTimeMillis()));
        } else {
            artifactReference = mArtifactItemCollection.document(artifact.getPostId());
        }

        // Get reference based on current mapLocation id
        StorageReference artifactMediaStorageReference = mArtifactMediaStorageReference
                .child(artifact.getPostId());

        // 1. Store Photo (same as what's in MapLocation)
        Map<String, String> mediaUrlMap = new HashMap<>();
        int i = 0;
        if (artifact.getMediaDataUrls() != null) {
            for (String mediaUrl : artifact.getMediaDataUrls()) {
                mediaUrlMap.put(artifact.getPostId()+"_"+i, mediaUrl);
                Log.i(TAG, "Url: {" + artifact.getPostId()+"_"+i + ", " + mediaUrlMap.get(mediaUrl) + "}");
                i += 1;
            }
        }

        Log.i(TAG, "adding artifact media...");
        for (String key: mediaUrlMap.keySet()) {
            Log.i(TAG, "Url: {" + key + ", " + mediaUrlMap.get(key) + "}");
            FirebaseStorageHelper.getInstance()
                    .uploadByUri(Uri.parse(mediaUrlMap.get(key)), artifactMediaStorageReference, key)
                    .addOnFailureListener(e -> Log.w(TAG,
                            "Error Uploading media Url: {" + key + ", " +
                                    mediaUrlMap.get(key) + "}, e:" + e.toString()))
                    .addOnSuccessListener(taskSnapshot -> Log.d(TAG,
                            "Successfully upload media Url: {" + key + ", " +
                                    mediaUrlMap.get(key) + "}"));
        }

        // 2. Upload Artifact
        Log.i(TAG, "adding artifact to fire store...");
        // Now store the actual artifact
        artifactReference.set(artifact)
                .addOnFailureListener(e -> Log.w(TAG,
                        "Error Uploading Location:" + artifact.toString() +
                                "e:" + e.toString()));
    }

    private void storeArtifact(ArtifactTimeline artifact) {
        // 0. Set post id not not have one
        DocumentReference artifactReference;
        if (artifact.getPostId() == null) {
            artifactReference = mArtifactItemCollection.document(String.valueOf(System.currentTimeMillis()));
        } else {
            artifactReference = mArtifactItemCollection.document(artifact.getPostId());
        }

        // 2. Upload Artifact
        Log.i(TAG, "adding artifact timeline to firestore...");
        // Now store the actual artifact
        artifactReference.set(artifact)
                .addOnFailureListener(e -> Log.w(TAG,
                        "Error Uploading Location:" + artifact.toString() +
                                "e:" + e.toString()));
    }

    public LiveData<ArtifactItem> getArtifactByPostId(String postId) {
        MutableLiveData<ArtifactItem> mutableLiveData = new MutableLiveData<>();
        mArtifactItemCollection.document(postId).get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful() && task.getResult() != null &&
                            task.getResult().exists()) {
                        mutableLiveData.setValue(task.getResult().toObject(ArtifactItem.class));
                    } else {
                        Log.e(TAG, "getArtifactByPostId failed: " + task.getException());
                    }
                }
        );
        return mutableLiveData;
    }

    public LiveData<List<ArtifactItem>> getArtifactByPostId(List<String> postIds, int timeout) {
        MutableLiveData<List<ArtifactItem>> mutableLiveData = new MutableLiveData<>();
        LiveDataListDispatchHelper<ArtifactItem> liveDataListDispatchHelper =
                new LiveDataListDispatchHelper<>(mutableLiveData, timeout);
        for (String postId: new HashSet<>(postIds)) {
            liveDataListDispatchHelper.addWaitingTask();
            LiveData<ArtifactItem> artifactItemLiveData = getArtifactByPostId(postId);
            artifactItemLiveData.observeForever(
                    new Observer<ArtifactItem>() {
                        @Override
                        public void onChanged(ArtifactItem artifactItem) {
                            liveDataListDispatchHelper
                                    .addResultAfterTaskCompletion(artifactItem);
                            artifactItemLiveData.removeObserver(this);
                        }
                    }
            );
        }
        return mutableLiveData;
    }

    public LiveData<List<ArtifactItem>> getArtifactByUid(String uid) {
        MutableLiveData<List<ArtifactItem>> mutableLiveData = new MutableLiveData<>();
        mArtifactItemCollection.whereEqualTo("uid", uid).get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful() && task.getResult() != null &&
                            task.getResult().isEmpty()) {
                        mutableLiveData.setValue(task.getResult().toObjects(ArtifactItem.class));
                    } else {
                        Log.e(TAG, "getArtifactByUid failed: " + task.getException());
                    }
                }
        );
        return mutableLiveData;
    }
}
