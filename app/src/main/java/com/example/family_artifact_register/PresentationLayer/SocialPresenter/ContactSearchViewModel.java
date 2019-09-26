package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.FoundationLayer.SocialModel.UserRepository;

public class ContactSearchViewModel extends AndroidViewModel {

    public static final String TAG = ContactSearchViewModel.class.getSimpleName();

    private UserRepository repository;

    public ContactSearchViewModel(Application application) {
        super(application);
        Log.i(TAG, "enter view model cons");
        repository = new UserRepository(application);
    }

    public LiveData<User> getFriend(String username) { return repository.getFriend(username); }
}
