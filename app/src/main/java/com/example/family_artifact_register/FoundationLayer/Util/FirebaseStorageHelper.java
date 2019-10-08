package com.example.family_artifact_register.FoundationLayer.Util;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

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
import java.util.List;

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
        if (mCacheDirectoryHelper.getCacheDirectory() == null) {
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

    private boolean uriStored(Uri uri) {
        // If True, already in cache, that means this is stored in database already
        return remoteLocalBiMap.containsKey(uri.toString()) || remoteLocalBiMap.inverse().containsKey(uri);
    }

    private Uri parseRemoteUrl(String remoteUrl) {
        Path remoteFilePath = new File(remoteUrl).toPath();
        Path localStoragePath = mCacheDirectoryHelper.getCacheDirectory().toPath();
        Path remotePath = localStoragePath.resolve(remoteFilePath);
        return checkAddUriScheme(Uri.parse(remotePath.toString()));
    }

    public Task<UploadTask.TaskSnapshot> uploadByUri(Uri uri) {
        if (uriStored(uri)) {
            Log.d(TAG, "Found in BiMap");
            // Found the local Uri in the map (thus already stored in remote)
            return null;
        } else {
            String remotePath = extractLocalUri(uri);
            Uri finalUri = checkAddUriScheme(uri);
            Log.d(TAG, "remotePath: " + remotePath + ", finalUri" + finalUri);
            // Put in the relative path
            return mStorageReference.child(remotePath).putFile(finalUri)
                    .addOnSuccessListener(
                            taskSnapshot -> {
                                remoteLocalBiMap.put(remotePath, finalUri);
                                Log.d(TAG, "Successfully uploaded, Adding to BiMap " +
                                        "remotePath: " + remotePath + ", finalUri" + finalUri + "\n"
                                + remoteLocalBiMap.toString());
                            }
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
        Uri localUri = parseRemoteUrl(remoteUrl);
        if (remoteLocalBiMap.get(remoteUrl) != null) {
            // If already loaded
            mutableLiveData.setValue(remoteLocalBiMap.get(remoteUrl));
        } else if (new File(localUri.toString()).exists()) {
            // Exist in local
            mutableLiveData.setValue(localUri);
            // add to mapping
            remoteLocalBiMap.put(remoteUrl, localUri);
        } else {
            // If not loaded yet
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

    /**
     * Load the remote url resource to local storage and set the Uri to it in the livedata
     * @param remoteUrls The remote Url to download resource with
     * @return LiveData of the destination Uri
     */
    public LiveData<List<Uri>> loadByRemoteUri(List<String> remoteUrls) {
        MutableLiveData<List<Uri>> mutableLiveData = new MutableLiveData<>();
        LiveDataListDispatchHelper<Uri> liveDataListDispatchHelper =
                new LiveDataListDispatchHelper<>(mutableLiveData);

        for (String remoteUrl : remoteUrls) {
            LiveData<Uri> liveData = loadByRemoteUri(remoteUrl);
            liveDataListDispatchHelper.addWaitingTask();
            liveData.observeForever(new Observer<Uri>() {
                @Override
                public void onChanged(Uri uri) {
                    liveDataListDispatchHelper.addResultAfterTaskCompletion(uri);
                    liveData.removeObserver(this);
                }
            });
        }
        return mutableLiveData;
    }

    public String getRemoteByLocalUri(Uri uri) {
        uri = checkAddUriScheme(uri);
        return remoteLocalBiMap.inverse().get(uri);
    }
}
