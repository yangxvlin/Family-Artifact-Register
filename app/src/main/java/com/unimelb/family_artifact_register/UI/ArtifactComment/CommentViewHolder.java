package com.unimelb.family_artifact_register.UI.ArtifactComment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.R;

/**
 * @author Haichao Song 854035,
 * @time 2019-10-13 13:54:47
 * @description view holder to pair variable with view in xml
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {

    ImageView avatar;
    TextView username, comment, time;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);
        this.time = itemView.findViewById(R.id.time);
        this.username = itemView.findViewById(R.id.username);
        this.comment = itemView.findViewById(R.id.comment);
        this.avatar = itemView.findViewById(R.id.avatar);
    }

}
