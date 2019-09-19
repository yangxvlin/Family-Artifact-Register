package com.example.family_artifact_register.UI.ArtifactHub;

/**
 * @author Haichao Song 854035,
 * @time 2019-9-18 14:24:56
 * @description Model for each card in artifact hub
 */
public class Model {

    private String title, description, username;
    private int img, avatar;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
