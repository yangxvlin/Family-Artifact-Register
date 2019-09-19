package com.example.family_artifact_register.UI.MapServiceFragment;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.family_artifact_register.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.compat.Place;
import com.google.gson.Gson;

import java.util.List;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link MapDisplayFragment.OnFragmentInteractionListener} interface to handle interaction events.
 * Use the {@link MapDisplayFragment#newInstance} factory method to create an instance of this
 * fragment.
 */
public class MapDisplayFragment extends Fragment implements OnMapReadyCallback {
    private static final String PLACES = "places";

    // Stores the map object to be operated
    private GoogleMap mMap;
    private List<Place> places;
    private Gson gson = new Gson();

    private OnFragmentInteractionListener mListener;

    public MapDisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @return A new instance of fragment MapDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapDisplayFragment newInstance(List<Place> places) {
        MapDisplayFragment fragment = new MapDisplayFragment();
        Bundle bundle = new Bundle();
        String jsonList = fragment.gson.toJson(places);
        bundle.putString(PLACES, jsonList);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_display, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.display_map);
        mapFragment.getMapAsync(this);
        return view;
    }

    public void displayLocations(List<LatLng> locations) {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
