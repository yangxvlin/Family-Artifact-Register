package com.unimelb.family_artifact_register.UI.Artifact.NewArtifact;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener.NewTimelineListener;
import com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener.StartUploadListener;
import com.unimelb.family_artifact_register.Util.IFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import es.dmoral.toasty.Toasty;

import static com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener.NewTimelineListener.EXISTING_ARTIFACT_TIMELINE;
import static com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener.NewTimelineListener.NEW_ARTIFACT_TIMELINE;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-30 14:14:19
 * @description fragment to let user to choose the timeline for the new artifact
 */
public class NewArtifactChooseTimelineFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    private static final String TAG = NewArtifactChooseTimelineFragment.class.getSimpleName();

    /**
     * associate item with new timeline
     */
    private FloatingActionButton newTimelineConfirmButton;

    /**
     * associate item with existing timeline
     */
    private FloatingActionButton existingTimelineConfirmButton;

    /**
     * new timeline strategy button
     */
    private RadioButton newTimelineButton;


    /**
     * existing timeline strategy button
     */
    private RadioButton existingTimelineButton;

    /**
     * edit text for user to create new timeline with title
     */
    private EditText newTimelineTitleEditText;

    /**
     * spinner for user to choose existing timeline
     */
    private Spinner existingTimelineSpinner;

    /**
     * list of existing timeline titles
     */
    private List<String> timelineTitles;

    /**
     * user's chosen existing timeline's title
     */
    private String selectedTimelineTitle = null;

    /**
     * list of existing timeline from DB
     */
    private List<ArtifactTimeline> timelines;

    /**
     * required empty constructor
     */
    public NewArtifactChooseTimelineFragment() {
    }

    public static NewArtifactChooseTimelineFragment newInstance() {
        return new NewArtifactChooseTimelineFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_artifact_choose_timeline, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.artifact_choose_timeline);

        newTimelineButton = view.findViewById(R.id.new_artifact_new_timeline_radio_button);
        newTimelineButton.setOnClickListener(view1 -> {
            onRadioButtonClicked(view1);
        });
        existingTimelineButton = view.findViewById(R.id.new_artifact_existing_timeline_radio_button);
        existingTimelineButton.setOnClickListener(view1 -> {
            onRadioButtonClicked(view1);
        });

        newTimelineTitleEditText = view.findViewById(R.id.new_timeline_title_edit_text);

        existingTimelineSpinner = view.findViewById(R.id.existing_timeline_spinner);

        timelines = ((NewTimelineListener) this.getActivity()).getArtifactTimelines();
        timelineTitles = timelines.stream()
                .map(ArtifactTimeline::getTitle)
                .collect(Collectors.toCollection(ArrayList::new));


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                R.layout.timeline_selection_spinner_item, timelineTitles);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        // Apply the adapter to the spinner
        existingTimelineSpinner.setAdapter(adapter);

        newTimelineConfirmButton = view.findViewById(R.id.fragment_new_artifact_choose_timeline_floating_button_new_timeline_confirm);
        newTimelineConfirmButton.setOnClickListener(view1 -> {
            String newTimelineTitle = newTimelineTitleEditText.getText().toString();

            // new timeline's title has non-empty length and not previously created
            if (newTimelineTitle.isEmpty()) {
                Toasty.error(getContext(), R.string.new_artifact_new_timeline_empty_title_warning, Toasty.LENGTH_LONG)
                        .show();
            } else if (timelineTitles.contains(newTimelineTitle)) {
                Toasty.error(getContext(), R.string.new_artifact_timeline_exists_warning, Toasty.LENGTH_LONG)
                        .show();
            } else {
                // pass data to activity
                ((NewTimelineListener) getActivity()).setTimeline(NEW_ARTIFACT_TIMELINE, newTimelineTitle);
                // call NewArtifactActivity method to start upload
                ((StartUploadListener) getActivity()).uploadNewArtifact();
                // finish the activity
                getActivity().finish();
            }
        });

        existingTimelineConfirmButton = view.findViewById(R.id.fragment_new_artifact_choose_timeline_floating_button_existing_timeline_confirm);
        existingTimelineConfirmButton.setOnClickListener(view1 -> {
            String selectedTimelineTitle = existingTimelineSpinner.getSelectedItem().toString();
            ArtifactTimeline selected = null;

            for (ArtifactTimeline t : timelines) {
                if (t.getTitle().equals(selectedTimelineTitle)) {
                    selected = t;
                    break;
                }
            }

            if (selected == null) {
                Log.e(TAG, "selected timeline is null !!!");
            }

            // pass selected timeline to activity
            ((NewTimelineListener) getActivity()).setTimeline(EXISTING_ARTIFACT_TIMELINE, selectedTimelineTitle);
            ((NewTimelineListener) getActivity()).setSelectedTimeline(selected);
            // Toast.makeText(getContext(), selectedTimelineTitle, Toast.LENGTH_SHORT).show();
            // call NewArtifactActivity method to start upload
            ((StartUploadListener) getActivity()).uploadNewArtifact();
            // finish the activity
            getActivity().finish();
        });

        // initially all views except radio buttons are invisible, one of them is visible only when
        // one method (one radio button is selected)
        disableVisibility();
    }

    /**
     * disable all required UI component
     */
    public void disableVisibility() {
        newTimelineConfirmButton.setVisibility(View.GONE);
        newTimelineTitleEditText.setVisibility(View.GONE);
        existingTimelineConfirmButton.setVisibility(View.GONE);
        existingTimelineSpinner.setVisibility(View.GONE);
    }

    /**
     * update UI based on user's choice of timeline strategy
     *
     * @param view view
     */
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.new_artifact_new_timeline_radio_button:
                if (checked) {
                    newTimelineConfirmButton.setVisibility(View.VISIBLE);
                    newTimelineTitleEditText.setVisibility(View.VISIBLE);
                    existingTimelineConfirmButton.setVisibility(View.GONE);
                    existingTimelineSpinner.setVisibility(View.GONE);
                }
                break;
            case R.id.new_artifact_existing_timeline_radio_button:
                if (checked) {
                    newTimelineConfirmButton.setVisibility(View.GONE);
                    newTimelineTitleEditText.setVisibility(View.GONE);
                    existingTimelineConfirmButton.setVisibility(View.VISIBLE);
                    existingTimelineSpinner.setVisibility(View.VISIBLE);
                }
                break;
            default:
                Log.e(getFragmentTag(), "unknown radio button clicked !!!");
                break;
        }
    }
}
