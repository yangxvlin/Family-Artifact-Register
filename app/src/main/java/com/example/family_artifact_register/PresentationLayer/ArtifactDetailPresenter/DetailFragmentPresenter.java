package com.example.family_artifact_register.PresentationLayer.ArtifactDetailPresenter;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.Artifact;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;

import java.util.ArrayList;
import java.util.List;

public class DetailFragmentPresenter {

    private IView view;
    private ArtifactItem artifactItem;

    public DetailFragmentPresenter(IView view) {
        this.view = view;
        this.artifactItem = initArtifactItem();
    }

    private ArtifactItem initArtifactItem() {
        ArtifactTimeline timeline1 = new ArtifactTimeline("timeline 1");

        ArtifactItem res = new ArtifactItem();
        return res;
    }

    public interface IView {
        void addData(String Pid);
    }

}
