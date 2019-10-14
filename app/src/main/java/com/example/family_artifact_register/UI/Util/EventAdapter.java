package com.example.family_artifact_register.UI.Util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.FoundationLayer.EventModel.Event;
import com.example.family_artifact_register.FoundationLayer.EventModel.EventListener;
import com.example.family_artifact_register.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Event> eventList;

    private boolean isAttend;

    private EventListener eventListener;

    public EventAdapter(List<Event> eventList, boolean isAttend, EventListener eventListener) {
        this.eventList = eventList;
        this.isAttend = isAttend;
        this.eventListener = eventListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
