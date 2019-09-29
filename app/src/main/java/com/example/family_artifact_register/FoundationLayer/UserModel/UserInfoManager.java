package com.example.family_artifact_register.FoundationLayer.UserModel;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.family_artifact_register.FoundationLayer.DBConstant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
    private Map<String, MutableLiveData<UserInfo>> userInfoMap = new HashMap<>();

    /**
     * Active listeners used (this should be cleared if not used)
     */
    private Map<String, Pair<ListenerRegistration, Integer>> listenerRegistrationMap;


    /**
     * The particular user that's using the app
     */
    private String currentUid;

    /**
     * The particular user that's using the app
     */
    private UserInfo currentUserInfo;

    /**
     * The database used.
     */
    private FirebaseFirestore db;

    private UserInfoManager() {
        currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        // Listen to current user data
        db.collection(DBConstant.USERS)
                .document(currentUid)
                .addSnapshotListener((documentSnapshot, e) -> {
                    // Catch and log the error
                    if (e != null) {
                        Log.e(TAG, "currentUid listen:error", e);
                        return;
                    }

                    // Successfully fetched data
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        currentUserInfo = documentSnapshot.toObject(UserInfo.class);
                    } else {
                        Log.e(TAG,"!!! get failed: user not exists " + currentUid, new Throwable(
                                "Current User not found! Error in Database detected!"
                        ));
                    }
                });

        listenerRegistrationMap = new HashMap<>();
    }

    /**
     * Get Detailed user information of some users
     * @param uid one user id
     * @return A LiveData object containing user information, that will be updated in real time!
     */
    public LiveData<UserInfo> getUserInfo(String uid) {
        if (uid.equals(currentUid)) {
            MutableLiveData<UserInfo> currentUserLiveData = new MutableLiveData<>();
            currentUserLiveData.setValue(currentUserInfo);
            return currentUserLiveData;
        }
        // If currently either this uid hasn't been listened yet
        if (!listenerRegistrationMap.containsKey(uid)) {
            // Create Entry if not in there
            if (!userInfoMap.containsKey(uid)) {
                userInfoMap.put(uid, new MutableLiveData<>());
            }
            // Not yet have it in cache
            ListenerRegistration snapshotListener = db
                    .collection(DBConstant.USERS)
                    .document(uid)
                    .addSnapshotListener((documentSnapshot, e) -> {
                // Catch and log the error
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }
                // Successfully fetched data
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    userInfoMap.get(uid).setValue(documentSnapshot.toObject(UserInfo.class));
                } else {
                    Log.d(TAG,"get failed: user not exists " + uid);
                }
            });
            // Register the listener with the number of users
            if (!listenerRegistrationMap.containsKey(uid)) {
                listenerRegistrationMap.put(uid, new Pair<>(snapshotListener, 1));
            } else {
                listenerRegistrationMap.put(uid,
                        new Pair<>(listenerRegistrationMap.get(uid).first,
                                listenerRegistrationMap.get(uid).second+1));
            }
        } else {
            listenerRegistrationMap.put(uid,
                    new Pair<>(listenerRegistrationMap.get(uid).first,
                            listenerRegistrationMap.get(uid).second+1));
        }
        return userInfoMap.get(uid);
    }

    /**
     * Get Detailed user information of some users
     * @param uids list of user id (duplication will be removed)
     * @return A List LiveData object containing user information, that will be updated in real time!
     */
    public List<LiveData<UserInfo>> getUserInfo(List<String> uids) {
        ArrayList<LiveData<UserInfo>> liveDataList = new ArrayList<>();
        for (String uid: new HashSet<>(uids)) {
            liveDataList.add(getUserInfo(uid));
        }
        return liveDataList;
    }

    /**
     * Preload the Cache with a list of user id and their info
     * @param uids The list of uid the user is loading
     */
    public void warmCache(ArrayList<String> uids) {
        for (String uid: uids) {
            db.collection("users").document(uid).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        // Successfully fetched data
                        if (!userInfoMap.containsKey(uid)) {
                            userInfoMap.put(uid, new MutableLiveData<>());
                        }
                        userInfoMap.get(uid).setValue(documentSnapshot.toObject(UserInfo.class));
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
        if (listenerRegistrationMap.containsKey(uid)) {
            listenerRegistrationMap.put(uid,
                    new Pair<>(listenerRegistrationMap.get(uid).first,
                            listenerRegistrationMap.get(uid).second-1));
            if (listenerRegistrationMap.get(uid).second == 0) {
                listenerRegistrationMap.get(uid).first.remove();
                listenerRegistrationMap.remove(uid);
            }
        } else {
            Log.w(TAG, "attempted to remove listener while no listener is attached");
        }
    }

    public void storeUserInfo(UserInfo userInfo) {
        String uid = userInfo.getUid();
        db.collection(DBConstant.USERS)
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
            db.collection(DBConstant.USERS).document(userInfo1.getUid()).update(UserInfo.FRIEND_UIDS,
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
            db.collection(DBConstant.USERS).document(userInfo2.getUid()).update(UserInfo.FRIEND_UIDS,
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
        for (String field: new String[]{UserInfo.DISPLAY_NAME, UserInfo.EMAIL,
                UserInfo.PHONE_NUMBER, UserInfo.UID}) {
            db.collection(DBConstant.USERS).whereEqualTo(field, query).get().addOnCompleteListener(
                    task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot == null || querySnapshot.isEmpty()) {
                                Log.i(TAG, "failed to find (" + field +") equal to (" + query + ")");
                                return;
                            }
                            for (DocumentSnapshot documentSnapshot: querySnapshot.getDocuments()) {
                                mutableLiveData
                                        .getValue()
                                        .add(documentSnapshot
                                                .toObject(UserInfo.class));
                            }
                        } else {
                            Log.d(TAG, task.getException() + " get failed with ");
                        }
                    }
            );
        }
        return mutableLiveData;
    }


    public void setUserName() {

    }
}
