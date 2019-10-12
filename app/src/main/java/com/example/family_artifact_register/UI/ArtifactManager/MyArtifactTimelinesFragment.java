package com.example.family_artifact_register.UI.ArtifactManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;
import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.MyTimelineViewModel;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.MyTimelineViewModelFactory;
import com.example.family_artifact_register.R;

import java.util.List;

public class MyArtifactTimelinesFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = MyArtifactTimelinesFragment.class.getSimpleName();

    private MyTimelineViewModel viewModel;

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

        viewModel = ViewModelProviders.of(this, new MyTimelineViewModelFactory(getActivity().getApplication())).get(MyTimelineViewModel.class);

        viewModel.getTimelines().observe(this, new Observer<List<ArtifactTimeline>>() {
            @Override
            public void onChanged(List<ArtifactTimeline> artifactTimelines) {
                // TODO logic when data comes back from DB
            }
        });
    }

    /**
     * @return created MyArtifactTimelinesFragment
     */
    public static MyArtifactTimelinesFragment newInstance() { return new MyArtifactTimelinesFragment(); }
}
