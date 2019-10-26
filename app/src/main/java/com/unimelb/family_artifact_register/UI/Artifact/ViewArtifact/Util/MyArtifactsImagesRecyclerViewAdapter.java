package com.unimelb.family_artifact_register.UI.Artifact.ViewArtifact.Util;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-21 19:03:05
 * adapter for images recycler view
 * Deprecated because use view pager for images
 * not deleted by the open-close principle
 */
@Deprecated
public class MyArtifactsImagesRecyclerViewAdapter extends RecyclerView.Adapter<MyArtifactsImagesViewHolder> {
    private List<Uri> images;

    public MyArtifactsImagesRecyclerViewAdapter() {
        images = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyArtifactsImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_artifact_image, parent, false);
        return new MyArtifactsImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyArtifactsImagesViewHolder holder, int position) {
        holder.image.setImageURI(images.get(position));
        holder.image.setOnClickListener(view -> {
            System.out.println("#" + position + " clicked!!!!");
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void addData(Uri image) {
        this.images.add(image);
        notifyDataSetChanged();
    }
}
