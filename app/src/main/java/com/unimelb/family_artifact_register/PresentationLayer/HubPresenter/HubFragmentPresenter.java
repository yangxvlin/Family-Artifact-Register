package com.unimelb.family_artifact_register.PresentationLayer.HubPresenter;

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;

import java.util.ArrayList;
import java.util.List;

public class HubFragmentPresenter {

    private List<ArtifactItem> artifacts;
    private IView view;

    public HubFragmentPresenter(IView view) {
        this.view = view;
        // tmp data
        this.artifacts = initArtifacts();

        for (ArtifactItem aItem: this.artifacts) {
//            this.view.addData(((Artifact) aItem).getCreatedDate(), aItem.getDescription(), aItem.getImages(), aItem.getVideos());
        }
    }

    private List<ArtifactItem> initArtifacts() {
        ArtifactTimeline timeline1 = new ArtifactTimeline("timeline 1");

        List<ArtifactItem> res = new ArrayList<>();
        return res;
    }

    public interface IView {
        void addData(ArtifactItem artifactItem);
    }
}
