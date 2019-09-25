package com.example.family_artifact_register.FoundationLayer.SocialModel;

import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * class used to handle many-to-many unary relationship for user entity
 */
@Entity(tableName = "friend_table",
        primaryKeys = {"user_1", "user_2"},
        foreignKeys = {
                @ForeignKey(entity = User.class,
                            parentColumns = "uid",
                            childColumns = "user_1"),
                @ForeignKey(entity = User.class,
                            parentColumns = "uid",
                            childColumns = "user_2")})
public class Friend {
    public String user_1;
    public String user_2;
}
