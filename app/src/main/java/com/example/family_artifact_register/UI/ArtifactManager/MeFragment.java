package com.example.family_artifact_register.UI.ArtifactManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.family_artifact_register.R;

public class MeFragment extends Fragment {
    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("me !!!!!!!!!!!!!");
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    public static MeFragment newInstance() { return new MeFragment(); }
}
