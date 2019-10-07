package com.example.family_artifact_register.PresentationLayer.MapPresenter;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocationManager;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

import java.util.ArrayList;
import java.util.List;

public class MapViewModel extends AndroidViewModel {

    public static final String TAG = MapViewModel.class.getSimpleName();

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();
    private ArtifactManager artifactManager = ArtifactManager.getInstance();
    private MapLocationManager mapLocationManager = MapLocationManager.getInstance();

    private LiveData<List<ArtifactItem>> artifacts = artifactManager.getArtifactItemByUid(userInfoManager.getCurrentUid());

    private List<MapLocation> mapLocations = new ArrayList<>();

    private MediatorLiveData<List<MapLocation>> locations = new MediatorLiveData<>();

    public MapViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<MapLocation>> getLocations() {
        artifacts.observeForever(new Observer<List<ArtifactItem>>() {
            @Override
            public void onChanged(List<ArtifactItem> artifactItems) {
                Log.d(TAG, "items returned from DB: " + artifactItems.toString());
                for(ArtifactItem item: artifactItems) {
                    LiveData<MapLocation> loc = mapLocationManager.getMapLocationById(item.getLocationHappenedId());
                    locations.removeSource(loc);
                    locations.addSource(loc, new Observer<MapLocation>() {
                        @Override
                        public void onChanged(MapLocation mapLocation) {
                            locations.getValue().add(mapLocation);
                            locations.setValue(locations.getValue());
                            Log.d(TAG, "map location returned from DB: " + mapLocation.toString());
                        }
                    });
                }
            }
        });
        return locations;
    }
}
