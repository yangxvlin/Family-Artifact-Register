package com.unimelb.family_artifact_register.UI.Social.Friend.Util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.R;

import java.util.ArrayList;

/**
 * This was the original Adapter class for the recyclerView in
 * {@link com.unimelb.family_artifact_register.UI.Social.Friend.FriendActivity}.
 * Since {@link com.unimelb.family_artifact_register.UI.Social.Friend.FriendActivity} has been
 * deprecated, this class is now deprecated as well.
 */
@Deprecated
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
