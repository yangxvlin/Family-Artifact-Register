package com.example.family_artifact_register.UI.ArtifactDetail;

import android.net.Uri;
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

    public void setPostImageUri(Uri uri) {
        postImage.setImageURI(uri);
//        postImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

}
