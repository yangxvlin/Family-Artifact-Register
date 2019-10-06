package com.example.family_artifact_register.FoundationLayer.MapModel;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.family_artifact_register.FoundationLayer.Util.DBConstant;
import com.example.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;
import com.example.family_artifact_register.FoundationLayer.Util.LiveDataListDispatchHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.UploadTask;

import java.util.List;

/**
 * Singleton manager for managing firebase related access with MapLocations.
 */
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

    private MapLocationManager() {
        mMapLocationCollection = FirebaseFirestore.getInstance().collection(DBConstant.MAP_LOCATION);
    }

    /**
     * Store the mapLocation to database.
     *
     * @param mapLocation The map location to store to database
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

        // Save to database
        // First save image then along with the reference push to database
        // Need to:
        // 1. generate corresponding image url from current url (map)
        // 2. store the url

        Log.i(TAG, "adding location image...");
        MutableLiveData<List<String>> uploadHelperLiveData = new MutableLiveData<>();
        uploadHelperLiveData.observeForever(
                new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> remoteUrls) {
                        Log.i(TAG, "adding map location to firestore");
                        mapLocation.getImageUrls().clear();
                        for (String url : remoteUrls) {
                            mapLocation.addImageUrl(url);
                        }
                        // Now store the actual MapLocation
                        mapLocationReference.set(mapLocation)
                                .addOnFailureListener(e -> Log.w(TAG,
                                        "Error Uploading Location:" + mapLocation.toString() +
                                                "e:" + e.toString()));
                        uploadHelperLiveData.removeObserver(this);
                    }
                }
        );
        LiveDataListDispatchHelper<String> liveDataListDispatchHelper =
                new LiveDataListDispatchHelper<>(uploadHelperLiveData, 10000);

        liveDataListDispatchHelper.addWaitingTask();
        for (String localImageUrl: mapLocation.getImageUrls()) {
            liveDataListDispatchHelper.addWaitingTask();

            Uri localUri = Uri.parse(localImageUrl);
            Log.i(TAG, "Url: {" + localImageUrl + "}");
            FirebaseStorageHelper.getInstance()
                    .uploadByUri(Uri.parse(localImageUrl)).addOnCompleteListener(
                    task -> {
                        if (task.isSuccessful()) {
                            liveDataListDispatchHelper.addResult(FirebaseStorageHelper
                                    .getInstance()
                                    .getRemoteByLocalUri(localUri));
                            Log.d(TAG, "Successfully upload image Url: {" + localImageUrl + "}");
                        } else {
                            Log.w(TAG, "Error Uploading image Url: {" + localImageUrl
                                    + "}, e:" + task.getException());
                        }
                        liveDataListDispatchHelper.completeWaitingTaskAndDispatch();
                    }
            );
        }
        liveDataListDispatchHelper.completeWaitingTaskAndDispatch();

    }


    /**
     * Get Map location based on ID, the LiveData returned from here won't be updated and need to be
     * requested again if want to refresh (this is different from UserManager
     * @param mapLocationId Id of MapLocation
     * @return MapLocation found by id.
     */
    public LiveData<MapLocation> getMapLocationById(String mapLocationId) {
        MutableLiveData<MapLocation> mapLocationMutableLiveData = new MutableLiveData<>();
        mMapLocationCollection.document(mapLocationId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                // TODO something need to be done?
            }
        });
        return mapLocationMutableLiveData;
    }
}
