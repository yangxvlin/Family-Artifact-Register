package com.example.family_artifact_register.FoundationLayer.MapModel;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Dataclass for Location on a map
 */
public class MapLocation extends Location implements Parcelable, Serializable,
        Comparable<MapLocation> {
    private String mapLocationId;
    private String placeId;
    private String name;
    private String description;
    private String address;
    private List<String> imageUrls;

    /**
     * Empty constructor needed by firebase
     */
    public MapLocation() {
        super("");
    }

    /**
     *
     * @param mapLocationId Id stored in database
     * @param provider provider of this location
     * @param latitude longitude of the place
     * @param longitude latitude of the place
     * @param placeId placeId of the place if searched by google place api
     * @param name name of the place
     * @param description description of the place
     * @param address address of the place
     * @param imageUrls set of iamge urls (can be street view in the future)
     */
    public MapLocation(String mapLocationId, String provider, double latitude, double longitude,
                       String placeId, String name, String description, String address,
                       List<String> imageUrls) {
        super(provider);
        this.mapLocationId = mapLocationId;
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.placeId = placeId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.imageUrls = imageUrls;
    }

    public static MapLocation newInstance(LatLng latLng) {
        return new MapLocation(null,
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

    public String getMapLocationId() {
        return mapLocationId;
    }

    void setMapLocationId(String mapLocationId) {
        this.mapLocationId = mapLocationId;
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

    public void addImageUrl(String imageUrl) {
        this.imageUrls.add(imageUrl);
    }

    public void removeImageUrl(String imageUrl) {
        this.imageUrls.remove(imageUrl);
    }

    @Override
    public String getProvider() {
        return super.getProvider();
    }

    @Override
    public double getLatitude() {
        return super.getLatitude();
    }

    @Override
    public double getLongitude() {
        return super.getLongitude();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mapLocationId);
        out.writeString(getProvider());
        out.writeDouble(getLatitude());
        out.writeDouble(getLongitude());
        out.writeString(placeId);
        out.writeString(name);
        out.writeString(description);
        out.writeString(address);
        out.writeStringList(imageUrls);
    }

    public static final Parcelable.Creator<MapLocation> CREATOR
            = new Parcelable.Creator<MapLocation>() {
        public MapLocation createFromParcel(Parcel in) {
            return new MapLocation(in);
        }

        public MapLocation[] newArray(int size) {
            return new MapLocation[size];
        }
    };

    /**
     * Constructor used for parcelable
     *
     * @param in The parcel with information
     */
    private MapLocation(Parcel in) {
        this(
                in.readString(),
                in.readString(),
                in.readDouble(),
                in.readDouble(),
                in.readString(),
                in.readString(),
                in.readString(),
                in.readString(),
                null
        );
        this.imageUrls = new ArrayList<>();
        in.readStringList(imageUrls);
    }

    @NotNull
    @Override
    public String toString() {
        return String.format(
                "MapLocation: %s, name: %s, latlng: (%f, %f), placeId: %s, description: %s," +
                        "address: %s, imageUrls: " + imageUrls.toString(),
                mapLocationId, name, getLatitude(), getLongitude(), placeId, description, address
        );
    }

    /**
     * Override Compare to to have an ordering of things (May be easier for frontend to manage)
     * @param o The other to compare to
     * @return Save order as the comparison between displayed name
     */
    @Override
    public int compareTo(MapLocation o) {
        return this.getName().compareTo(o.getName());
    }
}
