package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.FoundationLayer.SocialModel.UserRepository;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.UI.Social.ContactFragment;

import java.util.List;

public class ContactSearchResultViewModel extends AndroidViewModel {
    /**
     * class tag
     */
    public static final String TAG = ContactSearchResultViewModel.class.getSimpleName();

    private UserRepository repository;
    private LiveData<List<UserInfo>> result;

    public ContactSearchResultViewModel(Application application, String query) {
        super(application);
        repository = new UserRepository();
        result = repository.getUsers(query);
        result.observeForever(userInfos -> {
            for (UserInfo userInfo: userInfos) {
                Log.i(TAG, userInfo.toString());
            }
        });
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
