package com.example.family_artifact_register.UI.MapServiceFragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.compat.Place;
import com.google.common.collect.MapMaker;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link MapDisplayFragment.OnFragmentInteractionListener} interface to handle interaction events.
 * Use the {@link MapDisplayFragment#newInstance} factory method to create an instance of this
 * fragment.
 */
public class MapDisplayFragment extends Fragment implements OnMapReadyCallback, IFragment {
    private static final String PLACES = "places";

    // Stores the map object to be operated
    private GoogleMap mMap;
    // Stores the places to be displayed on screen
    private List<Place> places;
    // MapView the current fragment is operating on
    private MapView mapView;

    // TODO to be implemented in the future for a correct way for interaction
    private OnFragmentInteractionListener mListener;


    /**
     * Required empty public constructor
     */
    public MapDisplayFragment() {
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     * @param places The places to be displayed on the google map
     *
     * @return A new instance of fragment MapDisplayFragment.
     */
    public static MapDisplayFragment newInstance(List<Place> places) {
        MapDisplayFragment fragment = new MapDisplayFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PLACES, (Serializable) places);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        places = (List<Place>) bundle.get(PLACES);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map_display, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    public void setDisplayPlaces(List<Place> places) {
        this.places = places;
        displayPlaces();
    }

    public List<Place> getPlaces() {
        return places;
    }

    /**
     * Display the places currently held in this fragment
     */
    private void displayPlaces() {
        // Only display with marker if map is not null and there are places stored
        if (mMap != null && places.size() != 0) {
            mMap.clear();
            List<Marker> markers = new ArrayList<>();
            for (Place place: this.places) {
                // TODO can build map (with icon) here
                markers.add(mMap.addMarker(new MarkerOptions()
                        .position(place.getLatLng())
                        .title((String) place.getName())
                        .snippet((String) place.getAddress())));
            }
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }
            int padding = 100; // offset from edges of the map in pixels
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(cu);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        displayPlaces();
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this fragment to allow an
     * interaction in this fragment to be communicated to the activity and potentially other
     * fragments contained in that activity.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
