package com.unimelb.family_artifact_register.PresentationLayer.ViewArtifactPresenter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * this class is only used to provide additional param to instantiate view model object
 */
public class MeViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public MeViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MyArtifactItemsViewModel(application);
    }
}
