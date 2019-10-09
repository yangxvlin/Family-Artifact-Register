package com.example.family_artifact_register.PresentationLayer.ArtifactDetailPresenter;

import java.util.ArrayList;
import java.util.List;

public class DetailFragmentPresenter {

    private List<String> images;
    private IView view;

    public DetailFragmentPresenter(IView view) {
        this.view = view;
        // tmp data
        this.images = initImages();

        for (String aImage: this.images) {
//            this.view.addData(((Artifact) aItem).getCreatedDate(), aItem.getDescription(), aItem.getImages(), aItem.getVideos());
        }
    }

    private List<String> initImages() {

        List<String> res = new ArrayList<>();
        return res;
    }

    public interface IView {
        void addData(String image);
    }

}
