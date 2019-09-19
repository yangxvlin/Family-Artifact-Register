package com.example.family_artifact_register.UI.ArtifactHub;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

import org.jetbrains.annotations.NotNull;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView mImeaView, mAvatar;
    TextView mTitle, mDes, mUsername;
    ItemClickListener itemClickListener;

    MyHolder(@NotNull View itemView) {
        super(itemView);
        this.mImeaView = itemView.findViewById(R.id.imageView);
        this.mTitle = itemView.findViewById(R.id.titleTv);
        this.mDes = itemView.findViewById(R.id.DescriptionTv);
        this.mAvatar = itemView.findViewById(R.id.avatar);
        this.mUsername = itemView.findViewById(R.id.userTv);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.OnItemClickListener(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic) {
        this.itemClickListener = ic;
    }
}
