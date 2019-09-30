package com.example.family_artifact_register.UI.Social;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

public class FriendListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView textView;
    public ImageView imageView;

    public FriendListViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.textView = itemView.findViewById(R.id.username);
        this.imageView = itemView.findViewById(R.id.avatar);
    }

    @Override
    public void onClick(View view) {
        String value = (String) textView.getText();
        Context context = view.getContext();
        Intent i = new Intent(context, ContactDetailActivity.class);
        i.putExtra("key", value);
        context.startActivity(i);
    }
}
