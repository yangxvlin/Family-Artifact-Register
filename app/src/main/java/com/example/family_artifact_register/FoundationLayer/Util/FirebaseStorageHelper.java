package com.example.family_artifact_register.FoundationLayer.Util;

import android.net.Uri;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;
import java.util.UUID;

public class FirebaseStorageHelper {
    private static final FirebaseStorageHelper ourInstance = new FirebaseStorageHelper();
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    public static FirebaseStorageHelper getInstance() {
        return ourInstance;
    }

    private FirebaseStorageHelper() {
    }

    public UploadTask uploadByUri(Uri uri, StorageReference storageReference, String uploadName) {
        StorageReference storageRefOri = storageReference.child(uploadName);
        if (uri.getScheme() == null) {
            uri = Uri.parse("file://"+uri.toString());
        }
        return storageRefOri.putFile(uri);
    }

    public UploadTask uploadByUri(Uri uri, String path, String uploadName) {
        StorageReference storageReference = storage.getReference(path);
        return uploadByUri(uri, storageReference, uploadName);
    }

    public Pair<String, UploadTask> uploadByUri(Uri uri, StorageReference storageReference) {
        String uploadName = UUID.randomUUID().toString();
        return new Pair<>(uploadName, uploadByUri(uri, storageReference, uploadName));
    }

    public Pair<String, UploadTask> uploadByUri(Uri uri, String path) {
        String uploadName = UUID.randomUUID().toString();
        return new Pair<>(uploadName, uploadByUri(uri, path, uploadName));
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
}
