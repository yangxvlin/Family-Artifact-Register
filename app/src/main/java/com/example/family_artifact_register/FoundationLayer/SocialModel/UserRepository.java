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


    private UserDAO userDAO;
    private LiveData<List<User>> friends;


//    private DB db;
//    private Cache cache;

    public UserRepository(Application application) {
        UserDatabase db = UserDatabase.getInstance(application);
        userDAO = db.getUserDAO();
        friends = userDAO.getAllFriends();
    }

    public LiveData<List<User>> getFriends() { return friends; }
    public LiveData<User> getUser(String username) { return userDAO.getUser(username); }
    public LiveData<List<User>> getUsers(List<String> usernames) { return userDAO.getUsers(usernames); }

    // comment from codelab:
    // You must call this on a non-UI thread or your app will crash. Room ensures that you don't
    // do any long-running operations on the main thread, blocking the UI.
    public void insert (User user) {
        new insertAsyncTask(userDAO).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDAO mAsyncTaskDao;

        insertAsyncTask(UserDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insertUser(params[0]);
            return null;
        }
    }



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
