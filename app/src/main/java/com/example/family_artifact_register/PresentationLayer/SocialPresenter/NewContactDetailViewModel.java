package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.family_artifact_register.FoundationLayer.SocialModel.Friend;
import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.FoundationLayer.SocialModel.UserRepository;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.example.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;

public class NewContactDetailViewModel extends AndroidViewModel {

    public static final String TAG = NewContactDetailViewModel.class.getSimpleName();

    private UserInfoManager manager = UserInfoManager.getInstance();
    private FirebaseStorageHelper helper = FirebaseStorageHelper.getInstance();

    private MutableLiveData<UserInfoWrapper> friend = new MutableLiveData<>();

    public NewContactDetailViewModel(Application application, String uid) {
        super(application);
        manager.listenUserInfo(uid).observeForever(new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                Log.d(TAG, "user info come back from DB: " + userInfo.toString());
                UserInfoWrapper wrapper = new UserInfoWrapper(userInfo);
                String url = wrapper.getPhotoUrl();
                if(url == null) {
                    wrapper.setPhotoUrl(null);
                    friend.postValue(wrapper);
                }
                else {
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

    public LiveData<UserInfoWrapper> getUser() { return friend; }

    public void addFriend(String uid) {
        manager.sendFriendInvitation(uid);
    }

    public String getUserUid() {
        return manager.getCurrentUid();
    }
}
