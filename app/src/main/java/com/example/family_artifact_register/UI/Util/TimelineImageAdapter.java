package com.example.family_artifact_register.UI.Util;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

import java.util.List;

public class TimelineImageAdapter extends RecyclerView.Adapter<TimelineImageAdapter.TimelineImageViewHolder> {

    public static final String TAG = TimelineImageAdapter.class.getSimpleName();

    public class TimelineImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public TimelineImageViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.timeline_item_image);
        }
    }

    private List<String> dataSet;

    public TimelineImageAdapter(List<String> dataSet) {
        Log.d(TAG, "new recyclerview adapter");
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public TimelineImageAdapter.TimelineImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"creating each item view");
        View view = View.inflate(parent.getContext(), R.layout.timeline_item_image, null);
        return new TimelineImageAdapter.TimelineImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineImageAdapter.TimelineImageViewHolder holder, int position) {
        String url = dataSet.get(position);
        Log.d(TAG, "setting uri to image view, url: " + url);
        holder.image.setImageURI(Uri.parse(url));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}