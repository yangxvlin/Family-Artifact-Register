package com.example.family_artifact_register.FoundationLayer.UserModel;

import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.family_artifact_register.FoundationLayer.Util.DBConstant;
import com.example.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;
import com.example.family_artifact_register.FoundationLayer.Util.LiveDataListDispatchHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Class for managing user information
 */
public class UserInfoManager {
    private static final String TAG = UserInfoManager.class.getSimpleName();
    private static UserInfoManager instance;

    public static UserInfoManager getInstance() {
        if (instance == null) {
            instance = new UserInfoManager();
        }
        return instance;
    }

    /**
     * A map storing all retrieved user information
     */
    private Map<String, MutableLiveData<UserInfo>> mUserInfoMap = new HashMap<>();

    /**
     * Active listeners used (this should be cleared if not used)
     */
    private Map<String, Pair<ListenerRegistration, Integer>> mListenerRegistrationMap;


    /**
     * The particular user that's using the app
     */
    private String mCurrentUid;

    /**
     * The particular user that's using the app
     */
    private MutableLiveData<UserInfo> mCurrentUserInfoLiveData = new MutableLiveData<>();

    /**
     * The database reference used.
     */
    private CollectionReference mUserCollection;

    /**
     * Storage reference for storing photo
     */
    private StorageReference mPhotoStorageReference;


    private UserInfoManager() {
        mCurrentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUserCollection = FirebaseFirestore.getInstance().collection(DBConstant.USER_INFO);

        // Listen to current user data
        mUserCollection
                .document(mCurrentUid)
                .addSnapshotListener((documentSnapshot, e) -> {
                    // Catch and log the error
                    if (e != null) {
                        Log.e(TAG, "mCurrentUid listen:error", e);
                        return;
                    }
                    // Successfully fetched data
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        mCurrentUserInfoLiveData.setValue(documentSnapshot.toObject(UserInfo.class));
                    } else {
                        Log.e(TAG,"get failed: current user does not exist: " + mCurrentUid);
                    }
                });

