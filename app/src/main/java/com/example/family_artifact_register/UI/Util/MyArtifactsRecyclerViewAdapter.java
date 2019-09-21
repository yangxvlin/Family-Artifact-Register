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

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-21 15:57:25
 * @description
 */
public class MyArtifactsRecyclerViewAdapter extends RecyclerView.Adapter<MyArtifactsRecyclerViewHolder> {
    private List<String> times;

    private List<String> descriptions;

    private List<List<Uri>> imagesList;

    private List<List<Uri>> videosList;

    public MyArtifactsRecyclerViewAdapter() {
        times = new ArrayList<>();
        descriptions = new ArrayList<>();
        imagesList = new ArrayList<>();
        videosList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyArtifactsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_artifact, parent, false);
        return new MyArtifactsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyArtifactsRecyclerViewHolder holder, int position) {
        holder.time.setText(times.get(position));
        holder.description.setText(descriptions.get(position));
        holder.next.setOnClickListener(view -> {
            System.out.println("#"+ position+" arrow pressed!!!");
            ;
        });
    }

    @Override
    public int getItemCount() { return times.size(); }

    // *************************************** getter & setters ***********************************
    public void addData(String time, String description, List<Uri> images, List<Uri> videos) {
        times.add(time);
        descriptions.add(description);
        imagesList.add(images);
        videosList.add(videos);
        notifyDataSetChanged();
    }
}
