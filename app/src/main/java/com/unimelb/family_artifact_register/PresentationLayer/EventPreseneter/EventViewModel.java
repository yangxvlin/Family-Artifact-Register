package com.unimelb.family_artifact_register.PresentationLayer.EventPreseneter;

import com.unimelb.family_artifact_register.FoundationLayer.EventModel.Event;
import com.unimelb.family_artifact_register.FoundationLayer.EventModel.EventListener;
import com.unimelb.family_artifact_register.FoundationLayer.EventModel.EventManager;
import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * event view model to collect data from DB and process it to UI singleton pattern to provide a
 * global access observer pattern to observer on view model
 */
public class EventViewModel {
    /**
     * class tag
     */
    public static final String TAG = EventViewModel.class.getSimpleName();
    /**
     * singleton view model
     */
    private static EventViewModel eventViewModel;
    /**
     * list of user attending events
     */
    private List<String> attendEvent;
    /**
     * fragments
     */
    private List<EventListener> fragments;

    private EventViewModel() {
        attendEvent = new ArrayList<>();
        fragments = new ArrayList<>();
    }

    public static EventViewModel getInstance() {
        if (eventViewModel == null) {
            eventViewModel = new EventViewModel();
            return eventViewModel;
        }
        return eventViewModel;
    }

    /**
     * @return list of recommended events for user
     */
    public List<Event> getRecommendedEvent() {
        return EventManager.getInstance().getEventByUid(UserInfoManager.getInstance().getCurrentUid())
                .stream()
                .filter(x -> !attendEvent.contains(x.getId()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * @return list of user attending events
     */
    public List<Event> getAttendedEvent() {
        return EventManager.getInstance().getEventByUid(UserInfoManager.getInstance().getCurrentUid())
                .stream()
                .filter(x -> attendEvent.contains(x.getId()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * @param eventId user attend an event
     */
    public void addAttendEvent(String eventId) {
        attendEvent.add(eventId);
        fragments.forEach(EventListener::notifyEventsChange);
    }

    /**
     * @param eventId user cancel an event
     */
    public void cancelAttendEvent(String eventId) {
        attendEvent.remove(eventId);
        fragments.forEach(EventListener::notifyEventsChange);
    }

    /**
     * @param fragment subject
     */
    public void addObserver(EventListener fragment) {
        fragments.add(fragment);
    }
}
