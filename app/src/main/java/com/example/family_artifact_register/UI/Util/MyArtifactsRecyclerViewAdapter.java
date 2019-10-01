package com.example.family_artifact_register.UI.Util;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.ArtifactManager.ViewMediaFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.family_artifact_register.UI.Util.Constant.MEDIA_LIST;
import static com.example.family_artifact_register.UI.Util.Constant.MEDIA_TYPE;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-21 15:57:25
 * @description
 */
public class MyArtifactsRecyclerViewAdapter extends RecyclerView.Adapter<MyArtifactsRecyclerViewHolder> {
    private List<String> times;

    private List<String> descriptions;

    private List<List<Uri>> mediaLists;

    private List<Integer> mediaTypes;

    private FragmentManager fm;

    private Context context;

    public MyArtifactsRecyclerViewAdapter(Context context, FragmentManager fm) {
        this.times = new ArrayList<>();
        this.descriptions = new ArrayList<>();
        this.mediaTypes = new ArrayList<>();
        this.mediaLists = new ArrayList<>();
        this.context = context;
        this.fm = fm;
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

        Bundle bundle=new Bundle();
        bundle.putInt(MEDIA_TYPE, mediaTypes.get(position));
        bundle.putSerializable(MEDIA_LIST, (Serializable) mediaLists.get(position));
        holder.mediaFragment = ViewMediaFragment.newInstance();
        holder.mediaFragment.setArguments(bundle);
        fm.beginTransaction().add(holder.frame, holder.mediaFragment).commit();
    }

    @Override
    public int getItemCount() { return times.size(); }

    // *************************************** getter & setters ***********************************
    public void addData(String time, String description, List<Uri> mediaList, int mediaType) {
        times.add(time);
        descriptions.add(description);
        mediaTypes.add(mediaType);
        mediaLists.add(mediaList);
        notifyDataSetChanged();
    }
}
