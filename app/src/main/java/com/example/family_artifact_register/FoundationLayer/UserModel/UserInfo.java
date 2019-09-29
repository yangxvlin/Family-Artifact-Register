package com.example.family_artifact_register.FoundationLayer.UserModel;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserInfo implements Parcelable, Serializable, Comparable<UserInfo> {
    private String uid;
    private String displayName;
    private String email;
    private String phoneNumber;
    private String photoUrl;

    private HashSet<String> friendUids;
    private HashSet<String> artifactIds;

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
        this.friendUids = new HashSet<>(friendUids);
        this.artifactIds = new HashSet<>(artifactIds);
    }

    public UserInfo(String uid, String displayName) {
        this(uid, displayName, null, null, null, new ArrayList<>(), new ArrayList<>());
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
        return new ArrayList<>(friendUids);
    }

    public List<String> getArtifactIds() {
        return new ArrayList<>(artifactIds);
    }

    public void addFriend(String friendUid) {
        friendUids.add(friendUid);
    }

    public void removeFriend(String friendUid) {
        friendUids.remove(friendUid);
    }

    public void addArtifact(String artifactId) {
        artifactIds.add(artifactId);
    }

    public void removeArtifact(String artifactId) {
        artifactIds.remove(artifactId);
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

        out.writeList(new ArrayList<>(friendUids));
        out.writeList(new ArrayList<>(artifactIds));
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
                null,
                null
        );
        // Set friend list and artifact list
        List<String> friendUidsArr = new ArrayList<>();
        in.readStringList(friendUidsArr);
        this.friendUids = new HashSet<>(friendUidsArr);

        List<String> artifactIdsArr = new ArrayList<>();
        in.readStringList(artifactIdsArr);
        this.artifactIds = new HashSet<>(artifactIdsArr);
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

    /**
     * Override Compare to to have an ordering of things (May be easier for frontend to manage)
     * @param o The other to compare to
     * @return Save order as the comparison between displayed name
     */
    @Override
    public int compareTo(UserInfo o) {
        return this.getDisplayName().compareTo(o.getDisplayName());
    }
}