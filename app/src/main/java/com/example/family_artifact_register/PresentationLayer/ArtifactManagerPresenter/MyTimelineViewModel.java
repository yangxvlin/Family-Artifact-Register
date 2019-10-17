package com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.example.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MyTimelineViewModel extends AndroidViewModel {

    public static final String TAG = MyTimelineViewModel.class.getSimpleName();

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();
    private ArtifactManager artifactManager = ArtifactManager.getInstance();
    private FirebaseStorageHelper helper = FirebaseStorageHelper.getInstance();

    private MutableLiveData<Set<ArtifactTimelineWrapper>> timelines = new MutableLiveData<>();

    private Set<ArtifactTimelineWrapper> timelineList;

    public MyTimelineViewModel(@NonNull Application application) {
        super(application);

        timelineList = new TreeSet<>(new Comparator<ArtifactTimelineWrapper>() {
            @Override
            public int compare(ArtifactTimelineWrapper artifactTimelineWrapper, ArtifactTimelineWrapper t1) {
                return artifactTimelineWrapper.getUploadDateTime().compareTo(t1.getUploadDateTime());
            }
        });
        timelines.postValue(timelineList);
        artifactManager.listenArtifactTimelineByUid(userInfoManager.getCurrentUid(), "MyTimelineViewModel1").observeForever(new Observer<List<ArtifactTimeline>>() {
            @Override
            public void onChanged(List<ArtifactTimeline> artifactTimelines) {
                Log.d(TAG, "get timelines from DB");
                for(ArtifactTimeline timeline: artifactTimelines) {

                    List<ArtifactItemWrapper> wrappers = new ArrayList<>();
                    ArtifactTimelineWrapper timelineWrapper = new ArtifactTimelineWrapper(timeline);
                    // not sure if 5000 is a good timeout value
                    artifactManager.getArtifactItemByPostId(timeline.getArtifactItemPostIds(), 5000).observeForever(new Observer<List<ArtifactItem>>() {
                        @Override
                        public void onChanged(List<ArtifactItem> artifactItems) {
                            Log.d(TAG, "get artifacts from DB using ids in timeline object");
                            for(ArtifactItem item: artifactItems) {
                                Log.d(TAG, "item: " + item.toString());
                                ArtifactItemWrapper wrapper = new ArtifactItemWrapper(item);
                                helper.loadByRemoteUri(item.getMediaDataUrls()).observeForever(new Observer<List<Uri>>() {
                                    @Override
                                    public void onChanged(List<Uri> uris) {
                                        Log.d(TAG, "get urls from firebaes helper: " + uris.toString());
                                        wrapper.setLocalMediaDataUrls(
                                                uris.stream()
                                                        .map(Objects::toString)
                                                        .collect(Collectors.toList())
                                        );
                                        timelineWrapper.getArtifacts().add(wrapper);
                                        timelines.postValue(timelineList);
                                    }
                                });
                            }
//                            ArtifactTimelineWrapper timelineWrapper = new ArtifactTimelineWrapper(timeline);
                            timelineList.add(timelineWrapper);
                            timelines.postValue(timelineList);
                        }
                    });
                }
            }
        });
    }

    public LiveData<Set<ArtifactTimelineWrapper>> getTimelines() {
        return timelines;
    }
}
