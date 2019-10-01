package com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;

public class ArtifactDetailViewModel extends AndroidViewModel {

    public static final String TAG = ArtifactDetailViewModel.class.getSimpleName();

    private LiveData<ArtifactItem> selectedPost;
    private ArtifactManager manager;

    public ArtifactDetailViewModel(Application application, String selectedPid) {
        super(application);
        Log.i(TAG, "enter view model cons");
        manager = ArtifactManager.getInstance();
        selectedPost = manager.getArtifactItemByPostId(selectedPid);
    }

    public LiveData<ArtifactItem> getPost() { return selectedPost; }

}
