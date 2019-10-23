package com.unimelb.family_artifact_register.UI.Artifact.NewArtifact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.unimelb.family_artifact_register.Util.IFragment;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.Artifact.ArtifactMap.SearchLocationMap.MapSearchDisplayFragment;
import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener.HappenedLocationListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.dmoral.toasty.Toasty;

public class NewArtifactHappenedLocationFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = NewArtifactHappenedLocationFragment.class.getSimpleName();

    private MapSearchDisplayFragment mapSearchDisplayFragment;

    public NewArtifactHappenedLocationFragment() {
        // required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_artifact_happened_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.artifact_where_happened_title);

        // search location fragment
        this.mapSearchDisplayFragment = MapSearchDisplayFragment.newInstance();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_new_artifact_happened_location_main_view, mapSearchDisplayFragment)
                .commit();

        FloatingActionButton confirm = view.findViewById(R.id.fragment_new_artifact_happened_location_floating_button_confirm);
        confirm.setOnClickListener(view1 -> {
            MapLocation selectedLocation = mapSearchDisplayFragment.getSelectedLocation();
            if (selectedLocation != null) {
                // store location in NewArtifactActivity
                ((HappenedLocationListener)getActivity()).setHappenedLocation(selectedLocation);

                // to next fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("next");
                fragmentTransaction.replace(R.id.activity_new_artifact_main_view, NewArtifactStoredLocationFragment.newInstance());
                fragmentTransaction.commit();
            } else {
                Toasty.error(getContext(), R.string.not_enough_location_warning, Toasty.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public static NewArtifactHappenedLocationFragment newInstance() { return new NewArtifactHappenedLocationFragment(); }
}
