package com.example.family_artifact_register.PresentationLayer.ArtifactDetailPresenter;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.Artifact;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocationManager;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.example.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.UserInfoWrapper;
import com.example.family_artifact_register.PresentationLayer.Util.Pair;
import com.example.family_artifact_register.UI.ArtifactHub.ArtifactPostWrapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DetailViewModel extends AndroidViewModel {

    public static final String TAG = DetailViewModel.class.getSimpleName();

    private ArtifactManager artifactManager = ArtifactManager.getInstance();

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();

    private MapLocationManager mapLocationManager = MapLocationManager.getInstance();

    private FirebaseStorageHelper fSHelper = FirebaseStorageHelper.getInstance();

    private MutableLiveData<ArtifactItem> item;

    private MutableLiveData<ArtifactItemWrapper> itemWrapper = new MutableLiveData<>();

    public DetailViewModel(Application application) {
        super(application);
    }

    public LiveData<Pair<ArtifactItemWrapper, MapLocation>> getArtifactItem(String itemID) {
        MutableLiveData<Pair<ArtifactItemWrapper, MapLocation>> artifactPair = new MutableLiveData<>();
        artifactManager.getArtifactItemByPostId(itemID).observeForever(new Observer<ArtifactItem>() {
            @Override
            public void onChanged(ArtifactItem artifactItem) {
                List<String> mediaDataRemoteUrls = artifactItem.getMediaDataUrls();
                ArtifactItemWrapper wrapper = new ArtifactItemWrapper(artifactItem);

                fSHelper.loadByRemoteUri(mediaDataRemoteUrls).observeForever(new Observer<List<Uri>>() {
                    @Override
                    public void onChanged(List<Uri> uris) {
                        Log.d(TAG, "local uris: " + uris.toString());

                        // load data to wrapper
                        wrapper.setLocalMediaDataUrls(
                                uris.stream()
                                        .map(Objects::toString)
                                        .collect(Collectors.toList())
                        );

                        itemWrapper.setValue(wrapper);
                        // artifactList.setValue(artifactList.getValue());
                    }
                });

                String locationHappenedId = artifactItem.getLocationHappenedId();
                mapLocationManager.getMapLocationById(locationHappenedId).observeForever(new Observer<MapLocation>() {
                    @Override
                    public void onChanged(MapLocation mapLocation) {
                        artifactPair.setValue(new Pair<ArtifactItemWrapper, MapLocation>(wrapper, mapLocation));
                    }
                });

            }
        });
        return artifactPair;
    }

    public LiveData<UserInfoWrapper> getPosterInfo(String posterId) {
        MutableLiveData<UserInfoWrapper> poster = new MutableLiveData<>();
        userInfoManager.listenUserInfo(posterId).observeForever(new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                UserInfoWrapper wrapper = new UserInfoWrapper(userInfo);
                if(wrapper.getPhotoUrl() == null) {
                    wrapper.setPhotoUrl(null);
                    poster.postValue(wrapper);
                }
                else {
                    fSHelper.loadByRemoteUri(wrapper.getPhotoUrl()).observeForever(new Observer<Uri>() {
                        @Override
                        public void onChanged(Uri uri) {
                            wrapper.setPhotoUrl(uri.toString());
                            poster.postValue(wrapper);
                        }
                    });
                }

            }
        });
        return poster;
    }

    public LiveData<MapLocation> getLocationHappened(String PostId) {
        MutableLiveData<MapLocation> location = new MutableLiveData<>();
        artifactManager.getArtifactItemByPostId(PostId).observeForever(new Observer<ArtifactItem>() {
            @Override
            public void onChanged(ArtifactItem artifactItem) {
                String locationHappenedId = artifactItem.getLocationHappenedId();
                mapLocationManager.getMapLocationById(locationHappenedId).observeForever(new Observer<MapLocation>() {
                    @Override
                    public void onChanged(MapLocation mapLocation) {
                        location.setValue(mapLocation);
                    }
                });
            }
        });
        return location;
    }

    public LiveData<MapLocation> getStorePair(String itemID) {
        MutableLiveData<MapLocation> storeLocation = new MutableLiveData<>();
        artifactManager.getArtifactItemByPostId(itemID).observeForever(new Observer<ArtifactItem>() {
            @Override
            public void onChanged(ArtifactItem artifactItem) {
                Log.d(TAG, "Get stored artifact change");
                String locationStoredId = artifactItem.getLocationStoredId();
                mapLocationManager.getMapLocationById(locationStoredId).observeForever(new Observer<MapLocation>() {
                    @Override
                    public void onChanged(MapLocation mapLocation) {
                        storeLocation.setValue(mapLocation);
                        Log.d(TAG, "return store location: " + storeLocation.getValue().toString());
                    }
                });

            }
        });
        return storeLocation;
    }
}
