package com.example.family_artifact_register.FoundationLayer.UserModel;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.Artifact;

import java.util.List;

public class User {
    private String token;
    private String name;
    private String avatar;
    private String username;
    private String email;
    private String phoneNumber;

    private List<User> friends;
    private List<Artifact> artifacts;

    private User() {
        // Required constructor for FireStore
    }

    /**
     * Private constructor for User class
     * @param token Token given
     * @param name Name of the user (when registered)
     * @param username User name when registered
     * @param email Email account used
     * @param phoneNumber Phone number used
     * @param avatar The avatar of user
     */
    private User(String token, String name, String username, String email, String phoneNumber,
                 String avatar) {
        this.token = token;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<User> getFriends() {
        return friends;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void addFriend(User friend) {
        this.friends.add(friend);
    }

    public void removeFriend(User friend) {
        this.friends.remove(friend);
    }

    public void addArtifact(Artifact artifact) {
        this.artifacts.add(artifact);
    }

    public void removeArtifact(Artifact artifact) {
        this.artifacts.remove(artifact);
    }
}
