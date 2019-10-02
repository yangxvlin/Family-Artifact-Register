package com.example.family_artifact_register.UI.ArtifactHub;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

public class HubModelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView username, title, description;
    public ImageView avatar, postImage;

    public HubModelViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.username = itemView.findViewById(R.id.username);
        this.title = itemView.findViewById(R.id.title);
        this.description = itemView.findViewById(R.id.description);
        this.avatar = itemView.findViewById(R.id.avatar);
        this.postImage = itemView.findViewById(R.id.post_image);
    }

    @Override
    public void onClick(View view) {
        String value = (String) username.getText();
        Context context = view.getContext();
        Intent i = new Intent(context, ArtifactDetailActivity.class);
        i.putExtra("key", value);
        context.startActivity(i);
    }

}
