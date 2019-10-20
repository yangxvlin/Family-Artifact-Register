package com.unimelb.family_artifact_register.FoundationLayer.UserModel;

import android.net.Uri;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.unimelb.family_artifact_register.FoundationLayer.Util.DBConstant;
import com.unimelb.family_artifact_register.FoundationLayer.Util.DefaultListeners;
import com.unimelb.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;
import com.unimelb.family_artifact_register.FoundationLayer.Util.LiveDataListDispatchHelper;
import com.unimelb.family_artifact_register.Util.Callback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
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
    // Return code if the request for userInfo is ok (successful)
    public static final int RESULT_OK = 0;
    // Return code if the request for userInfo is cancelled (unsuccessful)
    public static final int RESULT_CANCELLED = -1;
    // Return code if the request for userInfo is failed (unsuccessful)
    public static final int RESULT_FAILURE = 1;

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


    private UserInfoManager() {
        setupOrUpdateUserDatabase(FirebaseAuth.getInstance());
        // Set the state of UserInfoManager when the auth state changes
        FirebaseAuth.getInstance().addAuthStateListener(
                this::setupOrUpdateUserDatabase
        );
    }

    private void setupOrUpdateUserDatabase(FirebaseAuth firebaseAuth) {
        // Set up the user collection for firebase
        mUserCollection = FirebaseFirestore.getInstance()
                .collection(DBConstant.USER_INFO);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user!=null) {
            Log.i(TAG, "firebase auth: " + user.getUid());
            // Already logged in
            mCurrentUid = user.getUid();

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
                            mCurrentUserInfoLiveData.setValue(documentSnapshot
                                    .toObject(UserInfo.class));
                        } else {
                            Log.w(TAG,"Listen currentUid failed: current user does not exist: "
                                    + mCurrentUid);
                        }
                    });
            // Reset registration Map
            mListenerRegistrationMap = new HashMap<>();
        } else {
            Log.i(TAG, "firebase auth: no user");
            // Cleanup the listeners
            for (Pair<ListenerRegistration, Integer> listenerRegistrationIntegerPair:
                    mListenerRegistrationMap.values()) {
                // clean the listener
                listenerRegistrationIntegerPair.first.remove();
            }
            mListenerRegistrationMap = new HashMap<>();
        }
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
        LiveData<UserInfo> userInfoLiveData = listenUserInfo(uid);
        userInfoLiveData.observeForever(
                new Observer<UserInfo>() {
                    @Override
                    public void onChanged(UserInfo userInfo) {
                        removeListener(uid);
                        userInfoLiveData.removeObserver(this);
                    }
                }
        );
        return userInfoLiveData;
    }

    public LiveData<UserInfo> getUserInfo(List<String> uid) {
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

    /**
     * Store the given userInfo into database without callback
     * @param userInfo The userInfo to store
     */
    public void storeUserInfo(UserInfo userInfo) {
        String uid = userInfo.getUid();
        storeUserInfo(userInfo, 0,null);
    }

    /**
     * Store the given userInfo into database with callback
     * @param userInfo The userInfo to store
     * @param requestCode specifier for the db request
     * @param userInfoCallback function to invoke after completion
     */
    // FIXME very very bad database practice!
    @Deprecated
    public void storeUserInfo(UserInfo userInfo, int requestCode, Callback<Void> userInfoCallback) {
        String uid = userInfo.getUid();
        mUserCollection
                .document(uid)
                .set(userInfo)
                .addOnCompleteListener(
                        task -> {
                            int resultCode = RESULT_FAILURE;
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User information"
                                        + userInfo.toString()
                                        + "successfully written!");
                                // FIXME temporary solution for when new user just registered
                                if (getCurrentUid() != null && getCurrentUid().equals(uid)) {
                                    mCurrentUserInfoLiveData.setValue(userInfo);
                                }
                                resultCode = RESULT_OK;
                            } else if (task.isCanceled()) {
                                Log.w(TAG, "Error writing user info" +
                                        userInfo.toString(), task.getException());
                                resultCode = RESULT_CANCELLED;
                            }
                            if (userInfoCallback != null) {
                                userInfoCallback.callback(requestCode, resultCode, task.getResult());
                            }
                        }
                );
    }

    /**
     * Make userInfo1 and userInfo2 become friend and post to server
     *
     * @param userInfo1 The first user to connect
     * @param userInfo2 The second user to connect
     *
     */
    private void addFriend(UserInfo userInfo1, UserInfo userInfo2) {
        // Make them friend
        if (userInfo1.addFriendUid(userInfo2.getUid())) {
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
        if (userInfo2.addFriendUid(userInfo1.getUid())) {
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
     * Send an invitation from Current User to the other User
     *
     * @param otherUid The other userId to send invitation to
     *
     */
    public void sendFriendInvitation(String otherUid) {
        String currentUid = getCurrentUid();
        if (currentUid == null) {
            Log.w(TAG, "There is no currentUid yet!");
            return;
        }
        LiveData<UserInfo> userInfoLiveData = getUserInfo(otherUid);
        userInfoLiveData.observeForever(
                new Observer<UserInfo>() {
                    @Override
                    public void onChanged(UserInfo userInfo) {
                        // Not already added
                        if (userInfo.addFriendInvitation(currentUid)) {
                            mUserCollection
                                    .document(otherUid)
                                    .update(UserInfo.FRIEND_INVITATIONS,
                                            userInfo.getFriendInvitations())
                                    .addOnSuccessListener(DefaultListeners.getInstance()
                                            .getOnSuccessListener(TAG))
                                    .addOnFailureListener(DefaultListeners.getInstance()
                                            .getOnFailureListener(TAG))
                                    .addOnCanceledListener(DefaultListeners.getInstance()
                                            .getOnCanceledListener(TAG));
                        }
                        userInfoLiveData.removeObserver(this);
                    }
                }
        );
    }

    /**
     * Accept an invitation from Current User to the other User
     *
     * @param otherUid The other userId to send invitation to
     *
     */
    public void acceptFriendInvitation(String otherUid) {
        UserInfo currentUserInfo = getCurrentUserInfo();
        if (currentUserInfo != null) {
            if (currentUserInfo.getFriendInvitations().containsKey(otherUid)) {
                // Get other User Info
                LiveData<UserInfo> otherUserInfoLiveData = getUserInfo(otherUid);
                otherUserInfoLiveData.observeForever(
                        new Observer<UserInfo>() {
                            @Override
                            public void onChanged(UserInfo otherUserInfo) {
                                // Add friend
                                addFriend(getCurrentUserInfo(), otherUserInfo);

                                // Remove invitation from the invitation map
                                currentUserInfo.getFriendInvitations().remove(otherUid);
                                mUserCollection.document(getCurrentUid())
                                        .update(UserInfo.FRIEND_INVITATIONS,
                                                currentUserInfo.getFriendInvitations())
                                        .addOnSuccessListener(DefaultListeners.getInstance()
                                                .getOnSuccessListener(TAG))
                                        .addOnFailureListener(DefaultListeners.getInstance()
                                                .getOnFailureListener(TAG))
                                        .addOnCanceledListener(DefaultListeners.getInstance()
                                                .getOnCanceledListener(TAG));
                                otherUserInfoLiveData.removeObserver(this);
                            }
                        }
                );
            }
        }
    }

    /**
     * Search user by a specific query word.
     * @param query displayName / email / phone number / uid to search for
     * @return List of LiveData of UserInfo that matched the query
     */
    public LiveData<List<UserInfo>> searchUserInfo(String query) {
        MutableLiveData<List<UserInfo>> mutableLiveData = new MutableLiveData<>();
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
                                Log.i(TAG, "failed to find (" + field +") " +
                                        "equal to (" + query + ")");
                            } else {
                                for (DocumentSnapshot documentSnapshot: querySnapshot.getDocuments()) {
                                    UserInfo userInfo = documentSnapshot.toObject(UserInfo.class);
                                    Log.i(TAG, "found (" + field +") equal to (" + query + ")"
                                            + "user: "+ userInfo.toString());

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
     *
     * @param photoUri The photo uri to set with (has to be local)
     */
    @Deprecated()
    public void setPhoto(Uri photoUri, UserInfo userInfo) {
        // TODO this part is potentially dangerous to async error
        Task<UploadTask.TaskSnapshot> uploadTask = FirebaseStorageHelper
                .getInstance()
                .uploadByUri(photoUri);
        if (uploadTask != null) {
            uploadTask.addOnFailureListener(
                    e -> Log.w(TAG, "Error writing user Image Uri to Storage failed" +
                            photoUri.toString(), e)
            ).addOnSuccessListener(taskSnapshot -> {
                // Change and store the user info to db after image complete
                userInfo
                        .setPhotoUrl(FirebaseStorageHelper
                                .getInstance()
                                .getRemoteByLocalUri(photoUri));
                storeUserInfo(userInfo);
            })
                    .addOnFailureListener(e ->
                            Log.w(TAG, "Error update user new Uri info to FireStore failed" +
                                    userInfo.toString(), e));
        } else {
            // Photo already stored, simply store user information
            userInfo
                    .setPhotoUrl(FirebaseStorageHelper
                            .getInstance()
                            .getRemoteByLocalUri(photoUri));
            storeUserInfo(userInfo);
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
        setPhoto(photoUri, currentUserInfo);
    }

    /**
     * Add an ArtifactId to current user and push to database
     * @param artifactId String ArtifactId to add
     */
    public void addArtifactItemId(String artifactId) {
        UserInfo currentUserInfo = getCurrentUserInfo();
        // Check and add artifact id
        if (currentUserInfo.addArtifactItemId(artifactId)) {
            // ArtifactId not in this user yet. Add and push to database
            Log.d(TAG, currentUserInfo.getArtifactItemIds().toString());
            mUserCollection.document(mCurrentUid).update(UserInfo.ARTIFACT_ITEM_IDS,
                    currentUserInfo.getArtifactItemIds()).addOnFailureListener(e ->
                    Log.w(TAG, "Error update user new ArtifactItemId to FireStore failed" +
                            currentUserInfo.toString(), e));
        }
    }

    /**
     * Add an ArtifactId to current user and push to database
     * @param artifactId String ArtifactId to add
     */
    public void addArtifactTimelineId(String artifactId) {
        UserInfo currentUserInfo = getCurrentUserInfo();
        // Check and add artifact id
        if (currentUserInfo.addArtifactTimelineId(artifactId)) {
            // ArtifactId not in this user yet. Add and push to database
            Log.d(TAG, currentUserInfo.getArtifactTimelineIds().toString());
            mUserCollection.document(mCurrentUid).update(UserInfo.ARTIFACT_TIMELINE_IDS,
                    currentUserInfo.getArtifactTimelineIds()).addOnFailureListener(e ->
                    Log.w(TAG, "Error update user new ArtifactTimelineId to FireStore failed" +
                            currentUserInfo.toString(), e));
        }
    }
}
