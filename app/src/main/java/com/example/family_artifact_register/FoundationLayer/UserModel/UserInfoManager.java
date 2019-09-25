package com.example.family_artifact_register.FoundationLayer.UserModel;

import android.util.Log;

import com.example.family_artifact_register.FoundationLayer.DBConstant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
     * A static map storing all retrieved user information
     */
    private static Map<String, UserInfo> userInfoMap = new HashMap<>();

    /**
     * The particular user that's using the app
     */
    private String uid;

    /**
     * The database used.
     */
    private FirebaseFirestore db;

    private UserInfoManager() {
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
    }

    public void getUserInfo(String uid, Callback<UserInfo> callback) {
        if (userInfoMap.containsKey(uid)) {
            if (callback != null) callback.callback(userInfoMap.get(uid));
            return;
        }

        db.collection("users").document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc = task.getResult();
                if (doc.exists()) {
                    UserInfo userInfo;
                    if (userInfoMap.containsKey(uid)) {
                        userInfo = userInfoMap.get(uid);
                    } else {
                        userInfo = doc.toObject(UserInfo.class);
                        userInfoMap.put(uid, userInfo);
                    }
                    if (callback!=null) callback.callback(userInfo);
                } else {
                    Log.d(TAG,"get failed: user not exists " + uid);
                }
            } else {
                Log.d(TAG, task.getException() + " get failed with ");
            }
        });
    }

    /**
     * Get Detailed user information of some users
     * @param uids list of user id
     * @param callback
     */
    public void getUserInfo(List<String> uids,
                            Callback<Map<String, UserInfo>> callback) {
        Map<String, UserInfo> res = new HashMap<>();
        for (String uid: new HashSet<>(uids)) {
            getUserInfo(uid, info -> {
                res.put(uid, info);
                if (res.size()==uids.size()) {
                    callback.callback(res);
                }
            });
        }
    }

    /**
     * Preload the Cache with a list of user id and their info
     * @param uids The list of uid the user is loading
     */
    public void warmCache(ArrayList<String> uids) {
        for (String uid: uids) {
            getUserInfo(uid,null);
        }
    }

    public interface Callback<T> {
        void callback(T t);
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
}
