package com.unimelb.family_artifact_register.UI.Artifact.ViewArtifact.Util;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tmall.ultraviewpager.UltraViewPager;
import com.unimelb.family_artifact_register.R;

/**
 * view holder for recycler view for artifact item
 * Deprecated because no longer use ViewMediaFragment
 * Not deleted because open-close principle
 */
@Deprecated
public class MyArtifactsRecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView time;

    TextView description;

    FrameLayout frame;

    UltraViewPager ultraViewPager;

    public MyArtifactsRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        time = itemView.findViewById(R.id.item_my_artifact_time);
        description = itemView.findViewById(R.id.item_my_artifact_description);
        frame = itemView.findViewById(R.id.item_my_artifact_media);
        ultraViewPager = itemView.findViewById(R.id.ultra_viewpager);
    }

    public void clearFrame() {
        frame.removeAllViews();
    }
}
