package com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

public class ContactSearchViewModel extends AndroidViewModel {

    public static final String TAG = ContactSearchViewModel.class.getSimpleName();

    private UserInfoManager manager;
    private String query;

    public ContactSearchViewModel(Application application) {
        super(application);
        Log.i(TAG, "enter view model cons");
        manager = UserInfoManager.getInstance();
        query = null;
    }

//    public LiveData<List<User>> getUsers(List<String> username) { return repository.getUsers(username); }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

}
