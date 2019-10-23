package com.unimelb.family_artifact_register.FoundationLayer.UserModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.unimelb.family_artifact_register.Util.TimeToString;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserInfo implements Parcelable, Serializable, Comparable<UserInfo> {
    // Encapsulate database attribute access
    public static final String UID = "uid";
    public static final String DISPLAY_NAME = "displayName";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String PHOTO_URL = "photoUrl";
    public static final String FRIEND_UIDS = "friendUids";
    public static final String FRIEND_INVITATIONS = "friendInvitations";
    public static final String ARTIFACT_ITEM_IDS = "artifactItemIds";
    public static final String ARTIFACT_TIMELINE_IDS = "artifactTimelineIds";

    private String uid;
    private String displayName;
    private String email;
    private String phoneNumber;
    private String photoUrl;

    // Hacky solution to keep both list unique
    private Map<String, String> friendInvitations = new HashMap<>();
    private Map<String, Boolean> friendUids = new HashMap<>();
    private Map<String, Boolean> artifactItemIds = new HashMap<>();
    private Map<String, Boolean> artifactTimelineIds = new HashMap<>();

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
                    String photoUrl, Map<String, Boolean> friendUids, Map<String, String> friendInvitations,
                    Map<String, Boolean> artifactItemIds, Map<String, Boolean> artifactTimelineIds) {
        if (uid == null) {
            uid = System.currentTimeMillis() + UUID.randomUUID().toString();
        }
        this.uid = uid;
        this.displayName = displayName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.photoUrl = photoUrl;

        if (friendUids == null) {
            friendUids = new HashMap<>();
        }
        this.friendUids = friendUids;

        if (friendInvitations == null) {
            friendInvitations = new HashMap<>();
        }
        this.friendInvitations = friendInvitations;

        if (artifactItemIds == null) {
            artifactItemIds = new HashMap<>();
        }
        this.artifactItemIds = artifactItemIds;

        if (artifactTimelineIds == null) {
            artifactTimelineIds = new HashMap<>();
        }
        this.artifactTimelineIds = artifactTimelineIds;
    }

    public static UserInfo newInstance(String uid, String displayName) {
        return new UserInfo(uid, displayName, null, null, null, new HashMap<>(),
                new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    public static UserInfo newInstance(String uid, String displayName, String photoUrl) {
        return new UserInfo(uid, displayName, null, null, photoUrl, new HashMap<>(),
                new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    public static UserInfo newInstance(String uid, String displayName,
                                       String email, String phoneNumber) {
        return new UserInfo(uid, displayName, email, phoneNumber, null, new HashMap<>(),
                new HashMap<>(), new HashMap<>(), new HashMap<>());
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

    public Map<String, String> getFriendInvitations() {
        return friendInvitations;
    }

    public boolean addFriendInvitation(String uid) {
        if (!friendInvitations.containsKey(uid)) {
            friendInvitations.put(uid, TimeToString.getCurrentTimeFormattedString());
            return true;
        }
        return false;
    }

    public boolean removeFriendInvitation(String uid) {
        if (friendInvitations.containsKey(uid)) {
            friendInvitations.remove(uid);
            return true;
        }
        return false;
    }

    public Map<String, Boolean> getFriendUids() {
        return friendUids;
    }

    public Map<String, Boolean> getArtifactItemIds() {
        return artifactItemIds;
    }

    public Map<String, Boolean> getArtifactTimelineIds() {
        return artifactTimelineIds;
    }

    public boolean addFriendUid(String friendUid) {
        if (friendUids.containsKey(friendUid)) {
            return false;
        }
        friendUids.put(friendUid, true);
        return true;
    }

    public boolean removeFriendUid(String friendUid) {
        friendUids.remove(friendUid, true);
        return true;
    }

    public boolean addArtifactItemId(String artifactId) {
        if (artifactItemIds.containsKey(artifactId)) {
            return false;
        }
        artifactItemIds.put(artifactId, true);
        return true;
    }

    public boolean removeArtifactItemId(String artifactId) {
        artifactItemIds.remove(artifactId);
        return true;
    }

    public boolean addArtifactTimelineId(String artifactId) {
        if (artifactTimelineIds.containsKey(artifactId)) {
            return false;
        }
        artifactTimelineIds.put(artifactId, true);
        return true;
    }

    public boolean removeArtifactTimelineId(String artifactId) {
        artifactTimelineIds.remove(artifactId);
        return true;
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
        out.writeMap(friendInvitations);
        out.writeMap(artifactItemIds);
        out.writeMap(artifactTimelineIds);
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
                in.readHashMap(HashMap.class.getClassLoader()),
                in.readHashMap(HashMap.class.getClassLoader()),
                in.readHashMap(HashMap.class.getClassLoader())
        );
        // Set friend list and artifact list
    }

    @NotNull
    @Override
    public String toString() {
        return String.format(
                "uid: %s, displayName: %s, email: %s, phoneNumber: %s, " +
                        "photoUrl: %s, friendUids: %s, friendUids: %s, " +
                        "artifactItemIds: %s, artifactTimelineIds: %s",
                uid, displayName, email, phoneNumber, photoUrl, friendUids, friendInvitations,
                artifactItemIds, artifactTimelineIds
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