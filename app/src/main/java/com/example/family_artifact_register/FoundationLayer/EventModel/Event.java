package com.example.family_artifact_register.FoundationLayer.EventModel;

public class Event {

    private String address;

    private int image;

    private String time;

    private String name;

    private String description;

    private Event(String address, int image, String time, String name, String description) {
        this.address = address;
        this.image = image;
        this.time = time;
        this.name = name;
        this.description = description;
    }

    public static Event newInstance(String address, int image, String time, String name, String description) {
        return new Event(address, image, time, name, description);
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
}
