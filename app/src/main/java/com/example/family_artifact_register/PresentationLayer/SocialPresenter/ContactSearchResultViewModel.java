package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.FoundationLayer.SocialModel.UserRepository;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.example.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;
import com.example.family_artifact_register.UI.Social.ContactFragment;

import java.util.ArrayList;
import java.util.List;

public class ContactSearchResultViewModel extends AndroidViewModel {
    /**
     * class tag
     */
    public static final String TAG = ContactSearchResultViewModel.class.getSimpleName();

    private UserInfoManager manager = UserInfoManager.getInstance();
    private FirebaseStorageHelper helper = FirebaseStorageHelper.getInstance();

    private MutableLiveData<List<UserInfoWrapper>> result = new MutableLiveData<>();
    private UserInfo currentUser;

    private String displayMode;

    public ContactSearchResultViewModel(Application application, String query) {
        super(application);

        // set the display mode for list item
        if(query.indexOf('@') >= 0) {
            displayMode = "email";
        }
        else {
            displayMode = "displayname";
        }
        Log.d(TAG, "query entered: " + query);
        Log.d(TAG, "mode: " + displayMode);

        manager.searchUserInfo(query).observeForever(new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> userInfos) {
                ArrayList<UserInfoWrapper> resultList = new ArrayList<>();
                for(UserInfo i: userInfos) {
                    UserInfoWrapper wrapper = new UserInfoWrapper(i);
                    String url = wrapper.getPhotoUrl();
                    if(url == null) {
                        wrapper.setPhotoUrl(null);
                        resultList.add(wrapper);
                        result.postValue(resultList);
                    }
                    else {
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
//        result.observeForever(userInfos -> {
//            for (UserInfo userInfo: userInfos) {
//                Log.i(TAG, userInfo.toString());
//            }
//        });
    }

    public LiveData<List<UserInfoWrapper>> getUsers() { return result; }

    public UserInfo getCurrentUser() { return currentUser; }

    public String getDisplayMode() {
        return displayMode;
    }
}
