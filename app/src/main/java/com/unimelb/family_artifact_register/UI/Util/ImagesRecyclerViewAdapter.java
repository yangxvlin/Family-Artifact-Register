package com.unimelb.family_artifact_register.UI.Util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.R;

import java.util.ArrayList;
import java.util.List;

import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.setImageOnClickOpenDialogListener;

/**
 * a recycler view adapter for displaying multiple images with customized image size;
 * required by android recycler view
 * pure fabricate with image width and length setter to reuse
 */
public class ImagesRecyclerViewAdapter extends RecyclerView.Adapter<ImagesRecyclerViewHolder> {
    /**
     * image item width
     */
    private int imageWidth;

    /**
     * image item height
     */
    private int imageHeight;

    /**
     * list of images to be displayed
     */
    private List<Uri> images;

    /**
     * context object
     */
    private Context context;

    /**
     * @param height image height
     * @param width image width
     * @param context contect object
     */
    public ImagesRecyclerViewAdapter(int height, int width, Context context) {
        images = new ArrayList<>();
        this.imageHeight = height;
        this.imageWidth = width;
        this.context = context;
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

        Uri imageUri = images.get(position);

        Bitmap bitmap = BitmapFactory.decodeFile(imageUri.getPath());
        holder.imageView.setImageBitmap(bitmap);

        holder.imageView.setLayoutParams(layoutParams);
        setImageOnClickOpenDialogListener(holder.imageView, images.get(position), context);
    }

    @Override
    public int getItemCount() { return images.size(); }

    /**
     * @param image new image
     */
    public void addData(Uri image) {
        this.images.add(image);
        notifyDataSetChanged();
    }

    /**
     * @return get image item width
     */
    public int getImageWidth() { return imageWidth; }

    /**
     * @return get image item height
     */
    public int getImageHeight() { return imageHeight; }
}
