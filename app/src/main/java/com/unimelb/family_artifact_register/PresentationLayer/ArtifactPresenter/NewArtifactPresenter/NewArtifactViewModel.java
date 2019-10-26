package com.unimelb.family_artifact_register.PresentationLayer.ArtifactPresenter.NewArtifactPresenter;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;
import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocationManager;
import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;

import java.util.ArrayList;
import java.util.List;

import static com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener.NewTimelineListener.EXISTING_ARTIFACT_TIMELINE;
import static com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener.NewTimelineListener.NEW_ARTIFACT_TIMELINE;
import static com.unimelb.family_artifact_register.Util.TimeToString.getCurrentTimeFormattedString;

public class NewArtifactViewModel extends AndroidViewModel {

    private UserInfoManager userInfoManager = UserInfoManager.getInstance();
    private ArtifactManager artifactManager = ArtifactManager.getInstance();

    public NewArtifactViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<ArtifactTimeline>> getTimeline() {
        // TODO get current uid has a bug, may need to find another way to retrieve timeline data
        // get all the timelines current user has
        System.out.println("live data asking: " + userInfoManager.getCurrentUid());
        return artifactManager.getArtifactTimelineByUid(userInfoManager.getCurrentUid());
    }

    public void upload(MapLocation uploadLocation,
                       MapLocation happenedLocation,
                       MapLocation storedLocation,
                       List<Uri> mediaData,
                       int mediaType,
                       String description,
                       String happenedTime,
                       int timelineStrategy,
                       String timelineTitle,
                       ArtifactTimeline selectedArtifactTimeline) {
        MapLocationManager mlm = MapLocationManager.getInstance();
        mlm.storeMapLocation(uploadLocation);
        String uploadLocationId = uploadLocation.getMapLocationId();

        mlm.storeMapLocation(happenedLocation);
        String happenedLocationId = happenedLocation.getMapLocationId();

        mlm.storeMapLocation(storedLocation);
        String storedLocationId = storedLocation.getMapLocationId();

        // Log.i(TAG, "upload location id: " + uploadLocationId);
        // Log.i(TAG, "happened location id: " + happenedLocationId);
        // Log.i(TAG, "stored location id" + storedLocationId);

//        String happenedTimeString = calendarToFormattedString(this.happenedTime);
        String currentTimeString = getCurrentTimeFormattedString();

        ArtifactManager am = ArtifactManager.getInstance();

        // convert uri to String
        List<String> mediaDataString = new ArrayList<>();
        for (Uri uri : mediaData) {
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

        ArtifactTimeline timeline = null;
        // add new timeline to DB
        if (timelineStrategy == NEW_ARTIFACT_TIMELINE) {
            timeline = new ArtifactTimeline(null,
                    UserInfoManager.getInstance().getCurrentUid(),
                    currentTimeString, currentTimeString,
                    new ArrayList<>(),
                    timelineTitle
            );
            // Log.d(TAG, newItem.getPostId() + "\n"+ timeline.getPostId());
            // Log.d(TAG, Arrays.toString(timeline.getArtifactItemPostIds().toArray()));
            UserInfoManager.getInstance().addArtifactTimelineId(timeline.getPostId());
//            am.addArtifact(timeline);
        } else if (timelineStrategy == EXISTING_ARTIFACT_TIMELINE) {
            timeline = selectedArtifactTimeline;
        } else {
            // Log.e(TAG, "unknown timeline strategy !!!");
        }

        // associate timeline with item
        am.associateArtifactItemAndArtifactTimeline(newItem, timeline);
    }
}
