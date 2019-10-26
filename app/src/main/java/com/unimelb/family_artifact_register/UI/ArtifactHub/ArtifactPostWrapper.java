package com.unimelb.family_artifact_register.UI.ArtifactHub;

import androidx.annotation.Nullable;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter.UserInfoWrapper;

/**
 * @author Haichao Song 854035,
 * @time 2019-10-13 15:03:48
 * @description wrapper to pair artifact item with user info in order to show poster avatar and
 * display name in the hub fragment.
 */
public class ArtifactPostWrapper {

    private ArtifactItemWrapper artifactItemWrapper;

    private UserInfoWrapper userInfoWrapper;

    /**
     * Construct wrapper
     * @param artifactItemWrapper the artifact information for post item
     * @param userInfoWrapper the poster information for post item
     */
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
