package com.unimelb.family_artifact_register.PresentationLayer.ArtifactPresenter.ArtifactCommentPresenter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * @author Haichao Song 854035,
 * @time 2019-10-14 17:34:22
 * @description view model factory to create comment view model in activity
 */
public class CommentViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private String param;

    public CommentViewModelFactory(Application application, String param) {
        this.application = application;
        this.param = param;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CommentViewModel(application, param);
    }
}
