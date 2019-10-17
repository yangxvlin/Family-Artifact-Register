package com.example.family_artifact_register.PresentationLayer.ArtifactCommentPresenter;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.Artifact;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactComment;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.UserInfoWrapper;

public class CommentWrapper {

    ArtifactComment artifactComment;

    UserInfoWrapper userInfoWrapper;

    public CommentWrapper(ArtifactComment artifactComment, UserInfoWrapper userInfoWrapper) {
        this.artifactComment = artifactComment;
        this.userInfoWrapper = userInfoWrapper;
    }

    public ArtifactComment getArtifactComment() {
        return artifactComment;
    }

    public void setArtifactComment(ArtifactComment artifactComment) {
        this.artifactComment = artifactComment;
    }

    public UserInfoWrapper getUserInfoWrapper() {
        return userInfoWrapper;
    }

    public void setUserInfoWrapper(UserInfoWrapper userInfoWrapper) {
        this.userInfoWrapper = userInfoWrapper;
    }
}
