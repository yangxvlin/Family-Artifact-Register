package com.example.family_artifact_register.UI.Util;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

import java.util.ArrayList;
import java.util.List;

/**
 * a recycler view adapter for displaying multiple images with customized image size;
 */
public class ImagesRecyclerViewAdapter extends RecyclerView.Adapter<ImagesRecyclerViewHolder> {

    private int imageWidth;

    private int imageHeight;

    private List<Uri> images;

    public ImagesRecyclerViewAdapter(int height, int width) {
        images = new ArrayList<>();
        this.imageHeight = height;
        this.imageWidth = width;
    }

    @NonNull
    @Override
    public ImagesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext())
                                            .inflate(R.layout.item_my_artifact_image,
                                                    parent,
                                                    false);
        return new ImagesRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesRecyclerViewHolder holder, int position) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                getImageWidth(),
                getImageHeight()
        );
        holder.imageView.setImageURI(images.get(position));
        holder.imageView.setLayoutParams(layoutParams);
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

    public int getImageWidth() { return imageWidth; }

    public int getImageHeight() { return imageHeight; }
}
