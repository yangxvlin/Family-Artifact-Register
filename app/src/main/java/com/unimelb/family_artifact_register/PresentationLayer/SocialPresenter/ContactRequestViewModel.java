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

/**
 * this class is responsible for communicating with DB (retrieving data or posting updates) and
 * prepares data for {@link com.unimelb.family_artifact_register.UI.Social.NewContact.ContactRequestActivity}
 * to display
 */
public class ContactRequestViewModel extends AndroidViewModel {

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();
    private FirebaseStorageHelper helper = FirebaseStorageHelper.getInstance();

    // requests to be displayed
    private MutableLiveData<Set<Request>> requests = new MutableLiveData<>();

    /**
     * public constructor for instantiating a new {@link ContactRequestViewModel}
     *
     * @param application the application
     */
    public ContactRequestViewModel(@NonNull Application application) {
        super(application);

        Set<Request> requestList = new TreeSet<>(new Comparator<Request>() {
            @Override
            public int compare(Request request, Request t1) {
                if (request.getTime() != null && t1.getTime() != null) {
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
                for (String id : invitations.keySet()) {
                    userInfoManager.listenUserInfo(id).observeForever(new Observer<UserInfo>() {
                        @Override
                        public void onChanged(UserInfo newFriend) {
                            UserInfoWrapper wrapper = new UserInfoWrapper(newFriend);
                            if (wrapper.getPhotoUrl() == null) {
                                wrapper.setPhotoUrl(null);
                                requestList.add(new Request(wrapper, invitations.get(id)));
                                requests.postValue(requestList);
                            } else {
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

    /**
     * get a list of requests to be displayed
     *
     * @return a list of requests to be displayed
     */
    public LiveData<Set<Request>> getRequests() {
        return requests;
    }

    /**
     * accept an invitation
     *
     * @param uid the uid of the user whose invitaiton is being accepted
     */
    public void accept(String uid) {
        userInfoManager.acceptFriendInvitation(uid);
    }

    /**
     * get the uid of current user
     *
     * @return the uid of current user
     */
    public String getCurrentUid() {
        return userInfoManager.getCurrentUid();
    }

    /**
     * get the current user
     *
     * @return the current user
     */
    public UserInfo getCurrentUser() {
        return userInfoManager.getCurrentUserInfo();
    }
}
