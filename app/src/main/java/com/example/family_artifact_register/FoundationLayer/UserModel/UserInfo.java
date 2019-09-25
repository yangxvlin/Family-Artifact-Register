package com.example.family_artifact_register.FoundationLayer.UserModel;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserInfo implements Parcelable, Serializable {
    private String uid;
    private String displayName;
    private String email;
    private String phoneNumber;
    private String photoUrl;

    private List<String> friendUids;
    private List<String> artifactIds;

    public UserInfo() {
        // Required constructor for FireStore
    }

    /**
     * Private constructor for UserInfo class
     *
     * @param uid         Uid given
     * @param displayName Username when registered
     * @param email       Email account used
     * @param phoneNumber Phone number used
     * @param photoUrl    The photoUrl of user
     */
    public UserInfo(String uid, String displayName, String email, String phoneNumber,
                    String photoUrl, List<String> friendUids, List<String> artifactIds) {
        this.uid = uid;
        this.displayName = displayName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.photoUrl = photoUrl;
        this.friendUids = friendUids;
        this.artifactIds = artifactIds;
    }

    public UserInfo(String uid, String displayName) {
        this.uid = uid;
        this.displayName = displayName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.photoUrl = photoUrl;
        this.friendUids = friendUids;
        this.artifactIds = artifactIds;
    }

    public String getUid() {
        return uid;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getFriendUids() {
        return friendUids;
    }

    public List<String> getArtifactIds() {
        return artifactIds;
    }

    public void addFriend(String friendUid) {
        this.friendUids.add(friendUid);
    }

    public void removeFriend(UserInfo friend) {
        this.friendUids.remove(friend);
    }

    public void addArtifact(String artifactId) {
        this.artifactIds.add(artifactId);
    }

    public void removeArtifact(String artifactId) {
        this.artifactIds.remove(artifactId);
    }

    @Override
    public int describeContents() {
        // TODO (Dovermore, 2019-09-25): Implement describeContents
        return 0;
    }


    public void writeToParcel(Parcel out, int flags) {
        out.writeString(uid);
        out.writeString(displayName);
        out.writeString(email);
        out.writeString(phoneNumber);
        out.writeString(photoUrl);

        out.writeList(friendUids);
        out.writeList(artifactIds);
    }

    public static final Parcelable.Creator<UserInfo> CREATOR
            = new Parcelable.Creator<UserInfo>() {
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    /**
     * Constructor used for parcelable
     *
     * @param in The parcel with information
     */
    private UserInfo(Parcel in) {
        this(
                in.readString(),
                in.readString(),
                in.readString(),
                in.readString(),
                in.readString(),
                new ArrayList<>(),
                new ArrayList<>()
        );
        in.readStringList(this.friendUids);
        in.readStringList(this.artifactIds);
    }

    @NotNull
    @Override
    public String toString() {
        return String.format(
                "uid: %s, displayName: %s, email: %s, phoneNumber: %s," +
                        "photoUrl: %s",
                uid, displayName, email, phoneNumber, photoUrl
        );
    }
}