package com.example.family_artifact_register.UI.Util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

public class MyArtifactsRecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView time;

    TextView description;

    RecyclerView imagesRecyclerView;

    RecyclerView videosRecyclerView;

    ImageView next;

    public MyArtifactsRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        time = itemView.findViewById(R.id.item_my_artifact_time);
        description = itemView.findViewById(R.id.item_my_artifact_description);
        imagesRecyclerView = itemView.findViewById(R.id.item_my_artifact_images_recycler_view);
        videosRecyclerView = itemView.findViewById(R.id.item_my_artifact_videos_recycler_view);
        next = itemView.findViewById(R.id.item_my_artifact_right_arrow);
    }
}
