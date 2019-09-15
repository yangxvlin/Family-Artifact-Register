package com.example.family_artifact_register;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class MyHolder extends RecyclerView.ViewHolder {

    ImageView mImeaView, mAvatar;
    TextView mTitle, mDes, mUsername;

    public MyHolder(@NotNull View itemView) {
        super(itemView);
        this.mImeaView = itemView.findViewById(R.id.imageView);
        this.mTitle = itemView.findViewById(R.id.titleTv);
        this.mDes = itemView.findViewById(R.id.DescriptionTv);
        this.mAvatar = itemView.findViewById(R.id.avatar);
        this.mUsername = itemView.findViewById(R.id.userTv);
    }

}
