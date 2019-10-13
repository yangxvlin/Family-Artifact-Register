package com.example.family_artifact_register.UI.ArtifactHub;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

public class HubModelViewHolder extends RecyclerView.ViewHolder {

<<<<<<< HEAD
            public TextView username, time, description, timeline;
            public ImageView avatar, like;
=======
            public TextView username, time, description, likes;
            public ImageView avatar, like, comment, viewDetail;
>>>>>>> 0ad253198d45badb52970cfaf64609cb5fbb6802

            public FrameLayout postImage;

            public HubModelViewHolder(View itemView) {
                super(itemView);
                this.username = itemView.findViewById(R.id.username);
                this.time = itemView.findViewById(R.id.publisher);
                this.description = itemView.findViewById(R.id.description);
                this.avatar = itemView.findViewById(R.id.avatar);
                this.postImage = itemView.findViewById(R.id.post_image);
<<<<<<< HEAD
                this.timeline = itemView.findViewById(R.id.timeline);
                this.like = itemView.findViewById(R.id.like);
=======
                this.likes = itemView.findViewById(R.id.likes);
                this.like = itemView.findViewById(R.id.like);
                this.comment = itemView.findViewById(R.id.comment);
                this.viewDetail = itemView.findViewById(R.id.view_detail);
>>>>>>> 0ad253198d45badb52970cfaf64609cb5fbb6802
            }

            public void clearFrame() { postImage.removeAllViews(); }

        }
