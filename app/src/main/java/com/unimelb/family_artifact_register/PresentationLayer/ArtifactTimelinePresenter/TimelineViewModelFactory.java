package com.unimelb.family_artifact_register.PresentationLayer.ArtifactTimelinePresenter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TimelineViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private String param;

    public TimelineViewModelFactory(Application application, String param) {
        this.application = application;
        this.param = param;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TimelineViewModel(application, param);
    }
}
