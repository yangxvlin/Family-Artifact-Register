package com.unimelb.family_artifact_register.PresentationLayer.HubPresenter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * @author Haichao Song 854035,
 * @time 2019-10-08 14:23:23
 * @description this class is only used to provide additional param to instantiate view model object
 */
public class HubViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    public HubViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HubViewModel(application);
    }
}
