package com.example.family_artifact_register.UI.MapServiceFragment;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;

public final class MyLocation extends Location {
    /* Custom Field for setting bundle attributes for a Location object */
    public enum Field {
        PLACE_ID,
        NAME,
        DESCRIPTION,
        ADDRESS,
        IMAGE
    }

    // Bundled information for storing additional information
    Bundle bundle = new Bundle();

    public MyLocation() {
        super("");
    }

    public MyLocation(LatLng latLng) {
        super("");
        this.setLatitude(latLng.latitude);
        this.setLongitude(latLng.longitude);
    }

    public MyLocation(Place place) {
        super("");
        if (place.getLatLng() != null) {
            this.setLatitude(place.getLatLng().latitude);
            this.setLongitude(place.getLatLng().longitude);
        }
        if (place.getId() != null) {
            this.setPlaceId(place.getId());
        }
        if (place.getName() != null) {
            this.setName(place.getName());
        }

        if (place.getAddress() != null) {
            this.setAddress(place.getAddress());
        }
        Log.i(getClass().getSimpleName(), this.toString());
    }

    public MyLocation(String provider) {
        super(provider);
    }

    public MyLocation(Location l) {
        super(l);
    }

    public LatLng getLatLng() {
        return new LatLng(getLatitude(), getLongitude());
    }

    public void setPlaceId(String placeId) {
        this.bundle.putString(Field.PLACE_ID.toString(), placeId);
    }

    public String getPlaceId() {
        return this.bundle.getString(Field.PLACE_ID.toString(), null);
    }

    public void setName(String name) {
        this.bundle.putString(Field.NAME.toString(), name);
    }

    public String getName() {
        return this.bundle.getString(Field.NAME.toString(), null);
    }

    public void setDescription(String description) {
        this.bundle.putString(Field.DESCRIPTION.toString(), description);
    }

    public String getDescription() {
        return this.bundle.getString(Field.DESCRIPTION.toString(), null);
    }

    public void setAddress(String address) {
        this.bundle.putString(Field.ADDRESS.toString(), address);
    }

    public String getAddress() {
        return this.bundle.getString(Field.ADDRESS.toString(), null);
    }

    public void setImage(Bitmap image) {
        this.bundle.putParcelable(Field.IMAGE.toString(), image);
    }

    public String getImage() {
        return this.bundle.getString(Field.IMAGE.toString(), null);
    }

    public void setValue(String key, Parcelable value) {
        this.bundle.putParcelable(key, value);
    }

    public void getValue(String key) {
        this.bundle.getParcelable(key);
    }
}
