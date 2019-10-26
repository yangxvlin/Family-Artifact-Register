package com.unimelb.family_artifact_register.UI.Artifact.ArtifactDetail.Util;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.R;

/**
 * @author Haichao Song 854035,
 * @time 2019-10-3 12:32:28
 * @description view holder for artifact fragment.
 * Deprecated because now detail page is an activity.
 */
@Deprecated
public class DetailImageViewHolder extends RecyclerView.ViewHolder {

    TextView mDescTv, mUserTv, mPublisher;
    ImageView mAvatarIv;
    public FrameLayout postImage;

    public DetailImageViewHolder(View itemView) {
        super(itemView);
        this.mAvatarIv = itemView.findViewById(R.id.avatarIv);
        this.mUserTv = itemView.findViewById(R.id.user);
        this.postImage = itemView.findViewById(R.id.post_image);
        this.mPublisher = itemView.findViewById(R.id.publisher);
        this.mDescTv = itemView.findViewById(R.id.desc);
    }

    public void clearFrame() {
        postImage.removeAllViews();
//        mapHappened.removeAllViews();
    }

}
