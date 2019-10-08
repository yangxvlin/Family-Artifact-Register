package com.example.family_artifact_register.UI.ArtifactManager;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;
import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocationManager;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.Util.DescriptionListener;
import com.example.family_artifact_register.UI.Util.HappenedLocationListener;
import com.example.family_artifact_register.UI.Util.HappenedTimeListener;
import com.example.family_artifact_register.UI.Util.MediaListener;
import com.example.family_artifact_register.UI.Util.MediaProcessHelper;
import com.example.family_artifact_register.UI.Util.NewTimelineListener;
import com.example.family_artifact_register.UI.Util.OnBackPressedListener;
import com.example.family_artifact_register.UI.Util.StartUploadListener;
import com.example.family_artifact_register.UI.Util.StoredLocationListener;
import com.example.family_artifact_register.UI.Util.UploadLocationListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.family_artifact_register.UI.Util.TimeToString.getCurrentTimeFormattedString;

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

    private FragmentManager fm;

    private Fragment mediaFragment = NewArtifactMediaFragment.newInstance();

    private List<Uri> mediaData;

    private String description;

    private int mediaType;

    private String happenedTime;

    private MapLocation happenedLocation;

    private MapLocation storedLocation;

    private int timelineStrategy;

    // TODO might be changed later
    private String timelineTitle;

    private MapLocation uploadLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_artifact_2);
        mediaData = new ArrayList<>();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        // initialize first fragment
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.activity_new_artifact_main_view, mediaFragment).commit();
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
        MapLocationManager mlm =  MapLocationManager.getInstance();
        mlm.storeMapLocation(uploadLocation);
        String uploadLocationId = uploadLocation.getMapLocationId();

        mlm.storeMapLocation(happenedLocation);
        String happenedLocationId = happenedLocation.getMapLocationId();

        mlm.storeMapLocation(storedLocation);
        String storedLocationId = storedLocation.getMapLocationId();

        Log.i(TAG, "upload location id: " + uploadLocationId);
        Log.i(TAG, "happened location id: " + happenedLocationId);
        Log.i(TAG, "stored location id" + storedLocationId);

//        String happenedTimeString = calendarToFormattedString(this.happenedTime);
        String currentTimeString = getCurrentTimeFormattedString();

        ArtifactManager am = ArtifactManager.getInstance();

        // convert uri to String
        List<String> mediaDataString = new ArrayList<>();
        for (Uri uri: mediaData) {
//            File externalStoredFile = MediaProcessHelper.copyFileToExternal(uri);
             mediaDataString.add(uri.toString());
//            Uri externalStoredUri = Uri.fromFile(externalStoredFile);
//            mediaDataString.add(externalStoredUri.toString());
//            Log.d(TAG, "media file Uri toString(): "+ externalStoredUri.toString());
//            Log.d(TAG, "media file Uri getPath() : "+ externalStoredUri.getPath());
        }

        ArtifactItem newItem = ArtifactItem.newInstance(currentTimeString,
                                                        currentTimeString,
                                                        mediaType,
                                                        mediaDataString,
                                                        description,
                                                        uploadLocationId,
                                                        happenedLocationId,
                                                        storedLocationId,
                                                        happenedTime,
                                                        null);
        am.addArtifact(newItem);

        ArtifactTimeline timeline;
        if (timelineStrategy == NEW_ARTIFACT_TIMELINE) {
            timeline = new ArtifactTimeline(null, UserInfoManager.getInstance().getCurrentUid(), currentTimeString, currentTimeString, new ArrayList<>(), timelineTitle);
            timeline.addArtifactPostId(newItem.getPostId());
            Log.d(TAG, newItem.getPostId() + "\n"+ timeline.getPostId());
            Log.d(TAG, Arrays.toString(timeline.getArtifactItemPostIds().toArray()));

            am.addArtifact(timeline);
        } else {
            // get timeline from remote DB
            timeline = null;
        }
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
                try {
                    mediaData.add(MediaProcessHelper.compressUriImage(this, data, false));
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
}
