package com.example.family_artifact_register.FoundationLayer.SocialModel;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey
    @NonNull
    public String uid;

    public String username;

    public String nickname;

    public String area;

    public User(String uid, String username, String nickname, String area) {
        this.uid = uid;
        this.username =username;
        this.nickname = nickname;
        this.area = area;
    }
}
