package com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * this class is only used to provide additional param to instantiate view model object
 */
public class ContactRequestViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public ContactRequestViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ContactRequestViewModel(application);
    }
}
