package com.unimelb.family_artifact_register.UI.Util;

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
 * @description
 */
public class MyArtifactsVideosRecyclerViewAdapter extends RecyclerView.Adapter<MyArtifactsVideosViewHolder> {
    private List<Uri> videos;

    public MyArtifactsVideosRecyclerViewAdapter() {
        videos = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyArtifactsVideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_artifact_image, parent, false);
        return new MyArtifactsVideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyArtifactsVideosViewHolder holder, int position) {
        holder.video.setVideoURI(videos.get(position));
        holder.video.setOnClickListener(view -> {
            System.out.println("#" + position + " clicked!!!!");
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void addData(Uri video) {
        this.videos.add(video);
        notifyDataSetChanged();
    }
}
