package com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

import java.util.List;

public class MeViewModel extends AndroidViewModel {

    public static final String TAG = MeViewModel.class.getSimpleName();

    private ArtifactManager artifactManager = ArtifactManager.getInstance();

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();

    private LiveData<List<ArtifactItem>> artifactList;

    private String currentUid;

    public MeViewModel(Application application) {
        super(application);
        currentUid = userInfoManager.getCurrentUid();
    }

    public LiveData<List<ArtifactItem>> getArtifactList() {
        artifactList = artifactManager.getArtifactItemByUid(currentUid);
        return artifactList;
    }
}
