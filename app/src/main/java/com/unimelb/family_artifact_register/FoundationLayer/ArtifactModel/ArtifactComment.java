package com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel;

public class ArtifactComment extends Artifact {
    /**
     * the text of the comment
     */
    private String content;

    /**
     * The artifact item id the comment belongs to
     */
    private String artifactItemId;

    /**
     * Create new artifact comment. This constructor is used by firestore and shouldn't be accessed
     * externally
     *
     * @deprecated Use manager/newInstance method to create new artifact. Don't use constructor
     */
    public ArtifactComment() {
        super();
    }

    /**
     * Constructor used by firestore, but this is abstract class, won't be used directly, only for
     * setting up the interfaces
     *
     * @param content The content of comment
     */
    public ArtifactComment(String postId, String uid, String uploadDateTime,
                           String lastUpdateDateTime, String artifactItemId, String content) {
        super(postId, uid, uploadDateTime, lastUpdateDateTime);
        this.artifactItemId = artifactItemId;
        this.content = content;
    }

    /**
     * Create a new instance of comment
     *
     * @param uid            User that created this comment
     * @param artifactItemId Artifact Item this comment is associated to
     * @param content        The content of artifact
     * @return a new instance of comment
     */
    public static ArtifactComment newInstance(String artifactItemId, String uid, String content) {
        return new ArtifactComment(null, uid, null,
                null, artifactItemId, content);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getArtifactItemId() {
        return artifactItemId;
    }
}
