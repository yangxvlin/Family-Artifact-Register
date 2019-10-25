package com.unimelb.family_artifact_register.FoundationLayer.EventModel;

/**
 * event data class
 */
public class Event {

    /**
     * event address
     */
    private String address;

    /**
     * event image
     */
    private int image;

    /**
     * event time
     */
    private String time;

    /**
     * event name
     */
    private String name;

    /**
     * event description
     */
    private String description;

    /**
     * event id
     */
    private String id;

    private Event(String id, String address, int image, String time, String name, String description) {
        this.id = id;
        this.address = address;
        this.image = image;
        this.time = time;
        this.name = name;
        this.description = description;
    }

    public static Event newInstance(String id, String address, int image, String time, String name, String description) {
        return new Event(id, address, image, time, name, description);
    }

    public String getAddress() {
        return address;
    }

    public int getImage() {
        return image;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }
}
