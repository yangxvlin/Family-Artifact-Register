package com.example.family_artifact_register.UI.MapServiceFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;
import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.PresentationLayer.MapPresenter.MapViewModel;
import com.example.family_artifact_register.PresentationLayer.MapPresenter.MapViewModelFactory;
import com.example.family_artifact_register.PresentationLayer.MapPresenter.TimelineMapWrapper;
import com.example.family_artifact_register.PresentationLayer.Util.Pair;
import com.example.family_artifact_register.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AllArtifactMapFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = AllArtifactMapFragment.class.getSimpleName();

    private MapDisplayFragment mdFragment = MapDisplayFragment.newInstance();

    private MapViewModel viewModel;

    private AppCompatSpinner chooseTimeline;

    // TODO get this from DB
//    private List<TimelineMapWrapper> timelineMapWrapperList = new ArrayList<>();

    private List<String> timelineTitles;

    public static final String ALL_TIMELINE = "";

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

//        viewModel.getLocations().observe(this, new Observer<List<MapLocation>>() {
//            @Override
//            public void onChanged(List<MapLocation> mapLocations) {
//                Log.d(TAG, "location data come back, setting to map fragment");
//                mdFragment.setDisplayLocations(mapLocations);
//            }
//        });

        viewModel.getMapWrapper().observe(this, new Observer<List<TimelineMapWrapper>>() {
            @Override
            public void onChanged(List<TimelineMapWrapper> timelineMapWrappers) {
                Log.d(TAG, "size of data from DB: " + timelineMapWrappers.size());
//                for(TimelineMapWrapper wrapper: timelineMapWrappers) {
//                    Log.d(TAG, "size of wrapper list: " +
//                            wrapper.getArtifactItemWrapperList().size() + "\n"
//                            + "stored location list size: " + wrapper.getStoreLocationList().size());
//                }
                chooseTimeline = view.findViewById(R.id.fragment_map_choose_timeline_to_display_spinner);
                chooseTimeline.setPrompt(getString(R.string.choose_timeline_prompt));
                timelineTitles = timelineMapWrappers.stream()
                        .map(TimelineMapWrapper::getArtifactTimeline)
                        .map(ArtifactTimeline::getTitle)
                        .collect(Collectors.toCollection(ArrayList::new));
                timelineTitles.add(0, ALL_TIMELINE); // add an empty "" for all timeline
//        timelineTitles.add("123");
//        timelineTitles.add("456");
//        timelineTitles.add("789");

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                        R.layout.timeline_selection_spinner_item, timelineTitles);
                // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

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
    public static AllArtifactMapFragment newInstance() {
        return new AllArtifactMapFragment();
    }
}
