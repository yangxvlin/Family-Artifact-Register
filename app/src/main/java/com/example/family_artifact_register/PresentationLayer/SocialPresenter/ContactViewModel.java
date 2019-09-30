package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.family_artifact_register.FoundationLayer.SocialModel.UserRepository;
import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ContactViewModel extends AndroidViewModel {

    public static final String TAG = ContactViewModel.class.getSimpleName();

//    private FriendRepository repository = FriendRepository.getInstance();

    private LiveData<List<UserInfo>> contacts;
    private UserRepository repository;

    public ContactViewModel(Application application, String currentUid) {
        super(application);
        repository = new UserRepository();
        try {
            UserInfo currentUser = repository.getUser(currentUid).getValue();
            List<String> friendUids = new ArrayList<>(currentUser.getFriendUids().keySet());
            List<LiveData<UserInfo>> friends = repository.getFriends(friendUids);
            List<UserInfo> friendsInfo = new ArrayList<>();
            for (LiveData<UserInfo> i: friends)
                friendsInfo.add(i.getValue());
            contacts= new MutableLiveData<>(friendsInfo);
        } catch (NullPointerException e) {
            Log.d(TAG, "failed to get friend information from database");
            contacts =  new MutableLiveData<>(new ArrayList<>());
        }
    }

    public LiveData<List<UserInfo>> getContacts() { return contacts; }

    public String getUidByName(String username) {
        for(UserInfo i: contacts.getValue()) {
            if(username.equals(i.getDisplayName()))
                return i.getUid();
        }
        return null;
    }
}
