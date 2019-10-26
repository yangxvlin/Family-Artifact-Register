package com.unimelb.family_artifact_register.PresentationLayer.ArtifactDetailPresenter;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocationManager;
import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.unimelb.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter.UserInfoWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.Util.Pair;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Haichao Song 854035,
 * @time 2019-10-02 19:36:15
 * @description view model to get information from backend to detail activity
 */
public class DetailViewModel extends AndroidViewModel {

    // get class tag
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

    /**
     * get artifact detail information from friends with poster and map location information
     * @param itemID the post id of artifact user clicked
     * @return pair of a artifact item with associated happened map location
     */
    public LiveData<Pair<ArtifactItemWrapper, MapLocation>> getArtifactItem(String itemID) {
        MutableLiveData<Pair<ArtifactItemWrapper, MapLocation>> artifactPair = new MutableLiveData<>();

        // get artifact item from backend
        artifactManager.getArtifactItemByPostId(itemID).observeForever(new Observer<ArtifactItem>() {
            @Override
            public void onChanged(ArtifactItem artifactItem) {
                List<String> mediaDataRemoteUrls = artifactItem.getMediaDataUrls();
                ArtifactItemWrapper wrapper = new ArtifactItemWrapper(artifactItem);

                // render map to detail activity
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

                // get happened location from backend
                // pair item with location
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

    /**
     * get poster information from backend in order to show poster avatar and display name
     * @param posterId
     * @return
     */
    public LiveData<UserInfoWrapper> getPosterInfo(String posterId) {
        MutableLiveData<UserInfoWrapper> poster = new MutableLiveData<>();

        // get poster information by post id
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

    /**
     * get happened location from backend
     * deprecate and move into get artifact item method
     * @param PostId the post id user clicked
     * @return map location of happened location in live data
     */
    @Deprecated
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

    /**
     * get upload location from backend
     * @param itemID the post id user clicked
     * @return map location of upload artifact in live data
     */
    public LiveData<MapLocation> getUploadLocation(String itemID) {
        MutableLiveData<MapLocation> uploadLocation = new MutableLiveData<>();
        artifactManager.getArtifactItemByPostId(itemID).observeForever(new Observer<ArtifactItem>() {
            @Override
            public void onChanged(ArtifactItem artifactItem) {
                String uploadLocationId = artifactItem.getLocationUploadedId();
                Log.d(TAG, "Get upload location id: " + uploadLocationId);
                mapLocationManager.getMapLocationById(uploadLocationId).observeForever(new Observer<MapLocation>() {
                    @Override
                    public void onChanged(MapLocation mapLocation) {
                        Log.d(TAG, "get map location");
                        uploadLocation.setValue(mapLocation);
                        Log.d(TAG, "return upload location: " + uploadLocation.getValue().toString());
                    }
                });

            }
        });
        return uploadLocation;
    }

    /**
     * get stored location from backend
     * @param itemID the post id user clicked
     * @return map location of artifact stored in live data
     */
    public LiveData<MapLocation> getStoredLocation(String itemID) {
        MutableLiveData<MapLocation> storedLocation = new MutableLiveData<>();
        artifactManager.getArtifactItemByPostId(itemID).observeForever(new Observer<ArtifactItem>() {
            @Override
            public void onChanged(ArtifactItem artifactItem) {
                String storedLocationId = artifactItem.getLocationStoredId();
                Log.d(TAG, "Get stored location id: " + storedLocationId);
                mapLocationManager.getMapLocationById(storedLocationId).observeForever(new Observer<MapLocation>() {
                    @Override
                    public void onChanged(MapLocation mapLocation) {
                        Log.d(TAG, "get map location");
                        storedLocation.setValue(mapLocation);
                        Log.d(TAG, "return upload location: " + storedLocation.getValue().toString());
                    }
                });

            }
        });
        return storedLocation;
    }

    /**
     * send likes number change information to backend to change likes map
     * @param tag the like image tag to distinguish user likes or unlikes an artifact
     * @param PostId the post id user pressed the like image
     */
    public void getLikeChange(String tag, String PostId) {
        if (tag == "liked") {
            artifactManager.addLike(PostId, userInfoManager.getCurrentUid());
        } else {
            artifactManager.removeLike(PostId, userInfoManager.getCurrentUid());
        }
    }

    public String getCurrentUid() {
        return userInfoManager.getCurrentUid();
    }
}
