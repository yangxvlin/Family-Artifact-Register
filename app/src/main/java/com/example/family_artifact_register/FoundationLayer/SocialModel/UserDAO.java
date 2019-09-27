package com.example.family_artifact_register.FoundationLayer.SocialModel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("select user_table.username from user_table")
    LiveData<List<String>> getUserNames();

    @Query("select user_table.nickname from user_table")
    LiveData<List<String>> getNicknames();

    @Query("select user_table.area from user_table")
    LiveData<List<String>> getAreas();

    @Query("select * from user_table")
    LiveData<List<User>> getAllUser();

    // used for searching friend
    @Query("select * from user_table where username in (:usernames)")
    LiveData<List<User>> getUsers(List<String> usernames);

    @Query("select * from user_table where username = :username")
    LiveData<User> getUser(String username);

    @Insert
    void insertUser(User user);

    @Query("delete from user_table")
    void deleteAllUser();

//    @Query("select * from user_table " +
//            "inner join friend_table " +
//            "on user_table.username = friend_table.user_1 " +
//            "where user_table.username = :username")
//    LiveData<List<User>> getFriends(String username);

    @Query("select * from user_table " +
            "inner join friend_table " +
            "on user_table.username = friend_table.friend")
    LiveData<List<User>> getAllFriends();

    @Insert
    void insertFriend(Friend friend);

    @Query("delete from friend_table")
    void deleteAllFriend();
}
