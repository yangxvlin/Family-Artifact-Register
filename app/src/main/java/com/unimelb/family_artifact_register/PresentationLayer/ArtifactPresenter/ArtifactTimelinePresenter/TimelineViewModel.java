package com.unimelb.family_artifact_register.PresentationLayer.ArtifactPresenter.ArtifactTimelinePresenter;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;
import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.unimelb.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;
import com.unimelb.family_artifact_register.PresentationLayer.Util.ArtifactItemWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TimelineViewModel extends AndroidViewModel {

    public static final String TAG = TimelineViewModel.class.getSimpleName();

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();
    private ArtifactManager artifactManager = ArtifactManager.getInstance();
    private FirebaseStorageHelper helper = FirebaseStorageHelper.getInstance();

    private String timelineID;

    private ArrayList<ArtifactItemWrapper> wrappers = new ArrayList<>();
    private MutableLiveData<List<ArtifactItemWrapper>> artifacts = new MutableLiveData<>();

    public TimelineViewModel(@NonNull Application application, String timelineID) {
        super(application);
        this.timelineID = timelineID;
    }

    public LiveData<ArtifactTimeline> getTimeline(String ID) {
        return artifactManager.getArtifactTimelineByPostId(ID);
    }

    public LiveData<List<ArtifactItemWrapper>> getArtifactItems(List<String> IDs) {
        // not sure what is the unit for time out, assume ms
        artifacts.postValue(wrappers);
        artifactManager.getArtifactItemByPostId(IDs, 5000).observeForever(new Observer<List<ArtifactItem>>() {
            @Override
            public void onChanged(List<ArtifactItem> artifactItems) {
                for (ArtifactItem item : artifactItems) {
                    ArtifactItemWrapper wrapper = new ArtifactItemWrapper(item);
                    Log.d(TAG, "artifact item from DB: " + wrapper.toString());
                    wrappers.add(wrapper);
                    artifacts.postValue(wrappers);
                    helper.loadByRemoteUri(item.getMediaDataUrls()).observeForever(new Observer<List<Uri>>() {
                        @Override
                        public void onChanged(List<Uri> uris) {
                            Log.d(TAG, "url from helper: " + uris.toString());
                            wrapper.setLocalMediaDataUrls(
                                    uris.stream()
                                            .map(Objects::toString)
                                            .collect(Collectors.toList())
                            );
                            artifacts.postValue(wrappers);
                        }
                    });
                }
            }
        });
        return artifacts;
    }
}
