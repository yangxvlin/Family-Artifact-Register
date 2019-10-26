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

public class ContactDetailViewModel extends AndroidViewModel {

    public static final String TAG = ContactDetailViewModel.class.getSimpleName();

    private UserInfoManager manager = UserInfoManager.getInstance();
    private FirebaseStorageHelper helper = FirebaseStorageHelper.getInstance();

    private MutableLiveData<UserInfoWrapper> selectedUser = new MutableLiveData<>();

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

    public LiveData<UserInfoWrapper> getUser() {
        return selectedUser;
    }
}
