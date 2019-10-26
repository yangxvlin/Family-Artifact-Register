package com.unimelb.family_artifact_register.UI.Social.Friend.Util;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.Social.Contact.ContactDetailActivity;

/**
 * This was the original ViewHolder class for the recyclerView in
 * {@link com.unimelb.family_artifact_register.UI.Social.Friend.FriendActivity}.
 * Since {@link com.unimelb.family_artifact_register.UI.Social.Friend.FriendActivity} has been
 * deprecated, this class is now deprecated as well.
 */
@Deprecated
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
