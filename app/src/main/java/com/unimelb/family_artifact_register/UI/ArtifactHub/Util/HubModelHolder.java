package com.unimelb.family_artifact_register.UI.ArtifactHub.Util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author Haichao Song 854035,
 * @time 2019-9-18 13:54:32
 * @description Hold recycler view and implement click listener to cards in recycler.
 */
@Deprecated
public class HubModelHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView mImeaView, mAvatar;
    TextView  mDes, mUsername, mPublisher;
    ItemClickListener itemClickListener;

    HubModelHolder(@NotNull View itemView) {
        super(itemView);
        this.mImeaView = itemView.findViewById(R.id.post_image);
        this.mDes = itemView.findViewById(R.id.description);
        this.mAvatar = itemView.findViewById(R.id.avatar);
        this.mUsername = itemView.findViewById(R.id.username);
        this.mPublisher = itemView.findViewById(R.id.publisher);

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
