package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.FoundationLayer.SocialModel.UserRepository;

import java.util.List;

public class ContactSearchResultViewModel extends AndroidViewModel {

    private UserRepository repository;
    private LiveData<User> user;

    public ContactSearchResultViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
//        user = repository.getUser(username);
    }

    public LiveData<List<User>> getUsers(List<String> usernames) { return repository.getUsers(usernames); }

//    public LiveData<User> getUser() { return user; }
}
