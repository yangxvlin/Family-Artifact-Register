package com.example.family_artifact_register.FoundationLayer.Util;

import android.net.Uri;
import android.util.Pair;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
}
