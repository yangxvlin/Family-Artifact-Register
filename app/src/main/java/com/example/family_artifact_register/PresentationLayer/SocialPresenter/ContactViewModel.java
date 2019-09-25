package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.family_artifact_register.FoundationLayer.SocialModel.FriendRepository;

import java.util.ArrayList;

public class ContactViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> contacts = new MutableLiveData<>();

    private FriendRepository repository = FriendRepository.getInstance();

    private String uid;

    public ContactViewModel(String uid) {
        this.uid = uid;
    }

    public MutableLiveData<ArrayList<String>> getContact() { return repository.getFriends(uid); }
}
