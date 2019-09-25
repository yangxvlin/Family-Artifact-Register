package com.example.family_artifact_register.UI.MapServiceFragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.family_artifact_register.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

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
     * parameters. The map is by default interactive.
     * @param locations The locations to be displayed on the google map
     *
     * @return A new instance of fragment MapDisplayFragment.
     */
    public static MapDisplayFragment newInstance(List<MyLocation> locations) {
        return MapDisplayFragment.newInstance(locations, false);
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters. The points to be displayed is by default empty
     * @param staticMap If the map is static (limited interaction)
     *
     * @return A new instance of fragment MapDisplayFragment.
     */
    public static MapDisplayFragment newInstance(boolean staticMap) {
        return MapDisplayFragment.newInstance(new ArrayList<>(), staticMap);
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     * @param locations The locations to be displayed on the google map
     * @param staticMap If the map displayed should be static (limited interaction)
     *
     * @return A new instance of fragment MapDisplayFragment.
     */
    public static MapDisplayFragment newInstance(List<MyLocation> locations, boolean staticMap) {
        MapDisplayFragment fragment = new MapDisplayFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(STATIC, staticMap);
        bundle.putSerializable(LOCATIONS, (Serializable) locations);
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
        List<MyLocation> locations = new ArrayList<>();
        bundle.putSerializable(LOCATIONS, (Serializable) locations);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map_search_display, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager fm = this.getChildFragmentManager();
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment =
                (AutocompleteSupportFragment) fm.findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,
                Place.Field.LAT_LNG, Place.Field.ADDRESS));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                LatLng latLng = place.getLatLng();
                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the snippet for the marker.
                // This will be displayed on taping the marker
                markerOptions.snippet(latLng.latitude + " : " + latLng.longitude);

                // add user's search text to marker
                // by XuLin Yang
                markerOptions.title(place.getName());

                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                currentMarker = mMap.addMarker(markerOptions);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
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

            // Setting the snippet for the marker.
            // This will be displayed on taping the marker
            markerOptions.snippet(latLng.latitude + " : " + latLng.longitude);

            // TODO get the place name by latLng
//            markerOptions.title();

            // Clears the previously touched position
            mMap.clear();

            // Animating to the touched position
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            // Placing a marker on the touched position
            currentMarker = mMap.addMarker(markerOptions);
        });
    }

    public MyLocation getSelectedLocation() {
        MyLocation location = null;
        if (currentMarker != null) {
            location = new MyLocation();
            location.setLongitude(currentMarker.getPosition().longitude);
            location.setLatitude(currentMarker.getPosition().latitude);
        }
        return location;
    }
}
