package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.FoundationLayer.SocialModel.UserRepository;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

public class ContactDetailViewModel extends AndroidViewModel {

    public static final String TAG = ContactDetailViewModel.class.getSimpleName();

    private LiveData<UserInfo> selectedUser;
    private UserInfoManager manager;

    public ContactDetailViewModel(Application application, String selectedUid) {
        super(application);
        Log.i(TAG, "enter view model cons");
        manager = UserInfoManager.getInstance();
        selectedUser = manager.getUserInfo(selectedUid);
    }

    public LiveData<UserInfo> getUser() { return selectedUser; }
}
