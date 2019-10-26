package com.unimelb.family_artifact_register.UI.Artifact.NewArtifact;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;
import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactPresenter.NewArtifactPresenter.NewArtifactViewModel;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactPresenter.NewArtifactPresenter.NewArtifactViewModelFactory;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener.DescriptionListener;
import com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener.HappenedLocationListener;
import com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener.HappenedTimeListener;
import com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener.MediaListener;
import com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper;
import com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener.NewTimelineListener;
import com.unimelb.family_artifact_register.UI.Util.OnBackPressedListener;
import com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener.StartUploadListener;
import com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener.StoredLocationListener;
import com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener.UploadLocationListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-21 13:33:24
 * @description activity let user to upload new artifact
 */
public class NewArtifactActivity2 extends AppCompatActivity implements MediaListener,
        DescriptionListener,
        HappenedTimeListener,
        UploadLocationListener,
        HappenedLocationListener,
        StoredLocationListener,
        NewTimelineListener,
        StartUploadListener {
    /**
     * class tag
     */
    private static final String TAG = NewArtifactActivity2.class.getSimpleName();

    /**
     * fragment manager
     */
    private FragmentManager fm;

    /**
     * media fragment to view selected images or video
     */
    private Fragment mediaFragment = NewArtifactMediaFragment.newInstance();

    /**
     * list of images or video
     */
    private List<Uri> mediaData;

    /**
     * artifact item's description
     */
    private String description;

    /**
     * artifact item's type: images or video
     */
    private int mediaType;

    /**
     * artifact item's happened time
     */
    private String happenedTime;

    /**
     * artifact item's happened location
     */
    private MapLocation happenedLocation;

    /**
     * artifact item's stored location
     */
    private MapLocation storedLocation;

    /**
     * artifact item's timeline strategy
     */
    private int timelineStrategy;

    /**
     * user's timeline from DB
     */
    private List<ArtifactTimeline> timelines = new ArrayList<>();

    /**
     * artifact item's timeline
     */
    private ArtifactTimeline selectedArtifactTimeline = null;


    /**
     * artifact item's timeline title
     */
    private String timelineTitle;

    /**
     * artifact item's upload location
     */
    private MapLocation uploadLocation;

    /**
     * view model
     */
    private NewArtifactViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_artifact_2);
        mediaData = new ArrayList<>();

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setBackgroundDrawable(this.getDrawable(R.drawable.gradient_background));
        }

        // initialize first fragment
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.activity_new_artifact_main_view, mediaFragment).commit();

        viewModel = ViewModelProviders.of(this, new NewArtifactViewModelFactory(getApplication())).get(NewArtifactViewModel.class);

        viewModel.getTimeline().observe(this, new Observer<List<ArtifactTimeline>>() {
            @Override
            public void onChanged(List<ArtifactTimeline> artifactTimelines) {
                Log.d(TAG, "data from db arrived");
                // data arrives
                timelines = artifactTimelines;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // https://stackoverflow.com/a/30059647
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.activity_new_artifact_main_view);
            if (f instanceof OnBackPressedListener) {
                ((OnBackPressedListener) f).onBackPressed();
            }

            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    // ************************************ implement interface ***********************************
    @Override
    public void uploadNewArtifact() {
        viewModel.upload(uploadLocation,
                happenedLocation,
                storedLocation,
                mediaData,
                mediaType,
                description,
                happenedTime,
                timelineStrategy,
                timelineTitle,
                selectedArtifactTimeline);
    }

    /**
     * take in data and compress it and record it in the activity
     *
     * @param data media data, can be image or video
     * @param type MediaProcessHelper's processing data Type
     */
    @Override
    public void addData(Uri data, int type) {
        switch (type) {
            case MediaProcessHelper.TYPE_IMAGE:
                mediaData.add(MediaProcessHelper.compressUriImage(this, data, false));
                break;
            case MediaProcessHelper.TYPE_VIDEO:
                mediaData.add(data);
                break;
        }
        Log.i(TAG, "added data: "+data.getPath() + " with cur size = " + mediaData.size());
    }


    @Override
    public List<Uri> getData() { return mediaData; }

    @Override
    public void clearData() { mediaData.clear(); }

    @Override
    public void setMediaType(int type) { mediaType = type; }

    @Override
    public int getMediaType() { return mediaType; }

    @Override
    public void setDescription(String description) { this.description = description; }

    @Override
    public String getDescription() { return this.description; }

    @Override
    public void clearDescription() { description = ""; }

    @Override
    public String getHappenedTime() { return this.happenedTime; }

    @Override
    public void setHappenedTime(String time) { this.happenedTime = time; }

    @Override
    public void setHappenedLocation(MapLocation happenedLocation) { this.happenedLocation = happenedLocation; }

    @Override
    public MapLocation getHappenedLocation() { return this.happenedLocation; }

    @Override
    public void setStoredLocation(MapLocation storedLocation) { this.storedLocation = storedLocation; }

    @Override
    public MapLocation getStoredLocation() { return this.storedLocation; }

    @Override
    public MapLocation getUploadLocation() { return this.uploadLocation; }

    @Override
    public void setUploadLocation(MapLocation uploadLocation) { this.uploadLocation = uploadLocation; }

    @Override
    public void setTimeline(int type, String timelineTitle) {
        this.timelineStrategy = type;
        this.timelineTitle = timelineTitle;
    }

    @Override
    public int getTimelineType() {
        return this.timelineStrategy;
    }

    @Override
    public String getTimelineTitle() {
        return this.timelineTitle;
    }

    @Override
    public List<ArtifactTimeline> getArtifactTimelines() {
        return this.timelines;
    }

    @Override
    public void setSelectedTimeline(ArtifactTimeline selectedTimeline) {
        this.selectedArtifactTimeline = selectedTimeline;
    }
}
