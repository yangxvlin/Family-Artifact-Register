package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.family_artifact_register.FoundationLayer.SocialModel.Friend;
import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.FoundationLayer.SocialModel.UserRepository;

public class NewContactDetailViewModel extends AndroidViewModel {

    public static final String TAG = NewContactDetailViewModel.class.getSimpleName();

    private LiveData<User> user;
    private UserRepository repository;

    public NewContactDetailViewModel(Application application, String useranme) {
        super(application);
        repository = new UserRepository(application);
        user = repository.getUser(useranme);
    }

    public LiveData<User> getUser() { return user; }

    public void insert() {
        Friend friend = new Friend("Me", user.getValue().username);
        repository.insertFriend(friend);
    }
}
