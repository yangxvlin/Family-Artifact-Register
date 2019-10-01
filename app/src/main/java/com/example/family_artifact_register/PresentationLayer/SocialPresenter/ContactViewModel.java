package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.example.family_artifact_register.FoundationLayer.SocialModel.UserRepository;
import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ContactViewModel extends AndroidViewModel {

    public static final String TAG = ContactViewModel.class.getSimpleName();

    private UserInfoManager manager = UserInfoManager.getInstance();

    private LiveData<UserInfo> currentUser = manager.listenUserInfo(manager.getCurrentUid());

    private List<String> friendUids;

//    private MediatorLiveData<List<UserInfo>> friends = new MediatorLiveData<>();
    private MediatorLiveData<Set<UserInfo>> friends = new MediatorLiveData<>();


    public ContactViewModel(Application application) {
        super(application);

        currentUser.observeForever(new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                friendUids = new ArrayList<>(userInfo.getFriendUids().keySet());

                friends.setValue(new TreeSet<>(new Comparator<UserInfo>() {
                    @Override
                    public int compare(UserInfo userInfo, UserInfo t1) {
                        return userInfo.getUid().compareTo(t1.getUid());
                    }
                }));

                List<LiveData<UserInfo>> friendList = manager.listenUserInfo(friendUids);
//                Set<LiveData<UserInfo>> x = new TreeSet<>(new Comparator<LiveData<UserInfo>>() {
//                    @Override
//                    public int compare(LiveData<UserInfo> userInfoLiveData, LiveData<UserInfo> t1) {
//                        return userInfoLiveData.getValue().getUid().compareTo(t1.getValue().getUid());
//                    }
//                });
//                x.addAll(manager.listenUserInfo(friendUids));

                for(LiveData<UserInfo> i: friendList) {
                    friends.removeSource(i);
                    friends.addSource(i, new Observer<UserInfo>() {
                        @Override
                        public void onChanged(UserInfo userInfo) {
                            friends.getValue().add(userInfo);
                            friends.setValue(friends.getValue());
                        }
                    });
                }
            }
        });
    }

    public LiveData<Set<UserInfo>> getContacts() {
        return friends;
    }
}
