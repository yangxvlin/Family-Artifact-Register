package com.example.family_artifact_register.UI.Util;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

import java.util.ArrayList;
import java.util.List;

public class ImagesRecyclerViewAdapter extends RecyclerView.Adapter<ImagesRecyclerViewHolder> {

    private List<Uri> images;

    public ImagesRecyclerViewAdapter() {
        images = new ArrayList<>();
    }

    @NonNull
    @Override
    public ImagesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_artifact_image, parent, false);
        return new ImagesRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesRecyclerViewHolder holder, int position) {
        holder.imageView.setImageURI(images.get(position));
        holder.imageView.setOnClickListener(view -> {
            System.out.println("#"+position+" clicked!!!!");
        });
    }

    @Override
    public int getItemCount() { return images.size(); }

    public void addData(Uri image) {
        this.images.add(image);
        notifyDataSetChanged();
    }
}
