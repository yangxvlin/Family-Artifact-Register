package com.example.family_artifact_register.PresentationLayer.ArtifactDetailPresenter;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;

public class DetailViewModel extends AndroidViewModel {

    public static final String TAG = DetailViewModel.class.getSimpleName();

    private ArtifactManager artifactManager = ArtifactManager.getInstance();

    public DetailViewModel(Application application) {
        super(application);
    }

    public LiveData<ArtifactItem> getArtifactItem(String itemID) {
        return artifactManager.getArtifactItemByPostId(itemID);
    }

}