        mPhotoStorageReference = FirebaseStorage.getInstance()
                .getReference(DBConstant.USER_INFO_PHOTO_URL);
        mListenerRegistrationMap = new HashMap<>();
    }

    /**
     * Get Uid of current user
     * @return uid of current user
     */
    public String getCurrentUid() {
        return mCurrentUid;
    }

    /**
     * Get current user
     * @return current user, UserInfo object
     */
    public UserInfo getCurrentUserInfo() {
        return mCurrentUserInfoLiveData.getValue();
    }

    public LiveData<UserInfo> getUserInfo(String uid) {
        // TODO
        return null;
    }

    public LiveData<UserInfo> getUserInfo(List<String> uid) {
        // TODO
        return null;
    }

    /**
     * Get Detailed user information of some users
     * @param uid one user id
     * @return A LiveData object containing user information, that will be updated in real time!
     *
     */
    public LiveData<UserInfo> listenUserInfo(String uid) {
        if (uid.equals(mCurrentUid)) {
            return mCurrentUserInfoLiveData;
        }
        // If currently either this uid hasn't been listened yet
        if (!mListenerRegistrationMap.containsKey(uid)) {
            // Create Entry if not in there
            if (!mUserInfoMap.containsKey(uid)) {
                mUserInfoMap.put(uid, new MutableLiveData<>());
            }
            // Not yet have it in cache
            ListenerRegistration snapshotListener = mUserCollection
                    .document(uid)
                    .addSnapshotListener((documentSnapshot, e) -> {
                // Catch and log the error
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }
                // Successfully fetched data
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    mUserInfoMap.get(uid).setValue(documentSnapshot.toObject(UserInfo.class));
                } else {
                    Log.d(TAG,"get failed: user not exists " + uid);
                }
            });
            // Register the listener with the number of users
            if (!mListenerRegistrationMap.containsKey(uid)) {
                mListenerRegistrationMap.put(uid, new Pair<>(snapshotListener, 1));
            } else {
                mListenerRegistrationMap.put(uid,
                        new Pair<>(mListenerRegistrationMap.get(uid).first,
                                mListenerRegistrationMap.get(uid).second+1));
            }
        } else {
            mListenerRegistrationMap.put(uid,
                    new Pair<>(mListenerRegistrationMap.get(uid).first,
                            mListenerRegistrationMap.get(uid).second+1));
        }
        return mUserInfoMap.get(uid);
    }

    /**
     * Get Detailed user information of some users
     * @param uids list of user id (duplication will be removed)
     * @return A List LiveData object containing user information, that will be updated in real time!
     *
     */
    public List<LiveData<UserInfo>> listenUserInfo(List<String> uids) {
        ArrayList<LiveData<UserInfo>> liveDataList = new ArrayList<>();
        for (String uid: new HashSet<>(uids)) {
            liveDataList.add(listenUserInfo(uid));
        }
        return liveDataList;
    }

    /**
     * Preload the Cache with a list of user id and their info
     * @param uids The list of uid the user is loading
     */
    public void warmCache(ArrayList<String> uids) {
        for (String uid: uids) {
            mUserCollection.document(uid).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        // Successfully fetched data
                        if (!mUserInfoMap.containsKey(uid)) {
                            mUserInfoMap.put(uid, new MutableLiveData<>());
                        }
                        mUserInfoMap.get(uid).setValue(documentSnapshot.toObject(UserInfo.class));
                    } else {
                        Log.d(TAG,"get failed: user not exists " + uid);
                    }
                } else {
                    Log.d(TAG, task.getException() + " get failed with ");
                }
            });
        }
    }

    /**
     * Clean the cache built by this adapter. Also clears ALL listeners
     */
    public void removeListener(String uid) {
        if (mListenerRegistrationMap.containsKey(uid)) {
            mListenerRegistrationMap.put(uid,
                    new Pair<>(mListenerRegistrationMap.get(uid).first,
                            mListenerRegistrationMap.get(uid).second-1));
            if (mListenerRegistrationMap.get(uid).second == 0) {
                mListenerRegistrationMap.get(uid).first.remove();
                mListenerRegistrationMap.remove(uid);
            }
        } else {
            Log.w(TAG, "attempted to remove listener while no listener is attached");
        }
    }

    public void storeUserInfo(UserInfo userInfo) {
        String uid = userInfo.getUid();
        mUserCollection
                .document(uid)
                .set(userInfo)
                .addOnSuccessListener(aVoid ->
                        Log.d(TAG, "User information"
                                + userInfo.toString()
                                + "successfully written!"))
                .addOnFailureListener(e ->
                        Log.w(TAG, "Error writing user info" +
                                userInfo.toString(), e));
    }

    /**
     * Make userInfo1 and userInfo2 become friend and post to server
     *
     * @param userInfo1 The first user to connect
     * @param userInfo2 The second user to connect
     *
     * @deprecated Temporary solution for this, will be modified in the future (this api will then be private)
     */
    @Deprecated()
    public void addFriend(UserInfo userInfo1, UserInfo userInfo2) {
        // Make them friend
        if (userInfo1.addFriend(userInfo2.getUid())) {
            // Save User info to server
            mUserCollection
                    .document(userInfo1.getUid())
                    .update(UserInfo.FRIEND_UIDS,
                    userInfo1.getFriendUids()).addOnSuccessListener(aVoid ->
                    Log.d(TAG, "User friend list"
                            + userInfo1.toString()
                            + "successfully written!"))
                    .addOnFailureListener(e ->
                            Log.w(TAG, "Error writing friend list" +
                                    userInfo1.toString(), e));
        }

        // Make them friend
        if (userInfo2.addFriend(userInfo1.getUid())) {
            // Save User info to server
            mUserCollection
                    .document(userInfo2.getUid())
                    .update(UserInfo.FRIEND_UIDS,
                    userInfo2.getFriendUids()).addOnSuccessListener(aVoid ->
                    Log.d(TAG, "User friend list"
                            + userInfo2.toString()
                            + "successfully written!"))
                    .addOnFailureListener(e ->
                            Log.w(TAG, "Error writing friend list" +
                                    userInfo2.toString(), e));
        }
    }

    /**
     * Search user by a specific query word.
     * @param query displayName / email / phone number / uid to search for
     * @return List of LiveData of UserInfo that matched the query
     */
    public LiveData<List<UserInfo>> searchUserInfo(String query) {
        MutableLiveData<List<UserInfo>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(new ArrayList<>());
        LiveDataListDispatchHelper<UserInfo> liveDataListDispatchHelper =
                new LiveDataListDispatchHelper<>(mutableLiveData);
        for (String field: new String[]{UserInfo.DISPLAY_NAME, UserInfo.EMAIL,
                UserInfo.PHONE_NUMBER, UserInfo.UID}) {
            // increase the counter
            liveDataListDispatchHelper.addWaitingTask();

            mUserCollection.whereEqualTo(field, query).get().addOnCompleteListener(
                    task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot == null || querySnapshot.isEmpty()) {
                                Log.i(TAG, "failed to find (" + field +") equal to (" + query + ")");
                            } else {
                                for (DocumentSnapshot documentSnapshot: querySnapshot.getDocuments()) {
                                    UserInfo userInfo = documentSnapshot.toObject(UserInfo.class);
                                    Log.i(TAG, "found (" + field +") equal to (" + query + ")" +
                                            "user: "+ userInfo.toString());

                                    // Add info to the dispatcher
                                    liveDataListDispatchHelper.addResult(userInfo);
                                }
                            }
                        } else {
                            Log.d(TAG, task.getException() + " get failed with ");
                        }

                        // decrease the counter and dispatch if needed
                        liveDataListDispatchHelper.completeWaitingTaskAndDispatch();
                    }
            );
        }
        Log.d(TAG, mutableLiveData.getValue().toString());
        return mutableLiveData;
    }

    /**
     * Change user name using this function
     * @param displayName The new display name to be changed to
     */
    public void setDisplayName(String displayName) {
        if (mCurrentUserInfoLiveData.getValue() == null) {
            Log.w(TAG, "Hasn't Fetched data of current user. Please try again later");
            return;
        }
        // Check if same as previous one
        if (!displayName.equals(mCurrentUserInfoLiveData.getValue().getDisplayName())) {
            UserInfo currentUserInfo = mCurrentUserInfoLiveData.getValue();
            currentUserInfo.setDisplayName(displayName);
            // Update
            mUserCollection
                    .document(mCurrentUid)
                    .update(UserInfo.DISPLAY_NAME,
                    displayName).addOnSuccessListener(aVoid ->
                    Log.d(TAG, "User information"
                            + currentUserInfo.toString()
                            + "successfully written!"))
                    .addOnFailureListener(e ->
                            Log.w(TAG, "Error writing user info" +
                                    currentUserInfo.toString(), e));
        }
    }

    /**
     * Set the Photo Uri for current user
     * @param photoUri The photo uri to set with
     */
    public void setPhoto(Uri photoUri) {
        if (mCurrentUserInfoLiveData.getValue() == null) {
            Log.w(TAG, "Hasn't Fetched data of current user. Please try again later");
            return;
        }
        UserInfo currentUserInfo = mCurrentUserInfoLiveData.getValue();
        Pair<String, UploadTask> uploadTaskPair =
                FirebaseStorageHelper.getInstance().uploadByUri(photoUri, mPhotoStorageReference);
        UploadTask uploadTask = uploadTaskPair.second;

        uploadTask.addOnFailureListener(
                e -> Log.w(TAG, "Error writing user Image Uri to Storage failed" +
                        photoUri.toString(), e)
        ).onSuccessTask(task ->
                mPhotoStorageReference.child(uploadTaskPair.first)
                        .getDownloadUrl())
                .addOnSuccessListener(task -> {
                    String photoUrl = task.toString();
                    currentUserInfo.setPhotoUrl(photoUrl);
        }).addOnFailureListener(e ->
                Log.w(TAG, "Error update user new Uri info to FireStore failed" +
                        currentUserInfo.toString(), e));
    }

    /**
     * Add an ArtifactId to current user and push to database
     * @param artifactId String ArtifactId to add
     */
    public void addArtifactId(String artifactId) {
        UserInfo currentUserInfo = getCurrentUserInfo();
        // Check and add artifact id
        if (currentUserInfo.addArtifact(artifactId)) {
            // ArtifactId not in this user yet. Add and push to database
            Log.d(TAG, currentUserInfo.getArtifactIds().toString());
            mUserCollection.document(mCurrentUid).update(UserInfo.ARTIFACT_IDS,
                    currentUserInfo.getArtifactIds()).addOnFailureListener(e ->
                    Log.w(TAG, "Error update user new ArtifactId to FireStore failed" +
                            currentUserInfo.toString(), e));
        }
    }
}
