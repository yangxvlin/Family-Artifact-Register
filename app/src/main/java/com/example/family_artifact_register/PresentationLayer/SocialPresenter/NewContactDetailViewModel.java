package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.family_artifact_register.FoundationLayer.SocialModel.Friend;
import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.FoundationLayer.SocialModel.UserRepository;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

public class NewContactDetailViewModel extends AndroidViewModel {

    public static final String TAG = NewContactDetailViewModel.class.getSimpleName();

    private UserInfoManager manager;
    private LiveData<UserInfo> friend;

    public NewContactDetailViewModel(Application application, String uid) {
        super(application);
        manager = UserInfoManager.getInstance();
        friend = manager.getUserInfo(uid);
    }

    public LiveData<UserInfo> getUser() { return friend; }

    public void insert(String currentUid) {
        UserInfo currentUser = manager.getCurrentUserInfo();
        manager.addFriend(currentUser, friend.getValue());
    }

    //    public void insert() {
//        Friend friend = new Friend("Me", user.getValue().username);
//        repository.insertFriend(friend);
//    }
}
