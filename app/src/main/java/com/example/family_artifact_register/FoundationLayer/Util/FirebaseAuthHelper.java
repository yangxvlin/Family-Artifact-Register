package com.example.family_artifact_register.FoundationLayer.Util;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.example.family_artifact_register.MyApplication;
import com.example.family_artifact_register.Util.CacheDirectoryHelper;
import com.example.family_artifact_register.Util.Callback;
import com.example.family_artifact_register.Util.DownloadBroadcastHelper;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;

import static android.os.Environment.DIRECTORY_PICTURES;


public class FirebaseAuthHelper {
    // Return code if the user already exist.
    public static final int RESULT_USER_EXIST = 0;
    // Return code if the user doesn't exist before.
    public static final int RESULT_NEW_USER = -1;

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


    public void checkRegisterUser(FirebaseUser firebaseUser, Callback<Void> callback,
                                  int requestCode) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = firebaseUser.getUid();
        Uri photoUri = firebaseUser.getPhotoUrl();
        db.collection(DBConstant.USER_INFO)
                .document(uid)
                .get()
                .addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot != null && documentSnapshot.exists() &&
                                        documentSnapshot.toObject(UserInfo.class) != null) {
                                    Log.d(TAG, "User already exist: "
                                            + documentSnapshot.getData());
                                    callback.callback(requestCode, RESULT_USER_EXIST, null);
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
                                            .storeUserInfo(userInfo, 0,
                                                    (requestCode1, resultCode, data) -> {
                                                if (photoUri != null) {
                                                    retrieveSetPhoto(photoUri, userInfo);
                                                }
                                                callback.callback(requestCode1,
                                                        RESULT_NEW_USER, null);
                                            });
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
            Log.d(TAG, "Starting accessing DownloadManager and creating request for" +
                    "downloading user profile photo: " + photoUri.toString());
            File imageStorageDir = new File(MyApplication.getContext().getExternalCacheDir(),
                    MyApplication.getApplication().getPackageName()+"_tmp");
            if (!imageStorageDir.exists()) {
                //noinspection ResultOfMethodCallIgnored
                imageStorageDir.mkdirs();
            }

            // default image extension
            String imgExtension = ".png";

            if (photoUri.toString().contains(".gif"))
                imgExtension = ".gif";
            else if (photoUri.toString().contains(".jpg"))
                imgExtension = ".jpg";
            else if (photoUri.toString().contains(".3gp"))
                imgExtension = ".3gp";

            DownloadManager downloadManager = (DownloadManager) MyApplication
                    .getContext()
                    .getSystemService(Context.DOWNLOAD_SERVICE);

            Log.d(TAG, "creating request");
            Uri uri = Uri.parse(CacheDirectoryHelper
                            .getInstance()
                            .createNewFile(imgExtension)
                            .toString());
            String fileName = new File(uri.toString()).getName();
            // Create request
            DownloadManager.Request request = new DownloadManager.Request(photoUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                    DownloadManager.Request.NETWORK_MOBILE)
                    .setDestinationInExternalPublicDir(DIRECTORY_PICTURES +
                            File.separator, fileName)
                    .setDescription("externalProfilePhoto")
                    .setNotificationVisibility(
                            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            Log.d(TAG, "registering helper");
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
            Log.d(TAG, "Downloading user profile photo: " + photoUri.toString());
            downloadManager.enqueue(request);
        } catch (NullPointerException e) {
            Log.w(TAG, "Failed to get downloadManager: " + e.toString());
        }
    }
}
