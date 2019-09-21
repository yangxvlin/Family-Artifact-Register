package com.example.family_artifact_register.UI.Util;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

public class MyArtifactsImagesViewHolder  extends RecyclerView.ViewHolder {
    ImageView image;

    public MyArtifactsImagesViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.item_image);
    }
}
