package com.example.family_artifact_register.UI.Util;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

import java.util.ArrayList;
import java.util.List;

public class UploadArtifactAdapter extends RecyclerView.Adapter<UploadArtifactViewHolder> {
    private List<Bitmap> images;

    public UploadArtifactAdapter() {
        this.images = new ArrayList<>();
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
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    images.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, images.size());
                }
            }
        );
    }

    @Override
    public int getItemCount() { return images.size(); }

    public void addData(Bitmap image) {
        images.add(image);
        notifyDataSetChanged();
    }

    public List<Bitmap> getImages() {return images;}
}
