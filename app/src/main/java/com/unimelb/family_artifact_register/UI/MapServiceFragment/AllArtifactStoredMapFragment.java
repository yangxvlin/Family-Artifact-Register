package com.unimelb.family_artifact_register.UI.MapServiceFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;
import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.unimelb.family_artifact_register.IFragment;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.MapPresenter.MapStoredViewModel;
import com.unimelb.family_artifact_register.PresentationLayer.MapPresenter.MapStoredViewModelFactory;
import com.unimelb.family_artifact_register.PresentationLayer.MapPresenter.TimelineMapWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.Util.Pair;
import com.unimelb.family_artifact_register.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AllArtifactStoredMapFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = AllArtifactStoredMapFragment.class.getSimpleName();

    private MapCustomizeWindowFragment mdFragment = MapCustomizeWindowFragment.newInstance();

    private MapStoredViewModel viewModel;

    private AppCompatSpinner chooseTimeline;

    // TODO get this from DB
//    private List<TimelineMapWrapper> timelineMapWrapperList = new ArrayList<>();

    private List<String> timelineTitles;

    public static final String ALL_TIMELINE = "";

    /**
     * Required empty public constructor
     */
    public AllArtifactStoredMapFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stored_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "view has been created");
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_stored_map_main_view, mdFragment, "MapDisplayFragment")
                .addToBackStack(null)
                .commit();

        viewModel = ViewModelProviders.of(this, new MapStoredViewModelFactory(getActivity().getApplication())).get(MapStoredViewModel.class);

        viewModel.getMapWrapper().observe(this, new Observer<List<TimelineMapWrapper>>() {
            @Override
            public void onChanged(List<TimelineMapWrapper> timelineMapWrappers) {
                Log.d(TAG, "size of data from DB: " + timelineMapWrappers.size());

                chooseTimeline = view.findViewById(R.id.fragment_stored_map_choose_timeline_to_display_spinner);
                chooseTimeline.setPrompt(getString(R.string.choose_timeline_prompt));
                timelineTitles = timelineMapWrappers.stream()
                        .map(TimelineMapWrapper::getArtifactTimeline)
                        .map(ArtifactTimeline::getTitle)
                        .collect(Collectors.toCollection(ArrayList::new));
                timelineTitles.add(0, ALL_TIMELINE); // add an empty "" for all timeline

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                        R.layout.timeline_selection_spinner_item, timelineTitles);

                chooseTimeline.setAdapter(adapter);

                chooseTimeline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    /**
                     * @param parent parent view which is the spinner
                     * @param view spinner item's view
                     * @param pos position of the item in the adapter
                     * @param id the line number of the item, normally same as pos
                     */
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        if (pos == 0) {
                            List<Pair<ArtifactItemWrapper, MapLocation>> allArtifactItems = new ArrayList<>();
                            for (TimelineMapWrapper timelineMapWrapper: timelineMapWrappers) {
                                allArtifactItems.addAll(timelineMapWrapper.getAllPairs());
                            }
                            Log.d(getFragmentTag(), "chosen time = " + adapter.getItem(pos));
                            Log.d(getFragmentTag(), "allArtifactItems size = " + allArtifactItems.size());
                            mdFragment.setDisplayArtifactItems(allArtifactItems);
                        } else {
                            Log.d(getFragmentTag(), "chosen time = " + adapter.getItem(pos));
                            Log.d(getFragmentTag(), "allArtifactItems size = " + timelineMapWrappers.get(pos-1).getAllPairs().size());
                            mdFragment.setDisplayArtifactItems(timelineMapWrappers.get(pos-1).getAllPairs());
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Another interface callback
                    }
                });
            }
        });
    }

    /**
     *
     */
    public static AllArtifactStoredMapFragment newInstance() {
        return new AllArtifactStoredMapFragment();
    }
}
