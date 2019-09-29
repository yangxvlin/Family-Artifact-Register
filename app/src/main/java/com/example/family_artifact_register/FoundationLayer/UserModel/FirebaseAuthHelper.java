package com.example.family_artifact_register.FoundationLayer.UserModel;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class FirebaseAuthHelper {
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
}
