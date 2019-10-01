package com.example.family_artifact_register.PresentationLayer.HubPresenter;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;


import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

import java.util.ArrayList;
import java.util.List;

public class HubViewModel extends AndroidViewModel {

    public static final String TAG = HubViewModel.class.getSimpleName();

    private UserInfoManager manager = UserInfoManager.getInstance();

    private LiveData<UserInfo> currentUser = manager.listenUserInfo(manager.getCurrentUid());

    private List<String> friendUids;

    private MediatorLiveData<List<UserInfo>> friends = new MediatorLiveData<>();
    private MediatorLiveData<List<UserInfo>> posts = new MediatorLiveData<>();

    public HubViewModel(Application application) {
        super(application);

        currentUser.observeForever(new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                friendUids = new ArrayList<>(userInfo.getFriendUids().keySet());

                posts.setValue(new ArrayList<>());

                List<LiveData<UserInfo>> friendList = manager.listenUserInfo(friendUids);
                for(LiveData<UserInfo> i: friendList) {
                    posts.removeSource(i);
                    posts.addSource(i, new Observer<UserInfo>() {
                        @Override
                        public void onChanged(UserInfo userInfo) {
                            posts.getValue().add(userInfo);
                            posts.setValue(posts.getValue());
                        }
                    });
                }
            }
        });
    }

    public LiveData<List<UserInfo>> getContacts() {
        return friends;
    }

}
