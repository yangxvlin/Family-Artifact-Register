package com.unimelb.family_artifact_register.PresentationLayer.HubPresenter;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.unimelb.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;
import com.unimelb.family_artifact_register.PresentationLayer.Util.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.Util.ArtifactPostWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.Util.UserInfoWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Haichao Song 854035,
 * @time 2019-10-08 14:23:08
 * @description view model to get information from backend to post item
 */
public class HubViewModel extends AndroidViewModel {

    // get class tag
    public static final String TAG = HubViewModel.class.getSimpleName();

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();
    private ArtifactManager artifactManager = ArtifactManager.getInstance();
    private FirebaseStorageHelper fSHelper = FirebaseStorageHelper.getInstance();

    private MutableLiveData<List<ArtifactPostWrapper>> artifactWrapperList =
            new MutableLiveData<>();
    private MutableLiveData<List<ArtifactPostWrapper>> latestArtifactWrapperList =
            new MutableLiveData<>();

    private String currentUid;
    private List<String> friendUids;

    private MediatorLiveData<List<UserInfoWrapper>> friends = new MediatorLiveData<>();

    public HubViewModel(Application application) {
        super(application);

        currentUid = userInfoManager.getCurrentUid();

        // get all friends information for current user
        userInfoManager.listenUserInfo(currentUid).observeForever(new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo me) {
                Log.d(TAG, "retrieved latest data about current user");

                // store all friends uids in array
                friendUids = new ArrayList<>(me.getFriendUids().keySet());
                ArrayList<ArtifactPostWrapper> postWrappers = new ArrayList<>();
                List<LiveData<UserInfo>> friendList = userInfoManager.listenUserInfo(friendUids);

                friends.setValue(new ArrayList<>());
                Log.d(TAG, "size of friend list: " + friendList.size());

                // for every friends id, get their user information
                for (LiveData<UserInfo> friend : friendList) {
                    friends.removeSource(friend);
                    friends.addSource(friend, new Observer<UserInfo>() {
                        @Override
                        public void onChanged(UserInfo userInfo) {
                            Log.d(TAG, "retrieved data about user with uid: " +
                                    userInfo.getUid());

                            // processing this user's info
                            UserInfoWrapper userWrapper = new UserInfoWrapper(userInfo);
                            String url = userWrapper.getPhotoUrl();

                            // get friends avatar in order to show in post item
                            if (url == null) {
                                userWrapper.setPhotoUrl(null);
                                friends.getValue().add(userWrapper);
                                friends.setValue(friends.getValue());
                                getFriendArtifactItem(userWrapper, postWrappers);
                            } else {
                                fSHelper.loadByRemoteUri(url).observeForever(new Observer<Uri>() {
                                    @Override
                                    public void onChanged(Uri uri) {
                                        userWrapper.setPhotoUrl(uri.toString());
//                                        friends.setValue(friends.getValue());
                                        getFriendArtifactItem(userWrapper, postWrappers);
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
        getPostsChange();
    }

    /**
     * get all friends artifact item information and store all of them in an array
     *
     * @param userInfo the friends user information
     * @param wrappers the artifact information post by friends
     */
    private void getFriendArtifactItem(UserInfoWrapper userInfo,
                                       List<ArtifactPostWrapper> wrappers) {

        // now processing this user's artifact info
        artifactManager.listenArtifactItemByUid(userInfo.getUid(), "HubViewModel1")
                .observeForever(new Observer<List<ArtifactItem>>() {
                    @Override
                    public void onChanged(List<ArtifactItem> artifactItems) {
                        Log.d(TAG, "retrieved artifact item data about user with uid: " +
                                userInfo.getUid());
                        latestArtifactWrapperList.setValue(wrappers);
                        for (ArtifactItem artifactItem : artifactItems) {
                            ArtifactPostWrapper wrapper = new ArtifactPostWrapper(
                                    new ArtifactItemWrapper(artifactItem), userInfo);
                            Log.d(TAG, wrapper.getArtifactItemWrapper().getDescription() +
                                    "get likes size: " + wrapper.getArtifactItemWrapper()
                                    .getLikes().size());
                            List<String> urls = artifactItem.getMediaDataUrls();
                            if (urls.size() > 0) {
                                fSHelper.loadByRemoteUri(urls).observeForever(new Observer<List<Uri>>() {
                                    @Override
                                    public void onChanged(List<Uri> uris) {
                                        wrapper.getArtifactItemWrapper().setLocalMediaDataUrls(
                                                uris.stream()
                                                        .map(Objects::toString)
                                                        .collect(Collectors.toList()));
                                        if (wrappers.contains(wrapper)) {
                                            wrappers.remove(wrapper);
                                            wrappers.add(wrapper);
                                        } else {
                                            wrappers.add(wrapper);
                                        }
                                        latestArtifactWrapperList.setValue(wrappers);
                                    }
                                });
                            }
                        }
                    }
                });
    }

    public void getPostsChange() {
        artifactWrapperList.postValue(latestArtifactWrapperList.getValue());
    }

    public LiveData<List<ArtifactPostWrapper>> getPosts() {
        return artifactWrapperList;
    }

    public LiveData<List<UserInfoWrapper>> getFriends() {
        return friends;
    }

    /**
     * send likes number change when user press like image
     *
     * @param tag    the image tag of like image to distinguish user likes or unlikes the post
     * @param PostId the post id user presses the like image
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
