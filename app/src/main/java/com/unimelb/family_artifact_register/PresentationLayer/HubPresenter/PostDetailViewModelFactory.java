package com.unimelb.family_artifact_register.PresentationLayer.HubPresenter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * @author Haichao Song 854035,
 * @time 2019-09-24 22:45:55
 * @description view model factory use to create post view model
 * Deprecated because it is for post activity and it has been substituted
 */
@Deprecated
public class PostDetailViewModelFactory implements ViewModelProvider.Factory{

    private Application application;
    private String param;

    public PostDetailViewModelFactory(Application application, String param) {
        this.application = application;
        this.param = param;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PostDetailViewModel(application, param);
    }

}
