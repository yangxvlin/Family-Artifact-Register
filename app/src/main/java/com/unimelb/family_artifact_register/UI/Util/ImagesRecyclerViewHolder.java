package com.unimelb.family_artifact_register.UI.Util;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.R;

/**
 * required android recycler view's view holder
 */
public class ImagesRecyclerViewHolder extends RecyclerView.ViewHolder {

    /**
     * image view in view holder
     */
    ImageView imageView;

    /**
     * @param itemView itemView
     */
    public ImagesRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.item_image);
    }
}
