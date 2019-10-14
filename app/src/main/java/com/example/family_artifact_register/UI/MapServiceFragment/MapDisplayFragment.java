package com.example.family_artifact_register.UI.MapServiceFragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.PresentationLayer.Util.Pair;
import com.example.family_artifact_register.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getVideoThumbNail;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link MapDisplayFragment.OnFragmentInteractionListener} interface to handle interaction events.
 * Use the {@link MapDisplayFragment#newInstance} factory method to create an instance of this
 * fragment.
 */
public class MapDisplayFragment extends BasePlacesFragment implements OnMapReadyCallback {
    /**
     * class tag
     */
    public static final String TAG = MapDisplayFragment.class.getSimpleName();
    static final String LOCATIONS = "locations";
    static final String STATIC = "static";
    // Stores the map object to be operated
    GoogleMap mMap;
    // Stores the locations to be displayed on screen
    private List<MapLocation> locations = new ArrayList<>();
    private boolean isStatic = false;
    // MapView the current fragment is operating on
    private MapView mapView;

    // TODO to be implemented in the future for a correct way for interaction
    private OnFragmentInteractionListener mListener;

    /**
     * Required empty public constructor
     */
    public MapDisplayFragment() { }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters. The map is by default interactive.
     * @param locations The locations to be displayed on the google map
     *
     * @return A new instance of fragment MapDisplayFragment.
     */
    public static MapDisplayFragment newInstance(List<MapLocation> locations) {
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
    public static MapDisplayFragment newInstance(List<MapLocation> locations, boolean staticMap) {
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
        return MapDisplayFragment.newInstance(new ArrayList<>(), false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.locations = (List<MapLocation>) this.getArguments().get(LOCATIONS);

            Object argStatic = this.getArguments().get(STATIC);
            if (argStatic == null) {
                this.isStatic = false;
            } else {
                this.isStatic = (Boolean) argStatic;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map_display, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        // TODO Make it possbile to use lite map view, google map
        if (isStatic) {
            GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
        }
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    public void setDisplayLocations(List<MapLocation> locations) {
        this.locations = locations;
        for (MapLocation mapLocation :locations) {
            Log.i(TAG, mapLocation.toString());
        }
        displayLocations();
    }

    public void setDisplayArtifactItems(List<Pair<ArtifactItemWrapper, MapLocation>> artifactItems) {
        // Only display with marker if map is not null and there are locations stored
        if (mMap != null && artifactItems.size() != 0) {
            mMap.clear();
            List<Marker> markers = new ArrayList<>();
            for (Pair<ArtifactItemWrapper, MapLocation> pair : artifactItems) {
                ArtifactItemWrapper artifactItemWrapper = pair.getFst();
                MapLocation storeLocation = pair.getSnd();

                // Log.d(getFragmentTag(), "store location = " + storeLocation.toString());

                MarkerOptions opt = new MarkerOptions()
                        .position(new LatLng(storeLocation.getLatitude(),
                                storeLocation.getLongitude()))
                        .title(artifactItemWrapper.getUploadDateTime())
                        .snippet(artifactItemWrapper.getDescription());

                if (artifactItemWrapper.getMediaType() == TYPE_IMAGE) {
                    try {
                        Uri uri = Uri.parse(artifactItemWrapper.getLocalMediaDataUrls().get(0));

                        if (uri.getScheme() == null) {
                            uri = Uri.parse("file:/" + uri.toString());
                            Log.d(getFragmentTag(), "uri = " + uri);
                        }

                        Bitmap mBitmap = MediaStore.Images.Media.getBitmap(
                                getContext().getContentResolver(),
                                uri
                        );
                        opt.icon(BitmapDescriptorFactory.fromBitmap(mBitmap));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (artifactItemWrapper.getMediaType() == TYPE_VIDEO) {
                    opt.icon(BitmapDescriptorFactory.fromBitmap(
                            getVideoThumbNail(artifactItemWrapper.getLocalMediaDataUrls()
                                                                    .get(0))
                        )
                    );
                } else {
                    Log.e(getFragmentTag(), "unknown media Type !!!");
                }

                markers.add(mMap.addMarker(opt));
            }
            CameraUpdate cu = MarkerZoomStrategyFactory
                    .getMarkerZoomStrategyFactory()
                    .getMarkerZoomStrategy(markers.size())
                    .makeCameraUpdate(markers);
            mMap.animateCamera(cu);
        }
    }

    public List<MapLocation> getLocations() {
        return locations;
    }

    /**
     * Display the locations currently held in this fragment
     */
    private void displayLocations() {
        // Only display with marker if map is not null and there are locations stored
        if (mMap != null && locations != null && locations.size() != 0) {
            mMap.clear();
            List<Marker> markers = new ArrayList<>();
            for (MapLocation mapLocation : this.locations) {
                // TODO can build map (with icon) here
                markers.add(mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(mapLocation.getLatitude(),
                                mapLocation.getLongitude()))
                        .title(mapLocation.getName())
                        .snippet(mapLocation.getAddress())));
            }
            CameraUpdate cu = MarkerZoomStrategyFactory
                    .getMarkerZoomStrategyFactory()
                    .getMarkerZoomStrategy(markers.size())
                    .makeCameraUpdate(markers);
            mMap.animateCamera(cu);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        displayLocations();
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
        // TODO update this fragment listen to set locations in MapDisplayActivity
        void onFragmentInteraction(Uri uri);
    }
}
