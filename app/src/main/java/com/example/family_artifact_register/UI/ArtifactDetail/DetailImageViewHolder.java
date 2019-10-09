package com.example.family_artifact_register.UI.ArtifactDetail;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

public class DetailImageViewHolder extends RecyclerView.ViewHolder {

    public ImageView postImage;

    public DetailImageViewHolder(View itemView) {
        super(itemView);
        this.postImage = itemView.findViewById(R.id.post_image);
    }

}
