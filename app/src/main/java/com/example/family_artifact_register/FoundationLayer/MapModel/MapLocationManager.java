package com.example.family_artifact_register.FoundationLayer.MapModel;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.family_artifact_register.FoundationLayer.DBConstant;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class MapLocationManager {
    /**
    Tag for logging
     */
    private static final String TAG = MapLocationManager.class.getSimpleName();

    private static final MapLocationManager ourInstance = new MapLocationManager();

    public static MapLocationManager getInstance() {
        return ourInstance;
    }

    /**
     * The database reference used.
     */
    private CollectionReference mMapLocationCollection;

    /**
     * Storage reference for storing photo
     */
    private StorageReference mMapLocationPhotoReference;

    private MapLocationManager() {
        mMapLocationCollection = FirebaseFirestore.getInstance().collection(DBConstant.MAP_LOCATION);
        mMapLocationPhotoReference = FirebaseStorage.getInstance().getReference(DBConstant.MAP_LOCATION_PHOTO_URL);
    }

    /**
     * Store the mapLocation to database.
     * WARNING!! The image url in the MapLocation will be overwritten by the new URL in server!
     *
     * @param mapLocation
     */
    public void storeMapLocation(MapLocation mapLocation) {
        DocumentReference mapLocationReference;
        // Assign mapLocationId if not yet assigned.
        if (mapLocation.getMapLocationId() == null) {
            // Generate the id automatically
            mapLocationReference = mMapLocationCollection.document();
            mapLocation.setMapLocationId(mapLocationReference.getId());
        } else {
            mapLocationReference = mMapLocationCollection
                    .document(mapLocation.getMapLocationId());
        }
        // Get reference based on current mapLocation id
        StorageReference mapLocationImagesReference = mMapLocationPhotoReference
                .child(mapLocation.getMapLocationId());

        // Save to database
        // First save image then along with the reference push to database
        // Need to:
        // 1. generate corresponding image url from current url (map)
        // 2. store the url
        Map<String, String> imageUrlMap = new HashMap<>();

        int i = 0;
        for (String imageUrl : mapLocation.getImageUrls()) {
            imageUrlMap.put(mapLocation.getMapLocationId()+"_"+i, imageUrl);
            i += 1;
        }

        for (String key: imageUrlMap.keySet()) {
            mapLocationImagesReference.child(key)
                    .putFile(Uri.parse(imageUrlMap.get(key)))
                    .addOnFailureListener(e -> Log.w(TAG,
                            "Error Uploading image Url: {" + key + ", " +
                            imageUrlMap.get(key) + "}, e:" + e.toString()))
                    .addOnSuccessListener(taskSnapshot -> Log.d(TAG,
                            "Successfully upload image Url: {" + key + ", " +
                                    imageUrlMap.get(key) + "}"));
            // 3. overwrite existing url (ideally this should be in on success listener,
            // but that double async on two servers is annoying to deal with)
            mapLocation.addImageUrl(key);
            mapLocation.removeImageUrl(imageUrlMap.get(key));
        }
        // Now store the actual MapLocation
        mapLocationReference.set(mapLocation)
                    .addOnFailureListener(e -> Log.w(TAG,
                            "Error Uploading Location:" + mapLocation.toString() +
                                    "e:" + e.toString()));
    }

}
