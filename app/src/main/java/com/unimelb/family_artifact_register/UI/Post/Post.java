package com.unimelb.family_artifact_register.UI.Post;

<<<<<<< HEAD
/**
 * @author Haichao Song 854035,
 * @time 2019-9-20 22:44:12
 * @description post item model before navigation bar.
 * Deprecate and replace by hub model now.
 */
=======
>>>>>>> 12a0d8a99d0a528d8d45489bdec557da828300a3
@Deprecated
public class Post {

    public String postid;
    public String postimage;
    public String description;
    public String publisher;


    public Post(String postid, String postimage, String description, String publisher) {
        this.postid = postid;
        this.postimage = postimage;
        this.description = description;
        this.publisher = publisher;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
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
}
