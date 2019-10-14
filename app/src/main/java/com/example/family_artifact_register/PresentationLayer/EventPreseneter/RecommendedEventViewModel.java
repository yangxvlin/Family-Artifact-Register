package com.example.family_artifact_register.PresentationLayer.EventPreseneter;

import com.example.family_artifact_register.FoundationLayer.EventModel.Event;
import com.example.family_artifact_register.FoundationLayer.EventModel.EventListener;
import com.example.family_artifact_register.FoundationLayer.EventModel.EventManager;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecommendedEventViewModel {
    public static final String TAG = RecommendedEventViewModel.class.getSimpleName();

    private List<String> attendEvent;

    private List<EventListener> fragments;

    private static RecommendedEventViewModel recommendedEventViewModel;

    private RecommendedEventViewModel() {
        attendEvent = new ArrayList<>();
        fragments = new ArrayList<>();
    }

    public static RecommendedEventViewModel getInstance() {
        if (recommendedEventViewModel == null) {
            return new RecommendedEventViewModel();
        }
        return recommendedEventViewModel;
    }

    public List<Event> getRecommandedEvent() {
        return EventManager.getInstance().getEventByUid(UserInfoManager.getInstance().getCurrentUid())
                .stream()
                .filter(x -> !attendEvent.contains(x.getId()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Event> getAttendedEvent() {
        return EventManager.getInstance().getEventByUid(UserInfoManager.getInstance().getCurrentUid())
                .stream()
                .filter(x -> attendEvent.contains(x.getId()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void addAttendEvent(String eventId) {
        attendEvent.add(eventId);
        fragments.forEach(EventListener::notifyEventsChange);
    }

    public void cancelAttendEvent(String eventId) {
        attendEvent.remove(eventId);
        fragments.forEach(EventListener::notifyEventsChange);
    }

    public void addObserver(EventListener fragment) {
        fragments.add(fragment);
    }
}
