package com.unimelb.family_artifact_register.PresentationLayer.ArtifactDetailPresenter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * @author Haichao Song 854035,
 * @time 2019-10-06 22:58:09
 * @description view model factory to create detail view model in activity
 */
public class DetailViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    public DetailViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailViewModel(application);
    }
}


