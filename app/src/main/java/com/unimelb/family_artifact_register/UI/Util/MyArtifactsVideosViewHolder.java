package com.unimelb.family_artifact_register.UI.Util;

import android.view.View;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.R;

public class MyArtifactsVideosViewHolder extends RecyclerView.ViewHolder {
    VideoView video;

    public MyArtifactsVideosViewHolder(@NonNull View itemView) {
        super(itemView);
        video = itemView.findViewById(R.id.item_video);
    }
}
