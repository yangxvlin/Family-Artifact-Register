package com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.unimelb.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;
import com.unimelb.family_artifact_register.PresentationLayer.Util.UserInfoWrapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ContactRequestViewModel extends AndroidViewModel {

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();
    private FirebaseStorageHelper helper = FirebaseStorageHelper.getInstance();

    private MutableLiveData<Set<Request>> requests = new MutableLiveData<>();

    public ContactRequestViewModel(@NonNull Application application) {
        super(application);

        Set<Request> requestList = new TreeSet<>(new Comparator<Request>() {
            @Override
            public int compare(Request request, Request t1) {
                if(request.getTime() != null && t1.getTime() != null) {
                    return -request.getTime().compareTo(t1.getTime());
                }
                return 0;
            }
        });
        requests.postValue(requestList);

        userInfoManager.listenUserInfo(userInfoManager.getCurrentUid()).observeForever(new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo me) {
                List<String> invitationIDs = new ArrayList<>();
                Map<String, String> invitations = me.getFriendInvitations();
                for(String id: invitations.keySet()) {
                    userInfoManager.listenUserInfo(id).observeForever(new Observer<UserInfo>() {
                        @Override
                        public void onChanged(UserInfo newFriend) {
                            UserInfoWrapper wrapper = new UserInfoWrapper(newFriend);
                            if(wrapper.getPhotoUrl() == null) {
                                wrapper.setPhotoUrl(null);
                                requestList.add(new Request(wrapper, invitations.get(id)));
                                requests.postValue(requestList);
                            }
                            else {
                                helper.loadByRemoteUri(wrapper.getPhotoUrl()).observeForever(new Observer<Uri>() {
                                    @Override
                                    public void onChanged(Uri uri) {
                                        wrapper.setPhotoUrl(uri.toString());
                                        requestList.add(new Request(wrapper, invitations.get(id)));
                                        requests.postValue(requestList);
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }

    public LiveData<Set<Request>> getRequests() {
        return requests;
    }

    public void accept(String uid) {
        userInfoManager.acceptFriendInvitation(uid);
    }

    public String getCurrentUid() { return userInfoManager.getCurrentUid(); }

    public UserInfo getCurrentUser() { return userInfoManager.getCurrentUserInfo(); }
}
