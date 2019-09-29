package com.example.family_artifact_register.FoundationLayer.SocialModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * this class is responsible for communicating with different data sources
 * (remote data source or local cache) to fetch data for the rest of the app
 */
public class UserRepository {


    private LiveData<List<User>> friends = null;


//    private DB db;
//    private Cache cache;

    public UserRepository(Application application) {}

    /***********************************/

    public LiveData<List<User>> getFriends() { return null; }
    public LiveData<User> getUser(String username) { return null; }
    public LiveData<List<User>> getUsers(List<String> usernames) { return null; }

    // comment from codelab:
    // You must call this on a non-UI thread or your app will crash. Room ensures that you don't
    // do any long-running operations on the main thread, blocking the UI.
//    public void insert (User user) { new insertAsyncTask(userDAO).execute(user); }
//    public void insertFriend(Friend friend) { new insertFriendAsyncTask(userDAO).execute(friend); }
    public void insert (User user) {}
    public void insertFriend(Friend friend) {}

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
