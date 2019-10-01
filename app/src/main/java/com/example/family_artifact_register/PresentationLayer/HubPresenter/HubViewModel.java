package com.example.family_artifact_register.PresentationLayer.HubPresenter;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;


import com.example.family_artifact_register.FoundationLayer.ArtifactModel.Artifact;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

import java.util.ArrayList;
import java.util.List;

public class HubViewModel extends AndroidViewModel {

    public static final String TAG = HubViewModel.class.getSimpleName();

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();

    private ArtifactManager artifactManager = ArtifactManager.getInstance();

    private LiveData<UserInfo> currentUser = userInfoManager.listenUserInfo(userInfoManager.getCurrentUid());

    private List<String> friendUids;

    private MediatorLiveData<List<UserInfo>> friends = new MediatorLiveData<>();
    private LiveData<List<ArtifactItem>> posts = new LiveData<List<ArtifactItem>>() {};

    public HubViewModel(Application application) {
        super(application);

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

        currentUser.observeForever(new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                posts = artifactManager.getArtifactItemByUid(userInfoManager.getCurrentUid());
            }
        });
    }

    public LiveData<List<ArtifactItem>> getContacts() {
        return posts;
    }

}
