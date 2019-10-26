package com.unimelb.family_artifact_register.PresentationLayer.HubPresenter;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;

/**
 * @author Haichao Song 854035,
 * @time 2019-09-24 22:45:55
 * @description view model to get information from backend to post item Deprecated because it is for
 * post activity and it has been substituted
 */
@Deprecated
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
