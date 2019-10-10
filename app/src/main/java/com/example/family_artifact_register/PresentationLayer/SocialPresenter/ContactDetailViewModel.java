package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.FoundationLayer.SocialModel.UserRepository;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.example.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;

public class ContactDetailViewModel extends AndroidViewModel {

    public static final String TAG = ContactDetailViewModel.class.getSimpleName();

    private LiveData<UserInfo> selectedUser;

    private UserInfoManager manager;

    private FirebaseStorageHelper helper = FirebaseStorageHelper.getInstance();

    public ContactDetailViewModel(Application application, String selectedUid) {
        super(application);
        Log.i(TAG, "enter view model cons");
        manager = UserInfoManager.getInstance();
        selectedUser = manager.listenUserInfo(selectedUid);
        selectedUser.observeForever(new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                String url = userInfo.getPhotoUrl();
                if(url == null) {
                    selectedUser.getValue().setPhotoUrl(null);
                }
                else {
                    helper.loadByRemoteUri(url).observeForever(new Observer<Uri>() {
                        @Override
                        public void onChanged(Uri uri) {
                            selectedUser.getValue().setPhotoUrl(uri.toString());
                        }
                    });
                }
            }
        });
    }

    public LiveData<UserInfo> getUser() { return selectedUser; }
}
