package com.example.family_artifact_register.UI.Social;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

import java.util.ArrayList;

public class FriendListAdapter extends RecyclerView.Adapter {

    private ArrayList<String> dataSet;

    public FriendListAdapter(ArrayList<String> dataSet) { this.dataSet = dataSet; }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
        return new FriendListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((FriendListViewHolder) holder).textView.setText(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
