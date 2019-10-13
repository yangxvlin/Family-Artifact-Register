package com.example.family_artifact_register.PresentationLayer.ArtifactDetailPresenter;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.Artifact;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.example.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DetailViewModel extends AndroidViewModel {

    public static final String TAG = DetailViewModel.class.getSimpleName();

    private ArtifactManager artifactManager = ArtifactManager.getInstance();

    private FirebaseStorageHelper fSHelper = FirebaseStorageHelper.getInstance();

    private MutableLiveData<ArtifactItem> item;

    private MutableLiveData<ArtifactItemWrapper> itemWrapper = new MutableLiveData<>();

    public DetailViewModel(Application application) {
        super(application);
    }

    public LiveData<ArtifactItem> getArtifactItem(String itemID) {
        item = (MutableLiveData<ArtifactItem>) artifactManager.getArtifactItemByPostId(itemID);
//        helper.loadByRemoteUri(item.getValue().ge ;
        item.observeForever(new Observer<ArtifactItem>() {
            @Override
            public void onChanged(ArtifactItem artifactItem) {
//                List<String> mediaDataRemoteUrls = item.getValue().getMediaDataUrls();
//                helper.loadByRemoteUri(mediaDataRemoteUrls).observeForever(new Observer<List<Uri>>() {
//                    @Override
//                    public void onChanged(List<Uri> uris) {
//                        Log.d(TAG, "local uris: " + uris.toString());
//                        item.getValue().setMediaDataUrls(
//                                uris.stream()
//                                        .map(Objects::toString)
//                                        .collect(Collectors.toList())
//                        );
//                        ((MutableLiveData) item).setValue(item.getValue());
////                artifactList.setValue(artifactList.getValue());
//                    }
//                });

                List<String> mediaDataRemoteUrls = artifactItem.getMediaDataUrls();
                ArtifactItemWrapper wrapper = new ArtifactItemWrapper(artifactItem);

                fSHelper.loadByRemoteUri(mediaDataRemoteUrls).observeForever(new Observer<List<Uri>>() {
                    @Override
                    public void onChanged(List<Uri> uris) {
                        Log.d(TAG, "local uris: " + uris.toString());

                        // load data to wrapper
                        wrapper.setLocalMediaDataUrls(
                                uris.stream()
                                        .map(Objects::toString)
                                        .collect(Collectors.toList())
                        );

                        itemWrapper.setValue(wrapper);
                        // artifactList.setValue(artifactList.getValue());
                    }
                });
            }
        });

        return item;
    }

    public LiveData<ArtifactItemWrapper> getArtifact() {
        return itemWrapper;
    }


}
