package com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter;

import com.unimelb.family_artifact_register.PresentationLayer.Util.UserInfoWrapper;

/**
 * a class that represents the friend request in the system
 */
public class Request {

    // request sending user
    private UserInfoWrapper user;

    // request sending time
    private String time;

    /**
     * public constructor for instantiating a new {@link Request}
     * @param user the sending user
     * @param time request sending time
     */
    public Request(UserInfoWrapper user, String time) {
        this.user = user;
        this.time = time;
    }

    /**
     * get the request sending time
     * @return the request sending time
     */
    public String getTime() {
        return time;
    }

    /**
     * the sending user
     * @return sending user
     */
    public UserInfoWrapper getUser() {
        return user;
    }
}
