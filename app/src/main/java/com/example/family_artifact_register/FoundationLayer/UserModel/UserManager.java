package com.example.family_artifact_register.FoundationLayer.UserModel;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class UserManager {
    private static final String TAG = UserManager.class.getSimpleName();
    private static UserManager instance;

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }
    private Map<String, UserInfo> userInfoMap = new HashMap<>();
    private String uid;
    private FirebaseFirestore db;

    private UserManager() {
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
    }

    public void getUserInfo(String id, Callback<UserInfo> callback) {
        if (userInfoMap.containsKey(id)) {
            if (callback != null) callback.callback(userInfoMap.get(id));
            return;
        }

        db.collection("users").document(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc = task.getResult();
                if (doc.exists()) {
                    UserInfo userInfo;
                    if (userInfoMap.containsKey(id)) {
                        userInfo = userInfoMap.get(id);
                    } else {
                        userInfo = doc.toObject(UserInfo.class);
                        userInfoMap.put(id, userInfo);
                    }
                    if (callback!=null) callback.callback(userInfo);
                } else {
                    Log.d(TAG,"get failed: user not exists " + id);
                }
            } else {
                Log.d(TAG, task.getException() + " get failed with ");
            }
        });
    }

    public void getUserInfo(ArrayList<String> ids,
                            Callback<Map<String, UserInfo>> callback) {
        Map<String, UserInfo> res = new HashMap<>();
        for (String id: new HashSet<>(ids)) {
            getUserInfo(id, info -> {
                res.put(id, info);
                if (res.size()==ids.size()) {
                    callback.callback(res);
                }
            });
        }
    }

    public void warmCache(ArrayList<String> ids) {
        for (String id: ids) {
            getUserInfo(id,null);
        }
    }

    public interface Callback<T> {
        void callback(T t);
    }
}
