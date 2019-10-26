package com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.unimelb.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;
import com.unimelb.family_artifact_register.PresentationLayer.Util.UserInfoWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * this class is responsible for communicating with DB (retrieving data or posting updates) and
 * prepares data for {@link com.unimelb.family_artifact_register.UI.Social.NewContact.ContactSearchResultActivity}
 * to display
 */
public class ContactSearchResultViewModel extends AndroidViewModel {
    /**
     * class tag
     */
    public static final String TAG = ContactSearchResultViewModel.class.getSimpleName();

    private UserInfoManager manager = UserInfoManager.getInstance();
    private FirebaseStorageHelper helper = FirebaseStorageHelper.getInstance();

    // search result to be displayed
    private MutableLiveData<List<UserInfoWrapper>> result = new MutableLiveData<>();

    // the current user
    private UserInfo currentUser;

    // the display mode used for action bar
    private String displayMode;

    /**
     * public constructor for instantiating a new {@link ContactSearchResultViewModel}
     *
     * @param application the application
     * @param query       the search query
     */
    public ContactSearchResultViewModel(Application application, String query) {
        super(application);

        // set the display mode for list item
        if (query.indexOf('@') >= 0) {
            displayMode = "email";
        } else {
            displayMode = "displayname";
        }
        Log.d(TAG, "query entered: " + query);
        Log.d(TAG, "mode: " + displayMode);

        manager.searchUserInfo(query).observeForever(new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> userInfos) {
                ArrayList<UserInfoWrapper> resultList = new ArrayList<>();
                for (UserInfo i : userInfos) {
                    UserInfoWrapper wrapper = new UserInfoWrapper(i);
                    String url = wrapper.getPhotoUrl();
                    if (url == null) {
                        wrapper.setPhotoUrl(null);
                        resultList.add(wrapper);
                        result.postValue(resultList);
                    } else {
                        helper.loadByRemoteUri(wrapper.getPhotoUrl()).observeForever(new Observer<Uri>() {
                            @Override
                            public void onChanged(Uri uri) {
                                wrapper.setPhotoUrl(uri.toString());
                                resultList.add(wrapper);
                                result.postValue(resultList);
                            }
                        });
                    }
                }
            }
        });
        currentUser = manager.getCurrentUserInfo();
    }

    /**
     * get the search result as a list of users
     *
     * @return a list of users representing the search result
     */
    public LiveData<List<UserInfoWrapper>> getUsers() {
        return result;
    }

    /**
     * get the current user
     *
     * @return the current user
     */
    public UserInfo getCurrentUser() {
        return currentUser;
    }

    /**
     * get the display mode
     *
     * @return the display mode to be used
     */
    public String getDisplayMode() {
        return displayMode;
    }
}
