package com.unimelb.family_artifact_register.FoundationLayer.EventModel;

/**
 * data base listener
 */
public interface EventListener {
    /**
     * notify data changed
     */
    void notifyEventsChange();

    /**
     * @param eventId attend event
     */
    void attendEvent(String eventId);

    /**
     * @param eventId cancel event
     */
    void cancelEvent(String eventId);
}
