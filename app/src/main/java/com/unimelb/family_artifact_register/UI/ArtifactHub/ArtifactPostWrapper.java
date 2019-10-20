package com.unimelb.family_artifact_register.UI.ArtifactHub;

import androidx.annotation.Nullable;

import com.unimelb.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter.UserInfoWrapper;

public class ArtifactPostWrapper {

    private ArtifactItemWrapper artifactItemWrapper;

    private UserInfoWrapper userInfoWrapper;

    public ArtifactPostWrapper(ArtifactItemWrapper artifactItemWrapper, UserInfoWrapper userInfoWrapper) {
        this.artifactItemWrapper = artifactItemWrapper;
        this.userInfoWrapper = userInfoWrapper;
    }

    public ArtifactItemWrapper getArtifactItemWrapper() {
        return artifactItemWrapper;
    }

    public void setArtifactItemWrapper(ArtifactItemWrapper artifactItemWrapper) {
        this.artifactItemWrapper = artifactItemWrapper;
    }

    public UserInfoWrapper getUserInfoWrapper() {
        return userInfoWrapper;
    }

    public void setUserInfoWrapper(UserInfoWrapper userInfoWrapper) {
        this.userInfoWrapper = userInfoWrapper;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.artifactItemWrapper.getPostId().equals(((ArtifactPostWrapper) obj).getArtifactItemWrapper().getPostId());
    }
}
