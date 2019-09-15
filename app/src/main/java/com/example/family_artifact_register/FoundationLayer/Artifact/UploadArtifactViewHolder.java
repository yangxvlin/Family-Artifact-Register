package com.example.family_artifact_register.FoundationLayer.Artifact;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

public class UploadArtifactViewHolder extends RecyclerView.ViewHolder {
    public ImageView img;
    public Button button;

    public UploadArtifactViewHolder(@NonNull View itemView) {
        super(itemView);
        this.img = itemView.findViewById(R.id.img);
        this.button = itemView.findViewById(R.id.delete_button);
    }
}
