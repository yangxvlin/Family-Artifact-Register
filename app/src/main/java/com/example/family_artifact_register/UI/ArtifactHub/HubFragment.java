package com.example.family_artifact_register.UI.ArtifactHub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.family_artifact_register.R;

public class HubFragment extends Fragment {
    public HubFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hub, container, false);
    }

    public static HubFragment newInstance() { return new HubFragment(); }
}

