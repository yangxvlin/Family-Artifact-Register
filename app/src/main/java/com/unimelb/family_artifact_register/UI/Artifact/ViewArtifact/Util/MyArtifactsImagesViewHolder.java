package com.unimelb.family_artifact_register.UI.Artifact.ViewArtifact.Util;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.R;

/**
 * view holder for images recycler view
 * Deprecated because use view pager for images
 * not deleted by the open-close principle
 */
@Deprecated
public class MyArtifactsImagesViewHolder extends RecyclerView.ViewHolder {
    ImageView image;

    public MyArtifactsImagesViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.item_image);
    }
}
