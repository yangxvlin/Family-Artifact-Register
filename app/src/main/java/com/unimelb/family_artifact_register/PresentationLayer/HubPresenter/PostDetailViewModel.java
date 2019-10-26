package com.unimelb.family_artifact_register.PresentationLayer.HubPresenter;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;

public class PostDetailViewModel extends AndroidViewModel {

    public static final String TAG = PostDetailViewModel.class.getSimpleName();

    private LiveData<ArtifactItem> selectedPost;
    private ArtifactManager manager;

    public PostDetailViewModel(Application application, String selectedPid) {
        super(application);
        Log.i(TAG, "enter view model cons");
        manager = ArtifactManager.getInstance();
        selectedPost = manager.getArtifactItemByPostId(selectedPid);
    }

    public LiveData<ArtifactItem> getPost() {
        return selectedPost;
    }

}
