package com.unimelb.family_artifact_register.FoundationLayer.SocialModel;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Data class for user
 * This class is deprecated because local database is not used
 */
@Deprecated
@Entity(tableName = "user_table")
public class User {

//    @PrimaryKey(autoGenerate = true)
//    @NonNull
//    public int uid;

    @PrimaryKey
    @NonNull
    public String username;

    public String nickname;

    public String area;

    public User(String username, String nickname, String area) {
        this.username =username;
        this.nickname = nickname;
        this.area = area;
    }
}
