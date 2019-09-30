package com.example.family_artifact_register.FoundationLayer.SocialModel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

/**
 * class used to handle many-to-many unary relationship for user entity
 */
@Entity(tableName = "friend_table",
        primaryKeys = {"user", "friend"},
        foreignKeys = {
                @ForeignKey(entity = User.class,
                            parentColumns = "username",
                            childColumns = "user"),
                @ForeignKey(entity = User.class,
                            parentColumns = "username",
                            childColumns = "friend")},
        indices = {@Index("user"), @Index("friend")})
public class Friend {
    @NonNull
    public String user;
    @NonNull
    public String friend;

    public Friend(String user, String friend) {
        this.user = user;
        this.friend = friend;
    }
}