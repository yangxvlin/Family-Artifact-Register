package com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

import java.util.List;

public class MyTimelineViewModel extends AndroidViewModel {

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();
    private ArtifactManager artifactManager = ArtifactManager.getInstance();

    public MyTimelineViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<ArtifactTimeline>> getTimelines() {
        return artifactManager.getArtifactTimelineByUid(userInfoManager.getCurrentUid());
    }
}
