package com.example.family_artifact_register.FoundationLayer.MapModel;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;

import java.util.List;

/**
 * Dataclass for Location on a map
 */
public class MapLocation extends Location {
    private String placeId;
    private String name;
    private String description;
    private String address;
    private List<String> imageUrls;

    public MapLocation() {
        super("");
    }

    public MapLocation(String provider, double latitude, double longitude, String placeId,
                       String name, String description, String address, List<String> imageUrls) {
        super(provider);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.placeId = placeId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.imageUrls = imageUrls;
    }

    public static MapLocation newInstance(LatLng latLng) {
        return new MapLocation(
                "", latLng.latitude, latLng.longitude, null, null,
                null, null, null

        );
    }

    public static MapLocation newInstance(Place place) {
        MapLocation mapLocation = new MapLocation();
        if (place.getLatLng() != null) {
            mapLocation.setLatitude(place.getLatLng().latitude);
            mapLocation.setLongitude(place.getLatLng().longitude);
        }
        if (place.getId() != null) {
            mapLocation.setPlaceId(place.getId());
        }
        if (place.getName() != null) {
            mapLocation.setName(place.getName());
        }

        if (place.getAddress() != null) {
            mapLocation.setAddress(place.getAddress());
        }
        return mapLocation;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
