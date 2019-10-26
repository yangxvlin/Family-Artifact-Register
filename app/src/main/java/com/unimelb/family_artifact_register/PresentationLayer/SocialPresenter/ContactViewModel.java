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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ContactViewModel extends AndroidViewModel {

    public static final String TAG = ContactViewModel.class.getSimpleName();

    private UserInfoManager manager = UserInfoManager.getInstance();
    private FirebaseStorageHelper helper = FirebaseStorageHelper.getInstance();

    private LiveData<UserInfo> currentUser = manager.listenUserInfo(manager.getCurrentUid());

    private List<String> friendUids;

//    private MediatorLiveData<List<UserInfo>> friends = new MediatorLiveData<>();
//    private MediatorLiveData<Set<UserInfo>> friends = new MediatorLiveData<>();

    private MediatorLiveData<Set<UserInfoWrapper>> friends = new MediatorLiveData<>();

    private MutableLiveData<UserInfoWrapper> me = new MutableLiveData<>();

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
//                Set<LiveData<UserInfo>> x = new TreeSet<>(new Comparator<LiveData<UserInfo>>() {
//                    @Override
//                    public int compare(LiveData<UserInfo> userInfoLiveData, LiveData<UserInfo> t1) {
//                        return userInfoLiveData.getValue().getUid().compareTo(t1.getValue().getUid());
//                    }
//                });
//                x.addAll(manager.listenUserInfo(friendUids));

                Log.d(TAG, "friend list size: " + friendList.size());
                for (LiveData<UserInfo> i : friendList) {
                    friends.removeSource(i);
                    friends.addSource(i, new Observer<UserInfo>() {
                        @Override
                        public void onChanged(UserInfo userInfo) {
                            Log.d(TAG, "friend info: " + userInfo.toString());
                            UserInfoWrapper wrapper = new UserInfoWrapper(userInfo);
//                            friends.getValue().add(wrapper);
//                            friends.setValue(friends.getValue());
                            String url = wrapper.getPhotoUrl();
                            if (url == null && !wrapper.getUid().equals(manager.getCurrentUid())) {
                                wrapper.setPhotoUrl(null);
                                friends.getValue().add(wrapper);
                                friends.postValue(friends.getValue());
                            } else {
                                helper.loadByRemoteUri(wrapper.getPhotoUrl()).observeForever(new Observer<Uri>() {
                                    @Override
                                    public void onChanged(Uri uri) {
                                        if (!wrapper.getUid().equals(manager.getCurrentUid())) {
                                            wrapper.setPhotoUrl(uri.toString());
                                            friends.getValue().add(wrapper);
                                            friends.postValue(friends.getValue());
                                        }
//                                        wrapper.setPhotoUrl(uri.toString());
//                                        friends.getValue().add(wrapper);
//                                        friends.postValue(friends.getValue());
                                    }
                                });
                            }
//                            friends.getValue().add(userInfo);
//                            friends.setValue(friends.getValue());
                        }
                    });
                }
            }
        });
    }

    public LiveData<Set<UserInfoWrapper>> getContacts() {
        return friends;
    }

    public LiveData<UserInfoWrapper> getPersonalProfile() {
        manager.listenUserInfo(manager.getCurrentUid()).observeForever(new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                UserInfoWrapper wrapper = new UserInfoWrapper(userInfo);
                if (wrapper.getPhotoUrl() == null) {
                    wrapper.setPhotoUrl(null);
                    me.postValue(wrapper);
                } else {
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

    public String getCurrentUid() {
        return manager.getCurrentUid();
    }
}
