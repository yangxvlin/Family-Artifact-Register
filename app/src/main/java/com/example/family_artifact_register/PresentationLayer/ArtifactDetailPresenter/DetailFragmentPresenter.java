package com.example.family_artifact_register.PresentationLayer.ArtifactDetailPresenter;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.Artifact;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;

import java.util.ArrayList;
import java.util.List;

public class DetailFragmentPresenter {

    private IView view;
    private String Pid;

    public DetailFragmentPresenter(IView view, String Pid) {
        this.view = view;
        this.Pid = Pid;
    }

    public interface IView {
        void addData(String Pid);
    }

}
