package com.example.family_artifact_register.UI.Util;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;

import java.util.List;

public class TimelineRecyclerViewHelper {

    public static final String TAG = TimelineRecyclerViewHelper.class.getSimpleName();

    public static View getRecyclerView(ArtifactItemWrapper wrapper, Context context) {

        RecyclerView recyclerView = new RecyclerView(context);

        LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);

        TimelineImageAdapter adapter = new TimelineImageAdapter(wrapper);
        recyclerView.setAdapter(adapter);

        return recyclerView;
    }
}
