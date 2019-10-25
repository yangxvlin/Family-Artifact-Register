package com.unimelb.family_artifact_register.PresentationLayer.Util;

import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * delegates user info in the wrapper
 */
public class UserInfoWrapper {

    private UserInfo user;
    private String photoUrl;
    private String displayName;
    private String email;
    private String phoneNumber;

    private Map<String, Boolean> friendUids = new HashMap<>();
    private Map<String, Boolean> artifactItemIds = new HashMap<>();
    private Map<String, Boolean> artifactTimelineIds = new HashMap<>();

    public UserInfoWrapper(UserInfo user) {
        this.user = user;
        photoUrl = user.getPhotoUrl();
        displayName = user.getDisplayName();
        email = user.getEmail();
        phoneNumber = user.getPhoneNumber();
        friendUids = user.getFriendUids();
        artifactItemIds = user.getArtifactItemIds();
        artifactTimelineIds = user.getArtifactTimelineIds();
    }

    public String getUid() {
        return user.getUid();
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

    public static UserInfo toUserInfo(UserInfoWrapper wrapper) {
//        return new UserInfo(wrapper.getUid(), wrapper.getDisplayName(), wrapper.getEmail(),
//                            wrapper.getPhoneNumber(), wrapper.getPhotoUrl(), wrapper.getFriendUids(),
//                            wrapper.getArtifactItemIds(), wrapper.getArtifactTimelineIds());
        return null;
    }

    @Override
    public String toString() {
        return String.format(
                "uid: %s, displayName: %s, email: %s, phoneNumber: %s, " +
                        "photoUrl: %s, friendUids: %s, artifactItemIds: %s, artifactTimelineIds: %s",
                user.getUid(), displayName, email, phoneNumber, photoUrl, friendUids, artifactItemIds,
                artifactTimelineIds
        );
    }
}
