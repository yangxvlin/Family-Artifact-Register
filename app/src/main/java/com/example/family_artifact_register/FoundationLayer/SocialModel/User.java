package com.example.family_artifact_register.FoundationLayer.SocialModel;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class User {

    @PrimaryKey
    public String uid;

    public String username;

    public String nickname;

    public String area;

    public ArrayList<User> friends;
}
