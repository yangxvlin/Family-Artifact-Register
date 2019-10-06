package com.example.family_artifact_register.FoundationLayer.Util;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.family_artifact_register.Util.CacheDirectoryHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.nio.file.Path;

public class FirebaseStorageHelper {
    private static final String TAG = FirebaseStorageHelper.class.getSimpleName();

    private static final FirebaseStorageHelper ourInstance = new FirebaseStorageHelper();
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    public static FirebaseStorageHelper getInstance() {
        return ourInstance;
    }

    // Map Remote storage location and local storage location together
    private BiMap<String, Uri> remoteLocalBiMap = HashBiMap.create();

    // Finds the correct cache directory
    private CacheDirectoryHelper mCacheDirectoryHelper = CacheDirectoryHelper.getInstance();

    // Firebase storage location
    private StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();

    private FirebaseStorageHelper() {
        if (mCacheDirectoryHelper.getCacheDirectory() != null) {
            Log.w(TAG, "Failed to find Cache directory from cache helper! (null)");
            // TODO ... check null
        }
    }

    private static Uri checkAddUriScheme(Uri uri) {
        if (uri.getScheme() == null) {
            uri = Uri.parse("file://"+uri.toString());
        }
        return uri;
    }

    private String extractLocalUri(Uri localUri) {
        Path localFilePath = new File(localUri.toString()).toPath();
        Path localStoragePath = mCacheDirectoryHelper.getCacheDirectory().toPath();
        Path remotePath = localStoragePath.relativize(localFilePath);
        return remotePath.toString();
    }

    private Uri parseRemoteUrl(String remoteUrl) {
        Path remoteFilePath = new File(remoteUrl).toPath();
        Path localStoragePath = mCacheDirectoryHelper.getCacheDirectory().toPath();
        Path remotePath = localStoragePath.resolve(remoteFilePath);
        return checkAddUriScheme(Uri.parse(remotePath.toString()));
    }

    public Task<UploadTask.TaskSnapshot> uploadByUri(Uri uri) {
        if (remoteLocalBiMap.inverse().get(uri) != null) {
            // Found the local Uri in the map (thus already stored in remote)
            return null;
        } else {
            String remotePath = extractLocalUri(uri);
            Uri finalUri = checkAddUriScheme(uri);
            // Put in the relative path
            return mStorageReference.child(remotePath).putFile(uri)
                    .addOnSuccessListener(
                            taskSnapshot -> remoteLocalBiMap.put(remotePath, finalUri)
                    );
        }
    }

    /**
     * Load the remote url resource to local storage and set the Uri to it in the livedata
     * @param remoteUrl The remote Url to download resource with
     * @return LiveData of the destination Uri
     */
    public LiveData<Uri> loadByRemoteUri(String remoteUrl) {
        MutableLiveData<Uri> mutableLiveData = new MutableLiveData<>();
        if (remoteLocalBiMap.get(remoteUrl) != null) {
            // If already loaded
            mutableLiveData.setValue(remoteLocalBiMap.get(remoteUrl));
        } else {
            // If not loaded yet
            Uri localUri = parseRemoteUrl(remoteUrl);
            mStorageReference.getFile(localUri).addOnSuccessListener(
                    new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // set value in map (cache)
                            remoteLocalBiMap.put(remoteUrl, localUri);
                            // Notify observer
                            mutableLiveData.setValue(localUri);
                        }
                    }
            ).addOnFailureListener(
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Failed to get remote Url: " + remoteUrl);
                        }
                    }
            );
        }
        return mutableLiveData;
    }

    public String getRemoteByLocalUri(Uri uri) {
        return remoteLocalBiMap.inverse().get(uri);
    }
}
