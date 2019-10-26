package com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.R;

/**
 * Deprecated because no need to preview artifacts before upload
 * not deleted by the open-close principle
 */
@Deprecated
public class UploadArtifactViewHolder extends RecyclerView.ViewHolder {
    public ImageView img;
    public Button button;

    public UploadArtifactViewHolder(@NonNull View itemView) {
        super(itemView);
        this.img = itemView.findViewById(R.id.img);
        this.button = itemView.findViewById(R.id.delete_button);
    }
}
