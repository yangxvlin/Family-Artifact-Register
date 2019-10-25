package com.unimelb.family_artifact_register.UI.Artifact.ViewArtifact.Util;

import android.view.View;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.R;

/**
 * view holder for video recycler view
 * Deprecated because only one video is needed
 * not deleted by the open-close principle
 */
@Deprecated
public class MyArtifactsVideosViewHolder extends RecyclerView.ViewHolder {
    /**
     * video
     */
    VideoView video;

    public MyArtifactsVideosViewHolder(@NonNull View itemView) {
        super(itemView);
        video = itemView.findViewById(R.id.item_video);
    }
}
