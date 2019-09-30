package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.family_artifact_register.FoundationLayer.SocialModel.Friend;
import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.FoundationLayer.SocialModel.UserRepository;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;

public class NewContactDetailViewModel extends AndroidViewModel {

    public static final String TAG = NewContactDetailViewModel.class.getSimpleName();

    private UserRepository repository;
    private LiveData<UserInfo> friend;

    public NewContactDetailViewModel(Application application, String uid) {
        super(application);
        repository = new UserRepository();
        friend = repository.getUser(uid);
    }

    public LiveData<UserInfo> getUser() { return friend; }

    public void insert(String currentUid) {
        UserInfo currentUser = repository.getUser(currentUid).getValue();
        repository.insertFriend(currentUser, friend.getValue());
    }

    //    public void insert() {
//        Friend friend = new Friend("Me", user.getValue().username);
//        repository.insertFriend(friend);
//    }
}
