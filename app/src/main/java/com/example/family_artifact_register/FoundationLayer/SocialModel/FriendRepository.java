package com.example.family_artifact_register.FoundationLayer.SocialModel;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

/**
 * this class is responsible for communicating with different data sources
 * (remote data source or local cache) to fetch data for the rest of the app
 */
public class FriendRepository {

    private static FriendRepository repository = null;

//    private DB db;
//    private Cache cache;

    public static FriendRepository getInstance() {
        if(repository == null)
            repository = new FriendRepository();
        return repository;
    }

    public MutableLiveData<ArrayList<String>> getFriends(String uid) {
//        MutableLiveData<ArrayList<String>> friends = cache.getFriends(uid);
//        // search local cache first
//        if(friends != null)
//            return friends;
//        // no record in local cache, fetch from network
//        friends = db.getFriends(uid);
//        return friends;
        return new MutableLiveData<>();
    }
}
