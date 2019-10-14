package com.example.family_artifact_register.UI.Util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.FoundationLayer.EventModel.Event;
import com.example.family_artifact_register.FoundationLayer.EventModel.EventListener;
import com.example.family_artifact_register.R;
import com.google.android.material.button.MaterialButton;

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
        Event event = eventList.get(position);

        holder.address.setText(event.getAddress());
        holder.description.setText(event.getDescription());
        holder.name.setText(event.getName());
        holder.imageView.setImageResource(event.getImage());
        if (isAttend) {
            holder.button.setText(R.string.attend);
            holder.button.setOnClickListener(view -> {
                eventListener.attendEvent(event.getId());
            });
        } else {
            holder.button.setText(R.string.button_cancel);
            holder.button.setOnClickListener(view -> {
                eventListener.cancelEvent(event.getId());
            });
        }

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        TextView name;

        TextView description;

        TextView address;

        MaterialButton button;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_event_image);
            name = itemView.findViewById(R.id.item_event_name);
            description = itemView.findViewById(R.id.item_event_description);
            address = itemView.findViewById(R.id.item_event_address);
            button = itemView.findViewById(R.id.item_event_button);
        }
    }
}
