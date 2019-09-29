package com.example.family_artifact_register.FoundationLayer.UserModel;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserInfo implements Parcelable, Serializable, Comparable<UserInfo> {
    public static final String UID = "uid";
    public static final String DISPLAY_NAME = "displayName";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String PHOTO_URL = "photoUrl";
    public static final String FRIEND_UIDS = "friendUids";
    public static final String ARTIFACT_IDS = "artifactIds";

    private String uid;
    private String displayName;
    private String email;
    private String phoneNumber;
    private String photoUrl;

    // Hacky solution to keep both list unique
    private Map<String, Boolean> friendUids = new HashMap<>();
    private Map<String, Boolean> artifactIds = new HashMap<>();

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
                    String photoUrl, Map<String, Boolean> friendUids,
                    Map<String, Boolean> artifactIds) {
        this.uid = uid;
        this.displayName = displayName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.photoUrl = photoUrl;
        this.friendUids = friendUids;
        this.artifactIds = artifactIds;
    }

    public UserInfo(String uid, String displayName) {
        this(uid, displayName, null, null, null,
                new HashMap<>(), new HashMap<>());
    }

    public String getUid() {
        return uid;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Map<String, Boolean> getFriendUids() {
        return friendUids;
    }

    public Map<String, Boolean> getArtifactIds() {
        return artifactIds;
    }

    public void addFriend(String friendUid) {
        friendUids.put(friendUid, true);
    }

    public void removeFriend(String friendUid) {
        friendUids.remove(friendUid, true);
    }

    public void addArtifact(String artifactId) {
        artifactIds.put(artifactId, true);
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
        out.writeMap(friendUids);
        out.writeMap(artifactIds);
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
                in.readHashMap(HashMap.class.getClassLoader()),
                in.readHashMap(HashMap.class.getClassLoader())
        );
        // Set friend list and artifact list
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