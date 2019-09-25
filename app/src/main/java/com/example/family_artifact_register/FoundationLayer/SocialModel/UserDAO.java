package com.example.family_artifact_register.FoundationLayer.SocialModel;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.ArrayList;

@Dao
public interface UserDAO {

    @Query("select user.username from user where user.uid = uid")
    String getUsername(String uid);

    @Query("select user.nickname from user where user.uid = uid")
    String getNickname(String uid);

    @Query("select user.area from user where user.uid = uid")
    String getAreaa(String uid);

    @Query("select user.friends from user where user.uid = uid")
    ArrayList<User> getFriends(String uid);

}
