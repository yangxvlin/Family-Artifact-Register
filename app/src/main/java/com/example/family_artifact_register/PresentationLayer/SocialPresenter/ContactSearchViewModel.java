package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.example.family_artifact_register.FoundationLayer.SocialModel.UserRepository;

public class ContactSearchViewModel extends AndroidViewModel {

    public static final String TAG = ContactSearchViewModel.class.getSimpleName();

    private UserRepository repository;

    private String query;

    public ContactSearchViewModel(Application application) {
        super(application);
        Log.i(TAG, "enter view model cons");
        repository = new UserRepository(application);
        query = null;
    }

//    public LiveData<List<User>> getUsers(List<String> username) { return repository.getUsers(username); }

    public void setQuery(String query) { this.query = query; }
    public String getQuery() { return query; }
}
