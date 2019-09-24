package com.example.family_artifact_register.UI.ArtifactManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.MapServiceFragment.MapDisplayFragment;
import com.example.family_artifact_register.UI.MapServiceFragment.MapSearchDisplayFragment;

public class NewArtifactHappenedLocationFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = NewArtifactHappenedLocationFragment.class.getSimpleName();

    private MapDisplayFragment mapDisplaySearchFragment;

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
//        getActivity().setTitle(R.string.);

        // search location fragment
        this.mapDisplaySearchFragment = MapSearchDisplayFragment.newInstance();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_new_artifact_happened_location_main_view, mapDisplaySearchFragment)
                .commit();

        // to next fragment
        // TODO
    }

    public static NewArtifactHappenedLocationFragment newInstance() { return new NewArtifactHappenedLocationFragment(); }
}
