package com.unimelb.family_artifact_register.UI.Artifact.ArtifactTimeline.Util;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.PresentationLayer.Util.ArtifactItemWrapper;

/**
 * helper class for preparing recyclerView
 */
public class TimelineRecyclerViewHelper {

    /**
     * class tag
     */
    public static final String TAG = TimelineRecyclerViewHelper.class.getSimpleName();

    /**
     * get a recyclerView that is fully setup
     * @param wrapper data to be put into the recyclerView
     * @param context the context
     * @return a fully setup recyclerView
     */
    public static View getRecyclerView(ArtifactItemWrapper wrapper, Context context) {

        RecyclerView recyclerView = new RecyclerView(context);

        LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);

        TimelineImageAdapter adapter = new TimelineImageAdapter(wrapper);
        recyclerView.setAdapter(adapter);

        return recyclerView;
    }
}
