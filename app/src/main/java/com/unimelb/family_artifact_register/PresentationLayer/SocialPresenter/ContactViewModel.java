package com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.unimelb.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;
import com.unimelb.family_artifact_register.PresentationLayer.Util.UserInfoWrapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * this class is responsible for communicating with DB (retrieving data or posting updates)
 * and prepares data for {@link com.unimelb.family_artifact_register.UI.Social.ContactFragment} to display
 */
public class ContactViewModel extends AndroidViewModel {

    /**
     * class tag
     */
    public static final String TAG = ContactViewModel.class.getSimpleName();

    private UserInfoManager manager = UserInfoManager.getInstance();
    private FirebaseStorageHelper helper = FirebaseStorageHelper.getInstance();

    // temp variable
    private LiveData<UserInfo> currentUser = manager.listenUserInfo(manager.getCurrentUid());

    // temp variable
    private List<String> friendUids;

    // contacts the current user has
    private MediatorLiveData<Set<UserInfoWrapper>> friends = new MediatorLiveData<>();

    // current user
    private MutableLiveData<UserInfoWrapper> me = new MutableLiveData<>();

    /**
     * public constructor for instantiating a new {@link ContactViewModel}
     * @param application the application
     */
    public ContactViewModel(Application application) {
        super(application);

        currentUser.observeForever(new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                friendUids = new ArrayList<>(userInfo.getFriendUids().keySet());

                friends.setValue(new TreeSet<>(new Comparator<UserInfoWrapper>() {
                    @Override
                    public int compare(UserInfoWrapper userInfo, UserInfoWrapper t1) {
                        return userInfo.getUid().compareTo(t1.getUid());
                    }
                }));

                List<LiveData<UserInfo>> friendList = manager.listenUserInfo(friendUids);

                Log.d(TAG, "friend list size: " + friendList.size());
                for(LiveData<UserInfo> i: friendList) {
                    friends.removeSource(i);
                    friends.addSource(i, new Observer<UserInfo>() {
                        @Override
                        public void onChanged(UserInfo userInfo) {
                            Log.d(TAG, "friend info: " + userInfo.toString());
                            UserInfoWrapper wrapper = new UserInfoWrapper(userInfo);
                            String url = wrapper.getPhotoUrl();
                            if(url == null && !wrapper.getUid().equals(manager.getCurrentUid())) {
                                wrapper.setPhotoUrl(null);
                                friends.getValue().add(wrapper);
                                friends.postValue(friends.getValue());
                            }
                            else {
                                helper.loadByRemoteUri(wrapper.getPhotoUrl()).observeForever(new Observer<Uri>() {
                                    @Override
                                    public void onChanged(Uri uri) {
                                        if(!wrapper.getUid().equals(manager.getCurrentUid())) {
                                            wrapper.setPhotoUrl(uri.toString());
                                            friends.getValue().add(wrapper);
                                            friends.postValue(friends.getValue());
                                        }
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
     * get data of all the contacts the current user has
     * @return a list of contacts the current user has
     */
    public LiveData<Set<UserInfoWrapper>> getContacts() {
        return friends;
    }

    /**
     * get the information of current user
     * @return the current user
     */
    public LiveData<UserInfoWrapper> getPersonalProfile() {
        manager.listenUserInfo(manager.getCurrentUid()).observeForever(new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                UserInfoWrapper wrapper = new UserInfoWrapper(userInfo);
                if(wrapper.getPhotoUrl() == null) {
                    wrapper.setPhotoUrl(null);
                    me.postValue(wrapper);
                }
                else {
                    helper.loadByRemoteUri(wrapper.getPhotoUrl()).observeForever(new Observer<Uri>() {
                        @Override
                        public void onChanged(Uri uri) {
                            wrapper.setPhotoUrl(uri.toString());
                            me.postValue(wrapper);
                        }
                    });
                }

            }
        });
        return me;
    }

    /**
     * get the uid of current user
     * @return the uid of current user
     */
    public String getCurrentUid() { return manager.getCurrentUid(); }
}
