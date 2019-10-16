package com.example.family_artifact_register.UI.Util;

import android.content.Context;
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
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Event> eventList;

    private boolean isAttend;

    private EventListener eventListener;

    private Context context;

    public EventAdapter(List<Event> eventList, boolean isAttend, EventListener eventListener, Context context) {
        this.eventList = eventList;
        this.isAttend = isAttend;
        this.eventListener = eventListener;
        this.context = context;
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
        final NormalDialog dialog = new NormalDialog(context);
        dialog.style(NormalDialog.STYLE_TWO)
                .titleTextSize(23)//
                .showAnim(new BounceTopEnter())//
                .dismissAnim(new SlideBottomExit())//
                .dividerColor(context.getColor(R.color.primaryColor))
                .titleTextColor(context.getColor(R.color.primaryDarkColor))
                .btnText(context.getString(R.string.button_cancel), context.getString(R.string.button_confirm));

        if (isAttend) {
            holder.button.setText(R.string.attend);
            holder.button.setOnClickListener(view -> {
                dialog.content(context.getString(R.string.attend_dialog_content))//
                        .title(context.getString(R.string.tips))
                        .show();

                dialog.setOnBtnClickL(
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                dialog.dismiss();
                            }
                        },
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                eventListener.attendEvent(event.getId());
                                dialog.dismiss();
                            }
                        });
            });
        } else {
            holder.button.setText(R.string.button_cancel);
            holder.button.setOnClickListener(view -> {
                dialog.content(context.getString(R.string.cancel_dialog_content))//
                        .title(context.getString(R.string.tips))
                        .show();

                dialog.setOnBtnClickL(
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                dialog.dismiss();
                            }
                        },
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                eventListener.cancelEvent(event.getId());
                                dialog.dismiss();
                            }
                        });


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
