package com.example.family_artifact_register.PresentationLayer.MapPresenter;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;
import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocationManager;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;

import java.util.ArrayList;
import java.util.List;

public class MapViewModel extends AndroidViewModel {

    public static final String TAG = MapViewModel.class.getSimpleName();

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();
    private ArtifactManager artifactManager = ArtifactManager.getInstance();
    private MapLocationManager mapLocationManager = MapLocationManager.getInstance();

    private LiveData<List<ArtifactItem>> artifacts;

    private List<MapLocation> mapLocations = new ArrayList<>();

    private MediatorLiveData<List<MapLocation>> locations = new MediatorLiveData<>();

    private MutableLiveData<List<TimelineMapWrapper>> timelineWrappers = new MutableLiveData<>();

    public MapViewModel(@NonNull Application application) {
        super(application);
        artifacts = artifactManager.getArtifactItemByUid(userInfoManager.getCurrentUid());
        locations.setValue(mapLocations);
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
                            mapLocations.add(mapLocation);
                            locations.setValue(mapLocations);
                            Log.d(TAG, "map location returned from DB: " + mapLocation.toString());
                        }
                    });
                }
            }
        });
        return locations;
    }

    public LiveData<List<TimelineMapWrapper>> getMapWrapper() {

        // TODO choose a good listener identifier
        artifactManager.listenArtifactTimelineByUid(userInfoManager.getCurrentUid(), "MapViewModel1").observeForever(new Observer<List<ArtifactTimeline>>() {
            @Override
            public void onChanged(List<ArtifactTimeline> artifactTimelines) {
                Log.d(TAG, "retrieved all timeline data for current user");
                List<TimelineMapWrapper> wrappers = new ArrayList<>();
                timelineWrappers.postValue(wrappers);

                for(ArtifactTimeline timeline: artifactTimelines) {
                    List<ArtifactItemWrapper> itemWrappers = new ArrayList<>();
                    List<MapLocation> mapLocations = new ArrayList<>();
                    TimelineMapWrapper timelineWrapper = new TimelineMapWrapper(timeline, itemWrappers, mapLocations);
                    wrappers.add(timelineWrapper);
                    timelineWrappers.postValue(wrappers);

                    List<String> itemIDs = timeline.getArtifactItemPostIds();
                    for(String id: itemIDs) {

                        // TODO choose a good listener identifier
                        artifactManager.listenArtifactItemByPostId(id, "MapViewModel2").observeForever(new Observer<ArtifactItem>() {
                            @Override
                            public void onChanged(ArtifactItem artifactItem) {
                                Log.d(TAG, "retrieved data about artifact item with id: " + artifactItem.getPostId());

                                timelineWrapper.getArtifactItemWrapperList().add(new ArtifactItemWrapper(artifactItem));
                                timelineWrappers.postValue(wrappers);
                                mapLocationManager.getMapLocationById(artifactItem.getLocationStoredId()).observeForever(new Observer<MapLocation>() {
                                    @Override
                                    public void onChanged(MapLocation mapLocation) {
                                        timelineWrapper.getStoreLocationList().add(mapLocation);
                                        timelineWrappers.postValue(wrappers);
                                    }
                                });
                            }
                        });

                    }
                }
            }
        });
        return timelineWrappers;
    }
}
