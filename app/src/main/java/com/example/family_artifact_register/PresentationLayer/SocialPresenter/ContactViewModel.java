package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.family_artifact_register.FoundationLayer.SocialModel.UserRepository;
import com.example.family_artifact_register.FoundationLayer.SocialModel.User;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

//    private FriendRepository repository = FriendRepository.getInstance();

    private LiveData<List<User>> contacts;
    private UserRepository repository;

    public ContactViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
        contacts = repository.getFriends();
    }

    public LiveData<List<User>> getContacts() { return contacts; }

    public void addContact(String username) {
        User user = new User("-1", username, "heihei", "unknown");
        repository.insert(user);
    }
}
