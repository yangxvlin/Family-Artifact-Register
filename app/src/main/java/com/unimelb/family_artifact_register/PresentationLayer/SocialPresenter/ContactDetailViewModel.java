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

/**
 * this class is responsible for communicating with DB (retrieving data or posting updates) and
 * prepares data for {@link com.unimelb.family_artifact_register.UI.Social.Contact.ContactDetailActivity}
 * to display
 */
public class ContactDetailViewModel extends AndroidViewModel {

    /**
     * class tag
     */
    public static final String TAG = ContactDetailViewModel.class.getSimpleName();

    private UserInfoManager manager = UserInfoManager.getInstance();
    private FirebaseStorageHelper helper = FirebaseStorageHelper.getInstance();

    // the user to be displayed
    private MutableLiveData<UserInfoWrapper> selectedUser = new MutableLiveData<>();

    /**
     * public constructor for instantiating a new {@link ContactDetailViewModel}
     *
     * @param application the application
     * @param selectedUid the unique id of the user to be displayed
     */
    public ContactDetailViewModel(Application application, String selectedUid) {
        super(application);
        Log.i(TAG, "enter view model cons");
        manager.listenUserInfo(selectedUid).observeForever(new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                Log.d(TAG, "user info come back from DB: " + userInfo.toString());
                UserInfoWrapper wrapper = new UserInfoWrapper(userInfo);
                String url = wrapper.getPhotoUrl();
                if (url == null) {
                    wrapper.setPhotoUrl(null);
                    selectedUser.postValue(wrapper);
                } else {
                    helper.loadByRemoteUri(url).observeForever(new Observer<Uri>() {
                        @Override
                        public void onChanged(Uri uri) {
                            Log.d(TAG, "photo uri come back from DB: " + uri.toString());
                            wrapper.setPhotoUrl(uri.toString());
                            selectedUser.postValue(wrapper);
                        }
                    });
                }
            }
        });
    }

    /**
     * get the user to be displayed
     *
     * @return the user to be displayed
     */
    public LiveData<UserInfoWrapper> getUser() {
        return selectedUser;
    }
}
