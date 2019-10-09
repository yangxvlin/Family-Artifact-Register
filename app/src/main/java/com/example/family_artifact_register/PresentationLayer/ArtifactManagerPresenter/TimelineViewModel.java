package com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

import java.util.List;

public class TimelineViewModel extends AndroidViewModel {

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();
    private ArtifactManager artifactManager = ArtifactManager.getInstance();

    private String timelineID;

    public TimelineViewModel(@NonNull Application application, String timelineID) {
        super(application);
        this.timelineID = timelineID;
    }

    public LiveData<ArtifactTimeline> getTimeline(String ID) {
        return artifactManager.getArtifactTimelineByPostId(ID);
    }

    public LiveData<List<ArtifactItem>> getArtifactItems(List<String> IDs) {
        // not sure what is the unit for time out, assume ms
        return artifactManager.getArtifactItemByPostId(IDs, 5000);
    }
}
