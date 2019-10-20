package com.unimelb.family_artifact_register.UI.MapServiceFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.InfoWindowManager;
import com.appolica.interactiveinfowindow.customview.TouchInterceptFrameLayout;
import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.unimelb.family_artifact_register.IFragment;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.Util.Pair;
import com.unimelb.family_artifact_register.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static com.unimelb.family_artifact_register.UI.MapServiceFragment.MarkerHelper.getCreateAt;
import static com.unimelb.family_artifact_register.UI.MapServiceFragment.MarkerHelper.getSnippet;
import static com.unimelb.family_artifact_register.UI.MapServiceFragment.MarkerHelper.setUpMarker;

public class MapCustomizeWindowFragment extends Fragment implements
        IFragment,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {
    /**
     * class tag
     */
    public static final String TAG = MapCustomizeWindowFragment.class.getSimpleName();

    private InfoWindowManager infoWindowManager;

    private InfoWindow infoWindow;

    GoogleMap mMap;

    private MapView mapView;

    public static final int IMAGE_DEFAULT_WIDTH = 200;
    public static final int IMAGE_DEFAULT_HEIGHT = 200;

    /**
     * Required empty public constructor
     */
    public MapCustomizeWindowFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_map_customize_window, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.fragment_map_customize_window_map_view);
        mapView.onCreate(savedInstanceState);
        final TouchInterceptFrameLayout mapViewContainer =
                view.findViewById(R.id.fragment_map_customize_window_map_view_container);

        mapView.getMapAsync(this);

        infoWindowManager = new InfoWindowManager(getFragmentManager());
        infoWindowManager.onParentViewCreated(mapViewContainer, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        infoWindowManager.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        infoWindowManager.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        infoWindowManager.onMapReady(googleMap);
        googleMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        final InfoWindow.MarkerSpecification markerSpec =
                new InfoWindow.MarkerSpecification(10, 10);

        infoWindow = new InfoWindow(marker, markerSpec, MyInfoWindow.newInstance(marker));

        if (infoWindow != null) {
            infoWindowManager.toggle(infoWindow, true);
        }

        return true;
    }

    public void setDisplayArtifactItems(List<Pair<ArtifactItemWrapper, MapLocation>> artifactItems) {
        // Only display with marker if map is not null and there are locations stored
        if (mMap != null && artifactItems.size() != 0) {
            mMap.clear();
            List<Marker> markers = new ArrayList<>();
            for (Pair<ArtifactItemWrapper, MapLocation> pair : artifactItems) {
                MarkerOptions opt = setUpMarker(pair,
                        getContext(),
                        IMAGE_DEFAULT_WIDTH, IMAGE_DEFAULT_HEIGHT,
                        getCreateAt(pair, getContext()),
                        getSnippet(pair, getContext()));

                markers.add(mMap.addMarker(opt));
            }
            CameraUpdate cu = MarkerZoomStrategyFactory
                    .getMarkerZoomStrategyFactory()
                    .getMarkerZoomStrategy(markers.size())
                    .makeCameraUpdate(markers);
            mMap.animateCamera(cu);
        }
    }

    /**
     *
     */
    public static MapCustomizeWindowFragment newInstance() {
        return new MapCustomizeWindowFragment();
    }
}
