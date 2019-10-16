package com.example.family_artifact_register.PresentationLayer.ArtifactCommentPresenter;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bumptech.glide.load.model.UrlUriLoader;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.Artifact;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactComment;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

import java.util.List;
import java.util.UUID;

import static com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem.TAG;

public class CommentViewModel extends AndroidViewModel {

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();
    private ArtifactManager artifactManager = ArtifactManager.getInstance();

    private String PostID;

    public CommentViewModel(@NonNull Application application, String PostId) {
        super(application);
        this.PostID = PostId;
    }

    public LiveData<List<ArtifactComment>> getComments(String id) {
        return artifactManager.listenCommentByArtifact(id, UUID.randomUUID().toString());
    }

    public LiveData<ArtifactItem> getArtifactItem(String id) {
        return artifactManager.getArtifactItemByPostId(id);
    }
}
