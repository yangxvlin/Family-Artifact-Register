package com.example.family_artifact_register.UI.MapServiceFragment;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.family_artifact_register.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link MapSearchDisplayFragment.OnFragmentInteractionListener} interface to handle interaction
 * events. Use the {@link MapSearchDisplayFragment#newInstance} factory method to create an instance
 * of this fragment.
 */
public class MapSearchDisplayFragment extends MapDisplayFragment {
    /**
     * class tag
     */
    public static final String TAG = MapSearchDisplayFragment.class.getSimpleName();
    private Marker currentMarker = null;

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     * @param places The places to be displayed on the google map
     *
     * @return A new instance of fragment MapDisplayFragment.
     */
    public static MapDisplayFragment newInstance(List<Place> places) {
        MapSearchDisplayFragment fragment = new MapSearchDisplayFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PLACES, (Serializable) places);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @return A new instance of fragment MapDisplayFragment.
     */
    public static MapDisplayFragment newInstance() {
        MapSearchDisplayFragment fragment = new MapSearchDisplayFragment();
        Bundle bundle = new Bundle();
        List<Place> places = new ArrayList<>();
        bundle.putSerializable(PLACES, (Serializable) places);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Instantiated Class " + TAG);
    }

    /**
     * This interface must be implemented by activities that contain this fragment to allow an
     * interaction in this fragment to be communicated to the activity and potentially other
     * fragments contained in that activity.
     * <p>
     * See the Android Training lesson <a href= "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        // Setting a click event handler for the map
        mMap.setOnMapClickListener(latLng -> {
            Log.i(TAG, "Clicked map at " + latLng.toString());
            // Creating a marker
            MarkerOptions markerOptions = new MarkerOptions();

            // Setting the position for the marker
            markerOptions.position(latLng);

            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title(latLng.latitude + " : " + latLng.longitude);

            // Clears the previously touched position
            mMap.clear();

            // Animating to the touched position
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            // Placing a marker on the touched position
            currentMarker = mMap.addMarker(markerOptions);
        });
    }

    public Location getSelectedLocation() {
        Location location = null;
        if (currentMarker != null) {
            location = new Location("");
            location.setLongitude(currentMarker.getPosition().longitude);
            location.setLatitude(currentMarker.getPosition().latitude);
        }
        return location;
    }
}
