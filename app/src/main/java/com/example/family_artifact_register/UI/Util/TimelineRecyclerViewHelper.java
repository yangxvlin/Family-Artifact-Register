package com.example.family_artifact_register.UI.Util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class TimelineRecyclerViewHelper {

    public static final String TAG = TimelineRecyclerViewHelper.class.getSimpleName();

    public static View getRecyclerView(List<String> srcs, Context context) {

        RecyclerView recyclerView = new RecyclerView(context);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.MATCH_PARENT
        );
        recyclerView.setLayoutParams(layoutParams);

        LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);

        TimelineImageAdapter adapter = new TimelineImageAdapter();
        adapter.setData(srcs);
        recyclerView.setAdapter(adapter);
        TimelineImageAdapter settedAdapter = (TimelineImageAdapter ) recyclerView.getAdapter();
        Log.d(TAG, "data in adapter: " + settedAdapter.getDataSet().toString());

        return recyclerView;
    }
}
