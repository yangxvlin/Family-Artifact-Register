package com.example.family_artifact_register.FoundationLayer.EventModel;

public interface EventListener {
    void notifyEventsChange();

    void attendEvent(String eventId);

    void cancelEvent(String eventId);
}
