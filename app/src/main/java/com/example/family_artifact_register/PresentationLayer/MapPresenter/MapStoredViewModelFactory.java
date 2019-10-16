package com.example.family_artifact_register.PresentationLayer.MapPresenter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MapStoredViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public MapStoredViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MapStoredViewModel(application);
    }
}
