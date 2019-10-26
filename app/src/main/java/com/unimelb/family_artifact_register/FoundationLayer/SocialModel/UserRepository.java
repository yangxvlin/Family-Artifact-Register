package com.unimelb.family_artifact_register.FoundationLayer.SocialModel;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

import java.util.List;

/**
 * this class is responsible for communicating with different data sources (remote data source or
 * local cache) to fetch data for the rest of the app
 */
public class UserRepository {

    public static final String TAG = UserRepository.class.getSimpleName();

    private LiveData<List<UserInfo>> friends = null;
    private UserInfoManager manager;


//    private DB db;
//    private Cache cache;

    public UserRepository() {
        this.manager = UserInfoManager.getInstance();
    }

    /***********************************/

    public List<LiveData<UserInfo>> getFriends(List<String> uids) {
        return manager.listenUserInfo(uids);
    }

    public LiveData<UserInfo> getUser(String uid) {
        LiveData<UserInfo> temp = manager.listenUserInfo(uid);
        if (temp == null)
            Log.d(TAG, "manager returns null for getuserinfo call");
        UserInfo user = manager.listenUserInfo(uid).getValue();
        if (user == null)
            Log.d(TAG, "live data contains null");
        return manager.listenUserInfo(uid);
    }

    public LiveData<List<UserInfo>> getUsers(String usernames) {
        return manager.searchUserInfo(usernames);
    }

    // comment from codelab:
    // You must call this on a non-UI thread or your app will crash. Room ensures that you don't
    // do any long-running operations on the main thread, blocking the UI.
//    public void insert (User user) { new insertAsyncTask(userDAO).execute(user); }
//    public void insertFriend(Friend friend) { new insertFriendAsyncTask(userDAO).execute(friend); }
    public void insert(User user) {
    }
//    public void insertFriend(UserInfo user1, UserInfo user2) {
//        manager.addFriend(user1, user2);
//    }

    /***********************************/

//    public static FriendRepository getInstance() {
//        if(repository == null)
//            repository = new FriendRepository();
//        return repository;
//    }

//    public MutableLiveData<ArrayList<String>> getFriends(String uid) {
//        MutableLiveData<ArrayList<String>> friends = cache.getFriends(uid);
//        // search local cache first
//        if(friends != null)
//            return friends;
//        // no record in local cache, fetch from network
//        friends = db.getFriends(uid);
//        return friends;
//        return new MutableLiveData<>();
//    }
}
