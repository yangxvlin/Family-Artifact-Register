package com.example.family_artifact_register.UI.MapServiceFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.R;

public class MapFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = MapFragment.class.getSimpleName();

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    public static MapFragment newInstance() { return new MapFragment(); }

    @Override
    public String getFragmentTag() { return TAG; }
}
