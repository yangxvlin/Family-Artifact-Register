package com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

/**
 * this class is responsible for communicating with DB (retrieving data or posting updates)
 * and prepares data for {@link com.unimelb.family_artifact_register.UI.Social.NewContact.ContactSearchActivity} to display
 */
public class ContactSearchViewModel extends AndroidViewModel {

    public static final String TAG = ContactSearchViewModel.class.getSimpleName();

    private UserInfoManager manager;

    // search query
    private String query;

    /**
     * public constructor for instantiating a new {@link ContactSearchViewModel}
     * @param application the application
     */
    public ContactSearchViewModel(Application application) {
        super(application);
        Log.i(TAG, "enter view model cons");
        manager = UserInfoManager.getInstance();
        query = null;
    }

    /**
     * set the search query
     * @param query the search query
     */
    public void setQuery(String query) { this.query = query; }

    /**
     * get the search query
     * @return the search query
     */
    public String getQuery() { return query; }

}
