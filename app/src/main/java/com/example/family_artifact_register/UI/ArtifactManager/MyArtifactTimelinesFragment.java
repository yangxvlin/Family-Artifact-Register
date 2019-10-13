package com.example.family_artifact_register.UI.ArtifactManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.R;

public class MyArtifactTimelinesFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = MyArtifactTimelinesFragment.class.getSimpleName();

    public MyArtifactTimelinesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "My Artifact Items Fragment created");
        return inflater.inflate(R.layout.fragment_my_artifact_timelines, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * @return created MyArtifactTimelinesFragment
     */
    public static MyArtifactTimelinesFragment newInstance() { return new MyArtifactTimelinesFragment(); }
}
