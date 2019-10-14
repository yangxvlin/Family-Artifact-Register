package com.example.family_artifact_register.PresentationLayer.EventPreseneter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.family_artifact_register.FoundationLayer.EventModel.Event;
import com.example.family_artifact_register.FoundationLayer.EventModel.EventManager;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecommandedEventViewModel extends AndroidViewModel {
    public static final String TAG = RecommandedEventViewModel.class.getSimpleName();

    private List<Event> events ;

    private List<String> attendEvent;

    public RecommandedEventViewModel(@NonNull Application application) {
        super(application);
        attendEvent = new ArrayList<>();
    }

    public List<Event> getRecommandedEvent() {
        return EventManager.getInstance().getEventByUid(UserInfoManager.getInstance().getCurrentUid(), EventManager.CHINESE)
                .stream()
                .filter(x -> !attendEvent.contains(x.getId()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Event> getAttendedEvent() {
        return EventManager.getInstance().getEventByUid(UserInfoManager.getInstance().getCurrentUid(), EventManager.CHINESE)
                .stream()
                .filter(x -> attendEvent.contains(x.getId()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void addAttendEvent(String eventId) {
        attendEvent.add(eventId);
    }
}
