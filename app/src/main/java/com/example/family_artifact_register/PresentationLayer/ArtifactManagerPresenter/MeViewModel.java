package com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.example.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MeViewModel extends AndroidViewModel {

    public static final String TAG = MeViewModel.class.getSimpleName();

    private ArtifactManager artifactManager = ArtifactManager.getInstance();

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();

    private FirebaseStorageHelper fSHelper = FirebaseStorageHelper.getInstance();

    private MutableLiveData<List<ArtifactItem>> artifactList;

    private String currentUid;

    public MeViewModel(Application application) {
        super(application);
        currentUid = userInfoManager.getCurrentUid();
        artifactList = (MutableLiveData<List<ArtifactItem>>) artifactManager.getArtifactItemByUid(currentUid);
        artifactList.observeForever(new Observer<List<ArtifactItem>>() {
            @Override
            public void onChanged(List<ArtifactItem> artifactItems) {
                for(ArtifactItem item: artifactItems) {
                    List<String> mediaDataRemoteUrls = item.getMediaDataUrls();
                    fSHelper.loadByRemoteUri(mediaDataRemoteUrls).observeForever(new Observer<List<Uri>>() {
                        @Override
                        public void onChanged(List<Uri> uris) {
                            Log.d(TAG, "local uris: " + uris.toString());
                            item.setMediaDataUrls(
                                    uris.stream()
                                        .map(Objects::toString)
                                        .collect(Collectors.toList())
                            );
                            artifactList.setValue(artifactList.getValue());
                        }
                    });
                }
            }
        });
    }

    public LiveData<List<ArtifactItem>> getArtifactList() {
        return artifactList;
    }
}
