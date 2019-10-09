package com.example.family_artifact_register.FoundationLayer.Util;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.example.family_artifact_register.MyApplication;
import com.example.family_artifact_register.Util.CacheDirectoryHelper;
import com.example.family_artifact_register.Util.DownloadBroadcastHelper;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class FirebaseAuthHelper {
    private static final String TAG = FirebaseAuthHelper.class.getSimpleName();

    private static final FirebaseAuthHelper instance = new FirebaseAuthHelper();

    public static FirebaseAuthHelper getInstance() {
        return instance;
    }

    /**
     * Construct a new user based on a FirebaseUser object.
     * @param firebaseUser The FirebaseUser to convert from
     * @return The new user object constructed from FirebaseUser
     */
    public UserInfo basicUserInfoFromFirebaseUser(FirebaseUser firebaseUser) {
        return UserInfo.newInstance(
                firebaseUser.getUid(),
                firebaseUser.getDisplayName(),
                firebaseUser.getEmail(),
                firebaseUser.getPhoneNumber());
    }


    public void checkRegisterUser(FirebaseUser firebaseUser) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = firebaseUser.getUid();
        db.collection(DBConstant.USER_INFO)
                .document(uid)
                .get()
                .addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot != null && documentSnapshot.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: "
                                            + documentSnapshot.getData());
                                } else {
                                    Log.d(TAG, "No such user info, adding to db");
                                    UserInfo userInfo = FirebaseAuthHelper
                                            .getInstance()
                                            .basicUserInfoFromFirebaseUser(
                                                    firebaseUser
                                            );
                                    // Store the info
                                    UserInfoManager
                                            .getInstance()
                                            .storeUserInfo(userInfo, 0, null);
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        });

    }

    /**
     * Retrieve and set Photo uri of a given FirebaseUser
     * @param photoUri The photoUri to retrieve and set
     */
    private void retrieveSetPhoto(Uri photoUri, UserInfo userInfo) {
        try {
            DownloadManager downloadManager = (DownloadManager) MyApplication
                    .getContext()
                    .getSystemService(Context.DOWNLOAD_SERVICE);

            // Create request
            DownloadManager.Request request = new DownloadManager.Request(photoUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                    DownloadManager.Request.NETWORK_MOBILE)
                    .setDestinationUri(Uri.parse(CacheDirectoryHelper
                            .getInstance()
                            .createNewFile()
                            .toString()))
                    .setDescription("externalProfilePhoto")
                    .setNotificationVisibility(
                            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            // Add callback to the retrieval of url
            DownloadBroadcastHelper.getInstance().addCallback(
                    new DownloadBroadcastHelper.DownloadCallback() {
                        @Override
                        public void callback(long downloadId, Uri data) {
                            UserInfoManager.getInstance().setPhoto(data, userInfo);
                            // Remove this download callback
                            DownloadBroadcastHelper.getInstance().removeCallback(this);
                        }
                    }
            );
            downloadManager.enqueue(request);
        } catch (NullPointerException e) {
            Log.w(TAG, "Failed to get downloadManager: " + e.toString());
        }
    }
}
