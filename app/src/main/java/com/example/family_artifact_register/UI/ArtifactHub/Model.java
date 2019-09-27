package com.example.family_artifact_register.UI.ArtifactHub;

/**
 * @author Haichao Song 854035,
 * @time 2019-9-18 14:24:56
 * @description Model for each card in artifact hub
 */
public class Model {

    public String postid, description, publisher, username;
    public int postimage, avatar;

    public Model() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPostimage() {
        return postimage;
    }

    public void setPostimage(int postimage) {
        this.postimage = postimage;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}

