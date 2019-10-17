package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

public class Request {

    private UserInfoWrapper user;
    private String time;

    public Request(UserInfoWrapper user, String time) {
        this.user = user;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public UserInfoWrapper getUser() {
        return user;
    }
}
