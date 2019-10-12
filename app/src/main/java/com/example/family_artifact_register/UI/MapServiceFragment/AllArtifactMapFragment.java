package com.example.family_artifact_register.UI.MapServiceFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.PresentationLayer.MapPresenter.MapViewModel;
import com.example.family_artifact_register.PresentationLayer.MapPresenter.MapViewModelFactory;
import com.example.family_artifact_register.R;

import java.util.List;

public class AllArtifactMapFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = AllArtifactMapFragment.class.getSimpleName();

    MapDisplayFragment mdFragment = MapDisplayFragment.newInstance();

    private MapViewModel viewModel;

    /**
     * Required empty public constructor
     */
    public AllArtifactMapFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.artifact_map);
        Log.d(TAG, "view has been created");
        FrameLayout mainView = view.findViewById(R.id.fragment_me_main_view);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_me_main_view, mdFragment, "MapDisplayFragment")
                .addToBackStack(null)
                .commit();

        viewModel = ViewModelProviders.of(this, new MapViewModelFactory(getActivity().getApplication())).get(MapViewModel.class);

        viewModel.getLocations().observe(this, new Observer<List<MapLocation>>() {
            @Override
            public void onChanged(List<MapLocation> mapLocations) {
                Log.d(TAG, "location data come back, setting to map fragment");
                mdFragment.setDisplayLocations(mapLocations);
            }
        });
    }

    /**
     *
     */
    public static AllArtifactMapFragment newInstance() {
        return new AllArtifactMapFragment();
    }
}
