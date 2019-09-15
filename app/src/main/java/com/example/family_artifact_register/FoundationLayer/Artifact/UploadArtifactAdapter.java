package com.example.family_artifact_register.FoundationLayer.Artifact;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

import java.util.List;

public class UploadArtifactAdapter extends RecyclerView.Adapter<UploadArtifactViewHolder> {
    private List<Bitmap> images;

    public UploadArtifactAdapter(List<Bitmap> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public UploadArtifactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.upload_artifact_list_item, parent, false);
        return new UploadArtifactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadArtifactViewHolder holder, int position) {
        holder.img.setImageBitmap(images.get(position));
        holder.button.setOnClickListener(view -> images.remove(position));
    }

    @Override
    public int getItemCount() { return images.size(); }
}
