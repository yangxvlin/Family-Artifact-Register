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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewArtifactStoredLocationFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = NewArtifactStoredLocationFragment.class.getSimpleName();

    private MapDisplayFragment mapDisplaySearchFragment;

    public NewArtifactStoredLocationFragment() {
        // required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_artifact_stored_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.artifact_where_stored_title);

        // search location fragment
        this.mapDisplaySearchFragment = MapSearchDisplayFragment.newInstance();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_new_artifact_stored_location_main_view, mapDisplaySearchFragment)
                .commit();

        // to next fragment
        FloatingActionButton confirm = view.findViewById(R.id.fragment_new_artifact_stored_location_floating_button_confirm);
    }

    public static NewArtifactStoredLocationFragment newInstance() { return new NewArtifactStoredLocationFragment(); }
}
