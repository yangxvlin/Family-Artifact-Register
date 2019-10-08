package com.example.family_artifact_register.UI.Util;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.ArtifactManager.ViewMediaFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.family_artifact_register.UI.Util.Constant.MEDIA_URL_LIST;
import static com.example.family_artifact_register.UI.Util.Constant.MEDIA_TYPE;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-21 15:57:25
 * @description
 */
public class MyArtifactsRecyclerViewAdapter extends RecyclerView.Adapter<MyArtifactsRecyclerViewHolder> {

    private List<ArtifactItem> artifactItemList;

    private FragmentManager fm;

    private Context context;

    public MyArtifactsRecyclerViewAdapter(Context context, FragmentManager fm) {
        this.artifactItemList = new ArrayList<>();
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
        holder.time.setText(artifactItemList.get(position).getLastUpdateDateTime());
        holder.description.setText(artifactItemList.get(position).getDescription());

        Bundle bundle = new Bundle();
        bundle.putInt(MEDIA_TYPE, artifactItemList.get(position).getMediaType());
        bundle.putSerializable(MEDIA_URL_LIST, (Serializable) artifactItemList.get(position).getMediaDataUrls());
        holder.mediaFragment = ViewMediaFragment.newInstance();
        holder.mediaFragment.setArguments(bundle);
        fm.beginTransaction().add(holder.frame, holder.mediaFragment).commit();
    }

    @Override
    public int getItemCount() { return artifactItemList.size(); }

    public void setData(List<ArtifactItem> newData) {
        artifactItemList = newData;
        notifyDataSetChanged();
    }

    // *************************************** getter & setters ***********************************
    public void addData(ArtifactItem artifactItem) {
        // 0 to add data at start
        this.artifactItemList.add(0, artifactItem);
        notifyDataSetChanged();
    }
}
