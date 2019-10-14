package com.example.family_artifact_register.PresentationLayer.EventPreseneter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.family_artifact_register.FoundationLayer.EventModel.Event;

import java.util.List;

public class RecommandedEventViewModel extends AndroidViewModel {
    public static final String TAG = RecommandedEventViewModel.class.getSimpleName();

    private List<Event> events ;

    public RecommandedEventViewModel(@NonNull Application application) {
        super(application);
    }
}
