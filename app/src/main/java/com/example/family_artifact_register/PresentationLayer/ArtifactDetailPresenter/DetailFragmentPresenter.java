package com.example.family_artifact_register.PresentationLayer.ArtifactDetailPresenter;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.Artifact;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;

import java.util.ArrayList;
import java.util.List;

public class DetailFragmentPresenter {

    private IView view;
    private ArtifactItem artifactItem;

    public DetailFragmentPresenter(IView view, ArtifactItem artifactItem) {
        this.view = view;
        this.artifactItem = artifactItem;
    }

    public interface IView {
        void addData(ArtifactItem artifactItem);
    }

}
