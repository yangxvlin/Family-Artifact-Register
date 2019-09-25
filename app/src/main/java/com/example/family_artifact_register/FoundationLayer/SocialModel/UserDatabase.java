package com.example.family_artifact_register.FoundationLayer.SocialModel;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDAO getUserDAO();

    private static UserDatabase userDB = null;

    public static UserDatabase getInstance(Context appContext) {
        if (userDB == null)
            userDB = Room.databaseBuilder(appContext, UserDatabase.class, "user_database").build();
        return userDB;
    }
}
