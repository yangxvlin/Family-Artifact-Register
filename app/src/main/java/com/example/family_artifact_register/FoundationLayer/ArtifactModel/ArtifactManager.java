package com.example.family_artifact_register.FoundationLayer.ArtifactModel;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.example.family_artifact_register.FoundationLayer.Util.DBConstant;
import com.example.family_artifact_register.FoundationLayer.Util.DefaultListeners;
import com.example.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;
import com.example.family_artifact_register.FoundationLayer.Util.LiveDataListDispatchHelper;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
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
     * The database reference used for artifact comments.
     */
    private CollectionReference mArtifactCommentCollection;

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

        mArtifactCommentCollection = FirebaseFirestore
                .getInstance()
                .collection(DBConstant.ARTIFACT_COMMENT);

        mListenerRegistrationMap = new HashMap<>();
        userInfoManager = UserInfoManager.getInstance();
    }

    public void addArtifact(Artifact artifact) {
        // Upload, store it to user
        if (artifact instanceof ArtifactItem) {
            storeArtifact((ArtifactItem) artifact);
            userInfoManager.addArtifactItemId(artifact.getPostId());
        } else if (artifact instanceof ArtifactTimeline) {
            storeArtifact((ArtifactTimeline) artifact);
            userInfoManager.addArtifactTimelineId(artifact.getPostId());
        }
    }

    private void storeArtifact(ArtifactItem artifact) {
        // 0. Set post id if not have one
        DocumentReference artifactReference;
        if (artifact.getPostId() == null) {
            artifact.setPostId(String.valueOf(System.currentTimeMillis()));
        }
        // 1. Set user id if not have one
        if (artifact.getUid() == null) {
            artifact.setUid(userInfoManager.getCurrentUid());
        }
        artifactReference = mArtifactItemCollection.document(artifact.getPostId());

        Log.i(TAG, "adding artifact media...");
        MutableLiveData<List<String>> uploadHelperLiveData = new MutableLiveData<>();
        uploadHelperLiveData.observeForever(
                new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> remoteUrls) {
                        Log.i(TAG, "adding artifact to firestore, with remoteUrl: " + remoteUrls.toString());
                        artifact.getMediaDataUrls().clear();
                        for (String url : remoteUrls) {
                            artifact.addMediaDataUrls(url);
                        }
                        // Now store the actual Artifact
                        artifactReference.set(artifact)
                                .addOnFailureListener(e -> Log.w(TAG,
                                        "Error Uploading artifact:" + artifact.toString() +
                                                "e:" + e.toString()));
                        uploadHelperLiveData.removeObserver(this);
                    }
                }
        );
        LiveDataListDispatchHelper<String> liveDataListDispatchHelper =
                new LiveDataListDispatchHelper<>(uploadHelperLiveData, 60000);

        liveDataListDispatchHelper.addWaitingTask();

        Log.d(TAG, "Artifact Media Urls: "+ artifact.getMediaDataUrls().toString());
        for (String localMediaDataUrl: artifact.getMediaDataUrls()) {

            Log.d(TAG, "Iterated to URL: " + localMediaDataUrl);
            Uri localUri = Uri.parse(localMediaDataUrl);
            Task<UploadTask.TaskSnapshot> uploadTask = FirebaseStorageHelper
                    .getInstance()
                    .uploadByUri(localUri);
            if (uploadTask != null) {
                liveDataListDispatchHelper.addWaitingTask();
                uploadTask.addOnCompleteListener(
                        task -> {
                            Log.d(TAG, "Finished Uploading: " + localMediaDataUrl);
                            if (task.isSuccessful()) {
                                liveDataListDispatchHelper.addResult(FirebaseStorageHelper
                                        .getInstance()
                                        .getRemoteByLocalUri(localUri));
                                Log.d(TAG, "Successfully upload media Url: {" + localMediaDataUrl + "}");
                            } else {
                                Log.w(TAG, "Error Uploading media Url: {" + localMediaDataUrl
                                        + "}, e:" + task.getException());
                            }
                            liveDataListDispatchHelper.completeWaitingTaskAndDispatch();
                        }
                );
            } else {
                Log.d(TAG, "localMediaDataUrl:" + localMediaDataUrl + ", already in database");
            }
        }
        liveDataListDispatchHelper.completeWaitingTaskAndDispatch();
    }

    private void storeArtifact(ArtifactTimeline artifact) {
        // 0. Set post id not not have one
        DocumentReference artifactReference;
        if (artifact.getPostId() == null) {
            artifact.setPostId(String.valueOf(System.currentTimeMillis()));
        }
        artifactReference = mArtifactTimelineCollection.document(artifact.getPostId());

        // 2. Upload Artifact
        Log.i(TAG, "adding artifact timeline to firestore...");
        // Now store the actual artifact
        artifactReference.set(artifact)
                .addOnFailureListener(e -> Log.w(TAG,
                        "Error Uploading Artifact:" + artifact.toString() +
                                "e:" + e.toString()));
    }

    public LiveData<ArtifactItem> getArtifactItemByPostId(String postId) {
        MutableLiveData<ArtifactItem> mutableLiveData = new MutableLiveData<>();
        mArtifactItemCollection.document(postId).get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful() && task.getResult() != null &&
                            task.getResult().exists()) {
                        mutableLiveData.setValue(task.getResult().toObject(ArtifactItem.class));
                    } else {
                        Log.e(TAG, "getArtifactItemByPostId failed: " + task.getException());
                    }
                }
        );
        return mutableLiveData;
    }

    public LiveData<ArtifactItem> listenArtifactItemByPostId(String postId, String listenerIdentifier) {
        MutableLiveData<ArtifactItem> mutableLiveData = new MutableLiveData<>();
        // Start Listening data snapshot
        ListenerRegistration listenerRegistration = mArtifactItemCollection
                .document(postId)
                .addSnapshotListener(
                        (documentSnapshot, e) -> {
                            if (e == null && documentSnapshot != null) {
                                ArtifactItem artifactItem;
                                if (documentSnapshot.exists()) {
                                    artifactItem = documentSnapshot.toObject(ArtifactItem.class);
                                    mutableLiveData.setValue(artifactItem);
                                }
                            } else {
                                Log.w(TAG, "DB query result in error: " + e);
                            }
                        }
                );
        // Register listener to remove in the future
        mListenerRegistrationMap.put(listenerIdentifier, listenerRegistration);
        return mutableLiveData;
    }

    public LiveData<List<ArtifactItem>> getArtifactItemByPostId(List<String> postIds, int timeout) {
        MutableLiveData<List<ArtifactItem>> mutableLiveData = new MutableLiveData<>();
        LiveDataListDispatchHelper<ArtifactItem> liveDataListDispatchHelper =
                new LiveDataListDispatchHelper<>(mutableLiveData, timeout);
        for (String postId: new HashSet<>(postIds)) {
            liveDataListDispatchHelper.addWaitingTask();
            LiveData<ArtifactItem> artifactItemLiveData = getArtifactItemByPostId(postId);
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

    public LiveData<List<ArtifactItem>> getArtifactItemByUid(String uid) {
        MutableLiveData<List<ArtifactItem>> mutableLiveData = new MutableLiveData<>();
        mArtifactItemCollection.whereEqualTo("uid", uid).get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful() && task.getResult() != null &&
                            !task.getResult().isEmpty()) {
                        mutableLiveData.setValue(task.getResult().toObjects(ArtifactItem.class));
                    } else {
                        Log.e(TAG, "getArtifactByUid failed: " + task.getException());
                    }
                }
        );
        return mutableLiveData;
    }

    public LiveData<List<ArtifactItem>> listenArtifactItemByUid(String uid, String listenerIdentifier) {
        MutableLiveData<List<ArtifactItem>> mutableLiveData = new MutableLiveData<>();
        ListenerRegistration listenerRegistration = mArtifactItemCollection.whereEqualTo("uid", uid)
                .addSnapshotListener(
                        (queryDocumentSnapshots, e) -> {
                            if (e == null && queryDocumentSnapshots != null) {
                                List<ArtifactItem> itemList = new ArrayList<>();
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    // Query is not empty, update value
                                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                                        if (documentSnapshot.exists()) {
                                            itemList.add(documentSnapshot.toObject(ArtifactItem.class));
                                        }
                                    }
                                }
                                mutableLiveData.setValue(itemList);
                            } else {
                                Log.w(TAG, "DB query result in error: " + e);
                            }
                        }
                );
        mListenerRegistrationMap.put(listenerIdentifier, listenerRegistration);
        return mutableLiveData;
    }

    public LiveData<ArtifactTimeline> getArtifactTimelineByPostId(String postId) {
        MutableLiveData<ArtifactTimeline> mutableLiveData = new MutableLiveData<>();
        mArtifactTimelineCollection.document(postId).get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful() && task.getResult() != null &&
                            task.getResult().exists()) {
                        mutableLiveData.setValue(task.getResult().toObject(ArtifactTimeline.class));
                    } else {
                        Log.e(TAG, "getArtifactTimelineByPostId failed: " + task.getException());
                    }
                }
        );
        return mutableLiveData;
    }

    public LiveData<List<ArtifactTimeline>> getArtifactTimelineByPostId(List<String> postIds, int timeout) {
        MutableLiveData<List<ArtifactTimeline>> mutableLiveData = new MutableLiveData<>();
        LiveDataListDispatchHelper<ArtifactTimeline> liveDataListDispatchHelper =
                new LiveDataListDispatchHelper<>(mutableLiveData, timeout);
        for (String postId: new HashSet<>(postIds)) {
            liveDataListDispatchHelper.addWaitingTask();
            LiveData<ArtifactTimeline> artifactTimelineLiveData = getArtifactTimelineByPostId(postId);
            artifactTimelineLiveData.observeForever(
                    new Observer<ArtifactTimeline>() {
                        @Override
                        public void onChanged(ArtifactTimeline artifactTimeline) {
                            liveDataListDispatchHelper
                                    .addResultAfterTaskCompletion(artifactTimeline);
                            artifactTimelineLiveData.removeObserver(this);
                        }
                    }
            );
        }
        return mutableLiveData;
    }

    public LiveData<List<ArtifactTimeline>> getArtifactTimelineByUid(String uid) {
        MutableLiveData<List<ArtifactTimeline>> mutableLiveData = new MutableLiveData<>();
        mArtifactTimelineCollection.whereEqualTo("uid", uid).get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful() && task.getResult() != null &&
                            !task.getResult().isEmpty()) {
                        mutableLiveData.setValue(task.getResult().toObjects(ArtifactTimeline.class));
                    } else {
                        Log.e(TAG, "getArtifactByUid failed: " + task.getException());
                    }
                }
        );
        return mutableLiveData;
    }

    public LiveData<List<ArtifactTimeline>> listenArtifactTimelineByUid(String uid, String listenerIdentifier) {
        MutableLiveData<List<ArtifactTimeline>> mutableLiveData = new MutableLiveData<>();
        ListenerRegistration listenerRegistration = mArtifactTimelineCollection.whereEqualTo("uid", uid)
                .addSnapshotListener(
                        (queryDocumentSnapshots, e) -> {
                            if (e == null && queryDocumentSnapshots != null) {
                                List<ArtifactTimeline> timelineList = new ArrayList<>();
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    // Query is not empty, update value
                                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                                        if (documentSnapshot.exists()) {
                                            timelineList.add(documentSnapshot.toObject(ArtifactTimeline.class));
                                        }
                                    }
                                }
                                mutableLiveData.setValue(timelineList);
                            } else {
                                Log.w(TAG, "DB query result in error: " + e);
                            }
                        }
                );
        mListenerRegistrationMap.put(listenerIdentifier, listenerRegistration);
        return mutableLiveData;
    }

    public void removeListener(String listenerIdentifier) {
        if (mListenerRegistrationMap.containsKey(listenerIdentifier)) {
            mListenerRegistrationMap.get(listenerIdentifier).remove();
            mListenerRegistrationMap.remove(listenerIdentifier);
        }
    }

    public void associateArtifactItemAndArtifactTimeline(ArtifactItem item,
                                                         ArtifactTimeline timeline) {
        timeline.addArtifactPostId(item.getPostId());
        item.setArtifactTimelineId(timeline.getPostId());
        storeArtifact(timeline);
        storeArtifact(item);
    }

    /**
     * Create and store a comment of user to a given artifact
     *
     * @param artifactId the artifact to add to
     * @param senderId   the sender who sent it
     * @param content    the content of the comment
     */
    public void addComment(String artifactId, String senderId, String content) {
        // Create new comment
        ArtifactComment comment = ArtifactComment.newInstance(artifactId, senderId, content);

        // Get the artifact
        mArtifactItemCollection
                .document(artifactId)
                .get().addOnSuccessListener(DefaultListeners.getInstance()
                .getOnSuccessListener(TAG))
                .addOnSuccessListener(
                        documentSnapshot -> {
                            if (documentSnapshot.exists() && documentSnapshot
                                    .toObject(ArtifactItem.class) != null) {
                                ArtifactItem artifactItem = documentSnapshot
                                        .toObject(ArtifactItem.class);
                                String commentId = comment.getPostId();
                                artifactItem.addArtifactCommentIds(commentId);
                                // Store comment, if successful, save to artifact
                                mArtifactCommentCollection.document(commentId)
                                        .set(comment)
                                        .addOnSuccessListener(
                                                aVoid -> {
                                                    mArtifactItemCollection.document(
                                                            artifactId
                                                    ).update("artifactCommentIds",
                                                            artifactItem.getArtifactCommentIds())
                                                            .addOnSuccessListener(DefaultListeners
                                                                    .getInstance()
                                                                    .getOnSuccessListener(TAG))
                                                            .addOnCanceledListener(
                                                                    DefaultListeners.getInstance()
                                                                            .getOnCanceledListener(TAG)
                                                            ).addOnFailureListener(
                                                            DefaultListeners.getInstance()
                                                                    .getOnFailureListener(TAG)
                                                    );
                                                    ;
                                                }
                                        ).addOnCanceledListener(
                                        DefaultListeners.getInstance()
                                                .getOnCanceledListener(TAG)
                                ).addOnFailureListener(
                                        DefaultListeners.getInstance()
                                                .getOnFailureListener(TAG)
                                );

                            } else {
                                Log.w(TAG, "Adding comment failed to get artifact " +
                                        "with id: " + artifactId);
                            }
                        })
                .addOnFailureListener(
                        DefaultListeners.getInstance().getOnFailureListener(TAG))
                .addOnCanceledListener(
                        DefaultListeners.getInstance().getOnCanceledListener(TAG));
    }

    /**
     * Create add a new like to existing artifact
     *
     * @param artifactId the artifact to add to
     * @param uid        the user who sent liked
     */
    public void addLike(String artifactId, String uid) {
        // Get the artifact
        mArtifactItemCollection
                .document(artifactId)
                .get().addOnSuccessListener(DefaultListeners.getInstance()
                .getOnSuccessListener(TAG))
                .addOnSuccessListener(
                        documentSnapshot -> {
                            if (documentSnapshot.exists() && documentSnapshot
                                    .toObject(ArtifactItem.class) != null) {
                                ArtifactItem artifactItem = documentSnapshot
                                        .toObject(ArtifactItem.class);
                                artifactItem.addLike(uid);
                                // Store likes, if successful, save to artifact
                                mArtifactItemCollection.document(
                                        artifactId
                                ).update("likes",
                                        artifactItem.getlikes())
                                        .addOnSuccessListener(DefaultListeners
                                                .getInstance()
                                                .getOnSuccessListener(TAG))
                                        .addOnCanceledListener(
                                                DefaultListeners.getInstance()
                                                        .getOnCanceledListener(TAG)
                                        ).addOnFailureListener(
                                        DefaultListeners.getInstance()
                                                .getOnFailureListener(TAG)
                                );
                            } else {
                                Log.w(TAG, "Adding likes failed to get artifact " +
                                        "with artifact id: " + artifactId + ", uid: " + uid);
                            }
                        })
                .addOnFailureListener(
                        DefaultListeners.getInstance().getOnFailureListener(TAG))
                .addOnCanceledListener(
                        DefaultListeners.getInstance().getOnCanceledListener(TAG));
    }

    /**
     * Create remove a new like to existing artifact
     *
     * @param artifactId the artifact to remove to
     * @param uid        the user who sent liked
     */
    public void removeLike(String artifactId, String uid) {
        // Get the artifact
        mArtifactItemCollection
                .document(artifactId)
                .get().addOnSuccessListener(DefaultListeners.getInstance()
                .getOnSuccessListener(TAG))
                .addOnSuccessListener(
                        documentSnapshot -> {
                            if (documentSnapshot.exists() && documentSnapshot
                                    .toObject(ArtifactItem.class) != null) {
                                ArtifactItem artifactItem = documentSnapshot
                                        .toObject(ArtifactItem.class);
                                artifactItem.removeLike(uid);
                                // Store likes, if successful, save to artifact
                                mArtifactItemCollection.document(
                                        artifactId
                                ).update("likes",
                                        artifactItem.getlikes())
                                        .addOnSuccessListener(DefaultListeners
                                                .getInstance()
                                                .getOnSuccessListener(TAG))
                                        .addOnCanceledListener(
                                                DefaultListeners.getInstance()
                                                        .getOnCanceledListener(TAG)
                                        ).addOnFailureListener(
                                        DefaultListeners.getInstance()
                                                .getOnFailureListener(TAG)
                                );
                            } else {
                                Log.w(TAG, "Removing likes failed to get artifact " +
                                        "with artifact id: " + artifactId + ", uid: " + uid);
                            }
                        })
                .addOnFailureListener(
                        DefaultListeners.getInstance().getOnFailureListener(TAG))
                .addOnCanceledListener(
                        DefaultListeners.getInstance().getOnCanceledListener(TAG));
    }


    public LiveData<List<ArtifactComment>> listenCommentByArtifact(String artifactItemId, String listenerIdentifier) {
        MutableLiveData<List<ArtifactComment>> mutableLiveData = new MutableLiveData<>();
        ListenerRegistration listenerRegistration = mArtifactCommentCollection
                .whereEqualTo("artifactItemId", artifactItemId)
                .addSnapshotListener(
                        (queryDocumentSnapshots, e) -> {
                            if (e == null && queryDocumentSnapshots != null) {
                                List<ArtifactComment> commentList = new ArrayList<>();
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    // Query is not empty, update value
                                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                                        if (documentSnapshot.exists()) {
                                            commentList.add(documentSnapshot.toObject(ArtifactComment.class));
                                        }
                                    }
                                }
                                mutableLiveData.setValue(commentList);
                            } else {
                                Log.w(TAG, "DB query result in error: " + e);
                            }
                        }
                );
        mListenerRegistrationMap.put(listenerIdentifier, listenerRegistration);
        return mutableLiveData;
    }
}
