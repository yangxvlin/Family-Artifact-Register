package com.unimelb.family_artifact_register.FoundationLayer.EventModel;

public class Event {

    private String address;

    private int image;

    private String time;

    private String name;

    private String description;

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
