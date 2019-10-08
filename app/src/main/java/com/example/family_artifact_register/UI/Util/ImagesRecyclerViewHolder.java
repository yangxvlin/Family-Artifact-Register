package com.example.family_artifact_register.UI.Util;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

public class ImagesRecyclerViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;

    public ImagesRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.item_image);
    }
}
