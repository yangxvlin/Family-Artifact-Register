package com.unimelb.family_artifact_register.UI.Artifact.ArtifactHub.Util;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.R;
import com.google.android.material.button.MaterialButton;

public class HubModelViewHolder extends RecyclerView.ViewHolder {

            public TextView username, time, description, likes;
            public ImageView avatar, like, comment;
            public MaterialButton viewDetail, timeline;

            public FrameLayout postImage;

            public HubModelViewHolder(View itemView) {
                super(itemView);
                this.username = itemView.findViewById(R.id.username);
                this.time = itemView.findViewById(R.id.publisher);
                this.description = itemView.findViewById(R.id.description);
                this.avatar = itemView.findViewById(R.id.avatar);
                this.postImage = itemView.findViewById(R.id.post_image);
                this.timeline = itemView.findViewById(R.id.view_timeline);
                this.like = itemView.findViewById(R.id.like);
                this.likes = itemView.findViewById(R.id.likes);
                this.like = itemView.findViewById(R.id.like);
                this.comment = itemView.findViewById(R.id.comment);
                this.viewDetail = itemView.findViewById(R.id.view_detail);
            }

            public void clearFrame() { postImage.removeAllViews(); }

        }
