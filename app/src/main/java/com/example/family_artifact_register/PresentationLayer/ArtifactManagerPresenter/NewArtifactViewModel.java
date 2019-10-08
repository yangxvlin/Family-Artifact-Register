package com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

import java.util.List;

public class NewArtifactViewModel extends AndroidViewModel {

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();
    private ArtifactManager artifactManager = ArtifactManager.getInstance();

    public NewArtifactViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<ArtifactTimeline>> getTimeline() {
        // TODO get current uid has a bug, may need to find another way to retrieve timeline data
        // get all the timelines current user has
        return artifactManager.getArtifactTimelineByUid(userInfoManager.getCurrentUid());
    }
}
