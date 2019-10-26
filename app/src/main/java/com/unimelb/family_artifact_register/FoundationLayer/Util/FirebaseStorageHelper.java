package com.unimelb.family_artifact_register.FoundationLayer.Util;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.unimelb.family_artifact_register.Util.CacheDirectoryHelper;
import com.unimelb.family_artifact_register.Util.FileHelper;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * Manager helps store and get file from firebase storage.
 */
public class FirebaseStorageHelper {
    private static final String TAG = FirebaseStorageHelper.class.getSimpleName();

    private static final FirebaseStorageHelper ourInstance = new FirebaseStorageHelper();
    // Map Remote storage location and local storage location together
    private BiMap<String, Uri> remoteLocalBiMap = HashBiMap.create();
    // Finds the correct cache directory
    private CacheDirectoryHelper mCacheDirectoryHelper = CacheDirectoryHelper.getInstance();
    // Firebase storage location
    private StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();

    private FirebaseStorageHelper() {
        if (mCacheDirectoryHelper.getCacheDirectory() == null) {
            Log.w(TAG, "Failed to find Cache directory from cache helper! (null)");
        }
    }

    public static FirebaseStorageHelper getInstance() {
        return ourInstance;
    }

    /**
     * Check and add scheme to filee
     *
     * @param uri uri
     * @return uri with scheme
     */
    private static Uri checkAddUriScheme(Uri uri) {
        if (uri.getScheme() == null) {
            uri = uri.buildUpon().scheme("file").build();
        }
        return uri;
    }

    /**
     * Convert local uri to remote path
     *
     * @param localUri uri to convert
     * @return string version of remote path
     */
    private String extractLocalUri(Uri localUri) {
        Path localFilePath = new File(localUri.getPath()).toPath();
        Path localStoragePath = mCacheDirectoryHelper.getCacheDirectory().toPath();
        Log.d(TAG, "LocalUri: " + localUri.toString()
                + "\nlocalFilePath: " + localFilePath.toString()
                + "\nlocalStoragePath: " + localStoragePath.toString()
        );
        Path remotePath = localStoragePath.relativize(localFilePath);
        return remotePath.toString();
    }

    /**
     * If True, already in cache, that means this is stored in database already
     */
    private boolean uriStored(Uri uri) {
        // If True, already in cache, that means this is stored in database already
        return remoteLocalBiMap.containsKey(uri.toString()) || remoteLocalBiMap.inverse().containsKey(uri);
    }

    /**
     * revert local uri from remote path
     *
     * @param remoteUrl url to revert
     * @return local uri path
     */
    private Uri parseRemoteUrl(String remoteUrl) {
        Path remoteFilePath = new File(remoteUrl).toPath();
        Path localStoragePath = mCacheDirectoryHelper.getCacheDirectory().toPath();
        Path remotePath = localStoragePath.resolve(remoteFilePath);
        return Uri.parse(remotePath.toString());
    }

    /**
     * Upload a file by the uri
     *
     * @param uri local uri
     * @return task object to monitor upload
     */
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
                                remoteLocalBiMap.put(remotePath, uri);
                                Log.d(TAG, "Successfully uploaded, Adding to BiMap " +
                                        "remotePath: " + remotePath + ", finalUri" + finalUri + "\n"
                                        + remoteLocalBiMap.toString());
                            }
                    );
        }
    }

    /**
     * Load the remote url resource to local storage and set the Uri to it in the livedata
     *
     * @param remoteUrl The remote Url to download resource with
     * @return LiveData of the destination Uri
     */
    public LiveData<Uri> loadByRemoteUri(String remoteUrl) {
        MutableLiveData<Uri> mutableLiveData = new MutableLiveData<>();
        Uri localUri = parseRemoteUrl(remoteUrl);
        File localFile = new File(localUri.toString());
        Log.d(TAG, "remote url: " + remoteUrl + "\nlocal uri: " + localUri.toString());
        if (localFile.exists()) {
            Log.d(TAG, "localFile exists!");
            // Exist in local
            mutableLiveData.setValue(localUri);
            // add to mapping
            if (!remoteLocalBiMap.containsKey(remoteUrl)) {
                // FIXME this try catch should be removed after someone fixed their problem in code
                try {
                    remoteLocalBiMap.put(remoteUrl, localUri);
                } catch (IllegalArgumentException e) {
                    Log.e(TAG, "Failed to put into bimap, e: " + e.toString() +
                            "remoteUrl: " + remoteUrl + ", localUri: " + localUri);
                }
            }
        } else {
            // If not loaded yet
            // First make directory then start query
            // Make directory
            if (localFile.getParentFile() != null && FileHelper.getInstance()
                    .mkdirs(localFile.getParentFile())) {
                // Query database
                StorageReference mFileStorageReference = mStorageReference.child(remoteUrl);
                mFileStorageReference.getFile(localUri)
                        .addOnProgressListener(
                                new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        if (taskSnapshot != null) {
                                            Log.w(TAG, "bytes transferred: " + taskSnapshot.getBytesTransferred() +
                                                    "\ntotal bytes: " + taskSnapshot.getTotalByteCount());
                                        }
                                        // Task snapshot is not null and all data has been transferred
                                        if (taskSnapshot != null &&
                                                taskSnapshot.getBytesTransferred() ==
                                                        taskSnapshot.getTotalByteCount()) {
                                            Log.w(TAG, "1Successfully getting remote Url: " + remoteUrl +
                                                    "\nLocal Uri: " + localUri.toString());
                                            Log.w(TAG, "1bytes transferred: " + taskSnapshot.getBytesTransferred() +
                                                    "\ntotal bytes: " + taskSnapshot.getTotalByteCount());
                                            // set value in map (cache)
                                            remoteLocalBiMap.putIfAbsent(remoteUrl, localUri);
                                            // Notify observer
                                            mutableLiveData.setValue(localUri);
                                        } else {
                                            // Handle here (I don't think it's going to reach here)
                                        }
                                    }
                                }
                        )
                        .addOnSuccessListener(
                                new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        if (taskSnapshot != null) {
                                            Log.w(TAG, "2Successfully getting remote Url: " + remoteUrl +
                                                    "\nLocal Uri: " + localUri.toString());
                                            Log.w(TAG, "2bytes transferred: " + taskSnapshot.getBytesTransferred() +
                                                    "\ntotal bytes: " + taskSnapshot.getTotalByteCount());
                                            // set value in map (cache)
//                                    remoteLocalBiMap.putIfAbsent(remoteUrl, localUri);
                                            // Notify observer
//                                    mutableLiveData.setValue(localUri);
                                        }
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
            } else {
                Log.w(TAG, "Failed to create directory for storing remote Url: " + remoteUrl +
                        "\nlocalUri: " + localUri.toString());
            }
        }
        return mutableLiveData;
    }

    /**
     * Load the remote url resource to local storage and set the Uri to it in the livedata
     *
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
        return remoteLocalBiMap.inverse().get(uri);
    }
}
