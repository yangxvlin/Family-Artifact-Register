package com.example.family_artifact_register.FoundationLayer.SocialModel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

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
    LiveData<List<User>> getUsers();

    @Insert
    void insertUser(User user);

    @Query("delete from user_table")
    void deleteAll();
}
