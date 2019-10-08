package com.example.family_artifact_register.UI.ArtifactHub;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

public class HubModelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView username, publisher, description;
            public ImageView avatar;

            public FrameLayout postImage;

            public String itemId;

            public HubModelViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                this.username = itemView.findViewById(R.id.username);
                this.publisher = itemView.findViewById(R.id.publisher);
                this.description = itemView.findViewById(R.id.description);
                this.avatar = itemView.findViewById(R.id.avatar);
                this.postImage = itemView.findViewById(R.id.post_image);
            }

            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ArtifactDetailActivity.class);
                i.putExtra("selectedPid", itemId);
                startActivity(i);
            }
        }
