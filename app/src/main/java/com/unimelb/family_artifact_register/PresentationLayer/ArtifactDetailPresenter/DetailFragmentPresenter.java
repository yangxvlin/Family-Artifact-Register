package com.unimelb.family_artifact_register.PresentationLayer.ArtifactDetailPresenter;

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;

/**
 * @author Haichao Song 854035,
 * @time 2019-10-07 23:14:43
 * @description present detail fragment for detail fragment class
 * Deprecate because change detail fragment to activity.
 */
@Deprecated
public class DetailFragmentPresenter {

    private IView view;
    private ArtifactItem artifactItem;

    public DetailFragmentPresenter(IView view) {
        this.view = view;
        this.artifactItem = initArtifactItem();
    }

    private ArtifactItem initArtifactItem() {
        ArtifactItem res = new ArtifactItem();
        return res;
    }

    public interface IView {
        void addData(ArtifactItem artifactItem);
    }

}
