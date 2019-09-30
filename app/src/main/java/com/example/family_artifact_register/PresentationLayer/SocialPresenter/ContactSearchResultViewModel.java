package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.FoundationLayer.SocialModel.UserRepository;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;

import java.util.List;

public class ContactSearchResultViewModel extends AndroidViewModel {

    private UserRepository repository;
    private LiveData<List<UserInfo>> result;

    public ContactSearchResultViewModel(Application application, String query) {
        super(application);
        repository = new UserRepository();
        result = repository.getUsers(query);
    }

    public LiveData<List<UserInfo>> getUsers() { return result; }

    public String getUidByName(String username) {
        for(UserInfo i: result.getValue()) {
            if(username.equals(i.getDisplayName()))
                return i.getUid();
        }
        return null;
    }
}
