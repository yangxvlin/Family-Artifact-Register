package com.example.family_artifact_register.PresentationLayer.HubPresenter;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;


import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.example.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HubViewModel extends AndroidViewModel {

    public static final String TAG = HubViewModel.class.getSimpleName();

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();

    private ArtifactManager artifactManager = ArtifactManager.getInstance();

    private FirebaseStorageHelper fSHelper = FirebaseStorageHelper.getInstance();

    private LiveData<UserInfo> currentUser = userInfoManager.listenUserInfo(userInfoManager.getCurrentUid());

    private String currentUid;

    private LiveData<List<ArtifactItem>> posts = new LiveData<List<ArtifactItem>>() {};

    public HubViewModel(Application application) {
        super(application);
//        currentUid = userInfoManager.getCurrentUid();
//        posts = artifactManager.getArtifactItemByUid(currentUid);
//        posts.observeForever(new Observer<List<ArtifactItem>>() {
//            @Override
//            public void onChanged(List<ArtifactItem> artifactItems) {
//                for(ArtifactItem item: artifactItems) {
//                    List<String> mediaDataRemoteUrls = item.getMediaDataUrls();
//                    fSHelper.loadByRemoteUrl(mediaDataRemoteUrls).observeForever(new Observer<List<Uri>>() {
//                        @Override
//                        public void onChanged(List<Uri> uris) {
//                            Log.d(TAG, "local uris: " + uris.toString());
//                            item.setMediaDataUrls(
//                                    uris.stream()
//                                            .map(Objects::toString)
//                                            .collect(Collectors.toList())
//                            );
//                        }
//                    });
//                }
//            }
//        });
        getPostsChange();

//        currentUser.observeForever(new Observer<UserInfo>() {
//            @Override
//            public void onChanged(UserInfo userInfo) {
//                friendUids = new ArrayList<>(userInfo.getFriendUids().keySet());
//
//                friends.setValue(new ArrayList<>());
//
//                List<LiveData<UserInfo>> friendList = userInfoManager.listenUserInfo(friendUids);
//                for(LiveData<UserInfo> i: friendList) {
//                    friends.removeSource(i);
//                    friends.addSource(i, new Observer<UserInfo>() {
//                        @Override
//                        public void onChanged(UserInfo userInfo) {
//                            friends.getValue().add(userInfo);
//                            friends.setValue(friends.getValue());
//                        }
//                    });
//                }
//            }
//        });
//
//        currentUser.observeForever(new Observer<UserInfo>() {
//            @Override
//            public void onChanged(UserInfo userInfo) {
//                posts = artifactManager.getArtifactItemByUid(userInfoManager.getCurrentUid());
//            }
//        });
    }

    public void getPostsChange() {
        currentUid = userInfoManager.getCurrentUid();
        posts = artifactManager.getArtifactItemByUid(currentUid);
        posts.observeForever(new Observer<List<ArtifactItem>>() {
            @Override
            public void onChanged(List<ArtifactItem> artifactItems) {
                for(ArtifactItem item: artifactItems) {
                    List<String> mediaDataRemoteUrls = item.getMediaDataUrls();
                    fSHelper.loadByRemoteUrl(mediaDataRemoteUrls).observeForever(new Observer<List<Uri>>() {
                        @Override
                        public void onChanged(List<Uri> uris) {
                            Log.d(TAG, "local uris: " + uris.toString());
                            item.setMediaDataUrls(
                                    uris.stream()
                                            .map(Objects::toString)
                                            .collect(Collectors.toList())
                            );
                        }
                    });
                }
            }
        });
    }

    public LiveData<List<ArtifactItem>> getPosts() {
        return posts;
    }

}
