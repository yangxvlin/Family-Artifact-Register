package com.example.family_artifact_register.FoundationLayer.ArtifactModel;

public class ArtifactComment extends Artifact {


    /**
     * the text of the comment
     */
    private String comment;

    /**
     * the user send comment
     */
    private String sender;

    /**
     * the time send the comment
     */
    private String sendTime;

    /**
     * Create new artifact comment. This constructor is used by firestore and shouldn't be accessed
     * externally
     * @deprecated Use manager/newInstance method to create new artifact. Don't use constructor
     */
    public ArtifactComment(String postId, String uid, String sendTime, String uploadDateTime,
                            String lastUpdateDateTime, String comment, String sender) {
        super(postId, uid, uploadDateTime, lastUpdateDateTime);
        this.comment = comment;
        this.sender = sender;
        this.sendTime = sendTime;
    }

    public ArtifactComment() { super(); }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String getPostId() {
        return super.getPostId();
    }

    @Override
    public String getUid() {
        return super.getUid();
    }

    @Override
    public String getUploadDateTime() {
        return super.getUploadDateTime();
    }

    @Override
    public String getLastUpdateDateTime() {
        return super.getLastUpdateDateTime();
    }
}
