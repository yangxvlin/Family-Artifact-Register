package com.example.family_artifact_register.FoundationLayer.SocialModel;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

/**
 * class used to handle many-to-many unary relationship for user entity
 */
@Entity(tableName = "friend_table",
        primaryKeys = {"user_1", "user_2"},
        foreignKeys = {
                @ForeignKey(entity = User.class,
                            parentColumns = "username",
                            childColumns = "user_1"),
                @ForeignKey(entity = User.class,
                            parentColumns = "username",
                            childColumns = "user_2")},
        indices = {@Index("user_1"), @Index("user_2")})
public class Friend {
    @NonNull
    public String user_1;
    @NonNull
    public String user_2;

    public Friend(String user_1, String user_2) {
        this.user_1 = user_1;
        this.user_2 = user_2;
    }
}
