package com.example.family_artifact_register.UI.Util;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;
import com.tmall.ultraviewpager.UltraViewPager;

public class MyArtifactsRecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView time;

    TextView description;

//    int frame;
    FrameLayout frame;

    ImageView navigateToArtifactTimeline;

    UltraViewPager ultraViewPager;

//    Fragment mediaFragment;

    public MyArtifactsRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        time = itemView.findViewById(R.id.item_my_artifact_time);
        description = itemView.findViewById(R.id.item_my_artifact_description);
        frame = itemView.findViewById(R.id.item_my_artifact_media);
        navigateToArtifactTimeline = itemView.findViewById(R.id.item_my_artifact_right_arrow);
        ultraViewPager = itemView.findViewById(R.id.ultra_viewpager);
    }

    public void clearFrame() {
        frame.removeAllViews();
    }
}
