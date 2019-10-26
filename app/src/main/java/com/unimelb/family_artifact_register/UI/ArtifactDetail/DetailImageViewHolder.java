package com.unimelb.family_artifact_register.UI.ArtifactDetail;

import android.view.View;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.R;

public class DetailImageViewHolder extends RecyclerView.ViewHolder {

    //    TextView  mDescTv, mUserTv, mPublisher;
//    ImageView mAvatarIv;
    public FrameLayout postImage;

    public DetailImageViewHolder(View itemView) {
        super(itemView);
//        this.mAvatarIv = itemView.findViewById(R.id.avatarIv);
//        this.mUserTv = itemView.findViewById(R.id.user);
        this.postImage = itemView.findViewById(R.id.post_image);
//        this.mPublisher = itemView.findViewById(R.id.publisher);
//        this.mDescTv = itemView.findViewById(R.id.desc);
    }

    public void clearFrame() {
        postImage.removeAllViews();
        // mapHappened.removeAllViews();
    }

}
