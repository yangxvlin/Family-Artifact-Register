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
 * prepares data for {@link com.unimelb.family_artifact_register.UI.Social.NewContact.NewContactDetailActivity}
 * to display
 */
public class NewContactDetailViewModel extends AndroidViewModel {

    /**
     * class tag
     */
    public static final String TAG = NewContactDetailViewModel.class.getSimpleName();

    // the uid of the user to be displayed
    private String uid;

    private UserInfoManager manager = UserInfoManager.getInstance();
    private FirebaseStorageHelper helper = FirebaseStorageHelper.getInstance();

    // the user to be displayed
    private MutableLiveData<UserInfoWrapper> friend = new MutableLiveData<>();

    /**
     * public constructor for instantiating a new {@link NewContactDetailViewModel}
     *
     * @param application the application
     * @param uid         the unique uid of the user to be displayed
     */
    public NewContactDetailViewModel(Application application, String uid) {
        super(application);
        this.uid = uid;
        manager.listenUserInfo(uid).observeForever(new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                Log.d(TAG, "user info come back from DB: " + userInfo.toString());
                UserInfoWrapper wrapper = new UserInfoWrapper(userInfo);
                String url = wrapper.getPhotoUrl();
                if (url == null) {
                    wrapper.setPhotoUrl(null);
                    friend.postValue(wrapper);
                } else {
                    helper.loadByRemoteUri(url).observeForever(new Observer<Uri>() {
                        @Override
                        public void onChanged(Uri uri) {
                            Log.d(TAG, "photo uri come back from DB: " + uri.toString());
                            wrapper.setPhotoUrl(uri.toString());
                            friend.postValue(wrapper);
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
        return friend;
    }

    /**
     * add a friend by sending an invitation
     *
     * @param uid the uid of the user to whom the invitation is going to be sent
     */
    public void addFriend(String uid) {
        manager.sendFriendInvitation(uid);
    }

    /**
     * get the uid of the user to be displayed
     *
     * @return the uid of the user to be displayed
     */
    public String getUserUid() {
        return uid;
    }
}
