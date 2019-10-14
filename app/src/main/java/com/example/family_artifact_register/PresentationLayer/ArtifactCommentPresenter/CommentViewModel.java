package com.example.family_artifact_register.PresentationLayer.ArtifactCommentPresenter;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.Artifact;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactComment;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

import static com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem.TAG;

public class CommentViewModel extends AndroidViewModel {

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();
    private ArtifactManager artifactManager = ArtifactManager.getInstance();

    private String commentId;

    public CommentViewModel(@NonNull Application application, String commentId) {
        super(application);
        this.commentId = commentId;
    }

    public LiveData<ArtifactComment> getArtifactComment(String id) {
        Log.d(TAG, "return artifactManager.getArtifactComment");
        return null;
    }
}
