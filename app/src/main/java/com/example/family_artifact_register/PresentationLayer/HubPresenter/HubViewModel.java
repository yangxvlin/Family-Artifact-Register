package com.example.family_artifact_register.PresentationLayer.HubPresenter;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;


import com.example.family_artifact_register.FoundationLayer.ArtifactModel.Artifact;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.example.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.UserInfoWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HubViewModel extends AndroidViewModel {

    public static final String TAG = HubViewModel.class.getSimpleName();

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();
    private ArtifactManager artifactManager = ArtifactManager.getInstance();
    private FirebaseStorageHelper fSHelper = FirebaseStorageHelper.getInstance();

    private MutableLiveData<List<ArtifactItem>> artifactList;
    private MutableLiveData<List<ArtifactItemWrapper>> artifactWrapperList = new MutableLiveData<>();

    private List<ArtifactItem> latestArtifactList = new ArrayList<>();
    private MutableLiveData<List<ArtifactItemWrapper>> latestArtifactWrapperList = new MutableLiveData<>();

    private List<String> latestPostList = new ArrayList<>();

    private String currentUid;
    private List<String> friendUids;

    private MediatorLiveData<List<UserInfoWrapper>> friends = new MediatorLiveData<>();

    public HubViewModel(Application application) {
        super(application);

        currentUid = userInfoManager.getCurrentUid();

        userInfoManager.listenUserInfo(currentUid).observeForever(new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo me) {
                Log.d(TAG, "retrieved latest data about current user");
                friendUids = new ArrayList<>(me.getFriendUids().keySet());
                List<LiveData<UserInfo>> friendList = userInfoManager.listenUserInfo(friendUids);

                friends.setValue(new ArrayList<>());

                for(LiveData<UserInfo> friend: friendList) {
                    friends.removeSource(friend);
                    friends.addSource(friend, new Observer<UserInfo>() {
                        @Override
                        public void onChanged(UserInfo userInfo) {
                            Log.d(TAG, "retrieved data about user with uid: " + userInfo.getUid());

                            // processing this user's info
                            UserInfoWrapper wrapper = new UserInfoWrapper(userInfo);
                            friends.getValue().add(wrapper);
                            friends.setValue(friends.getValue());
                            String url = wrapper.getPhotoUrl();
                            if(url == null) {
                                wrapper.setPhotoUrl(null);
                                friends.setValue(friends.getValue());
                            }
                            else {
                                fSHelper.loadByRemoteUri(url).observeForever(new Observer<Uri>() {
                                    @Override
                                    public void onChanged(Uri uri) {
                                        wrapper.setPhotoUrl(uri.toString());
                                        friends.setValue(friends.getValue());
                                    }
                                });
                            }

                            // now processing this user's artifact info
                            artifactManager.listenArtifactItemByUid(userInfo.getUid(), "HubViewModel1").observeForever(new Observer<List<ArtifactItem>>() {
                                @Override
                                public void onChanged(List<ArtifactItem> artifactItems) {
                                    Log.d(TAG, "retrieved artifact item data about user with uid: " + userInfo.getUid());
                                    ArrayList<ArtifactItemWrapper> wrappers = new ArrayList<>();
                                    latestArtifactWrapperList.postValue(wrappers);
                                    for(ArtifactItem artifactItem: artifactItems) {
                                        ArtifactItemWrapper wrapper = new ArtifactItemWrapper(artifactItem);
                                        wrappers.add(wrapper);
                                        latestArtifactWrapperList.postValue(wrappers);
                                        List<String> urls = artifactItem.getMediaDataUrls();
                                        if(urls.size() > 0) {
                                            fSHelper.loadByRemoteUri(urls).observeForever(new Observer<List<Uri>>() {
                                                @Override
                                                public void onChanged(List<Uri> uris) {
                                                    wrapper.setLocalMediaDataUrls(
                                                            uris.stream()
                                                                    .map(Objects::toString)
                                                                    .collect(Collectors.toList()));
                                                    latestArtifactWrapperList.postValue(wrappers);
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
        getPostsChange();
    }

    public void getPostsChange() {
        artifactWrapperList.postValue(latestArtifactWrapperList.getValue());
//        currentUid = userInfoManager.getCurrentUid();
//        artifactList = (MutableLiveData<List<ArtifactItem>>) artifactManager.getArtifactItemByUid(currentUid);
//        artifactWrapperList = new MutableLiveData<>();
//        ArrayList<ArtifactItemWrapper> wrappers = new ArrayList<>();
//        artifactWrapperList.setValue(wrappers);
//        artifactList.observeForever(new Observer<List<ArtifactItem>>() {
//            @Override
//            public void onChanged(List<ArtifactItem> artifactItems) {
//                for(ArtifactItem item: artifactItems) {
//                    List<String> mediaDataRemoteUrls = item.getMediaDataUrls();
//                    ArtifactItemWrapper wrapper = new ArtifactItemWrapper(item);
//
//                    fSHelper.loadByRemoteUri(mediaDataRemoteUrls).observeForever(new Observer<List<Uri>>() {
//                        @Override
//                        public void onChanged(List<Uri> uris) {
//                            Log.d(TAG, "local uris: " + uris.toString());
//
//                            // load data to wrapper
//                            wrapper.setLocalMediaDataUrls(
//                                    uris.stream()
//                                            .map(Objects::toString)
//                                            .collect(Collectors.toList())
//                            );
//
//                            wrappers.add(wrapper);
//                            artifactWrapperList.setValue(wrappers);
//                            // artifactList.setValue(artifactList.getValue());
//                        }
//                    });
//                }
//            }
//        });
    }

    public LiveData<List<ArtifactItemWrapper>> getPosts() {
        return artifactWrapperList;
    }

}
