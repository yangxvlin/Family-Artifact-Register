package com.example.family_artifact_register.FoundationLayer.UserModel;

import android.net.Uri;
import android.util.Log;

import com.example.family_artifact_register.FoundationLayer.DBConstant;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class FirebaseAuthHelper {
    private static final String TAG = FirebaseAuthHelper.class.getSimpleName();

    private static final FirebaseAuthHelper instance = new FirebaseAuthHelper();

    public static FirebaseAuthHelper getInstance() {
        return instance;
    }

    public UserInfo userFromFirebaseUser(FirebaseUser firebaseUser) {
        Uri photoUri = firebaseUser.getPhotoUrl();
        return new UserInfo(
                firebaseUser.getUid(),
                firebaseUser.getDisplayName(),
                firebaseUser.getEmail(),
                firebaseUser.getPhoneNumber(),
                photoUri != null ? photoUri.toString() : null,
                new HashMap<>(),
                new HashMap<>());
    }


    public void checkRegisterUser(FirebaseUser firebaseUser) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = firebaseUser.getUid();
        db
                .collection(DBConstant.USERS)
                .document(uid)
                .get()
                .addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot != null && documentSnapshot.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());
                                } else {
                                    Log.d(TAG, "No such user info, adding to db");
                                    UserInfo userInfo = FirebaseAuthHelper
                                            .getInstance()
                                            .userFromFirebaseUser(
                                                    firebaseUser
                                            );
                                    UserInfoManager.getInstance().storeUserInfo(userInfo);
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        });
    }
}
