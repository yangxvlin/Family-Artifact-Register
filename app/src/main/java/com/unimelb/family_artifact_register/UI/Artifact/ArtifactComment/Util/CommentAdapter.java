package com.unimelb.family_artifact_register.UI.Artifact.ArtifactComment.Util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.PresentationLayer.ArtifactPresenter.ArtifactCommentPresenter.CommentWrapper;
import com.unimelb.family_artifact_register.R;

import java.util.List;

/**
 * @author Haichao Song 854035,
 * @time 2019-10-13 13:34:13
 * @description adapter class for comment activity.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    /**
     * get class tag
     */
    private static final String TAG = CommentAdapter.class.getSimpleName();

    private List<CommentWrapper> artifactComments;

    private Context context;

    public CommentAdapter(Context context) {
        this.context = context;
    }

    /**
     * create comment view holder to find view in comment item
     *
     * @param parent   parent context for view holder
     * @param viewType view type to create
     * @return comment view holder
     */
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,
                parent, false);
        return new CommentViewHolder(view);
    }

    /**
     * set comment item with data get from view model
     *
     * @param holder   comment view holder constructed
     * @param position the position comment item is in an array of comments showing in comment
     *                 activity
     */
    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentWrapper artifactComment = artifactComments.get(position);
        Log.d(TAG, "onBindViewHolder: check avatar uri:" + artifactComment.
                getUserInfoWrapper().getPhotoUrl());
        holder.username.setText(artifactComment.getUserInfoWrapper().getDisplayName());
        holder.comment.setText(artifactComment.getArtifactComment().getContent());
        holder.time.setText(artifactComment.getArtifactComment().getLastUpdateDateTime());
        String url = artifactComment.getUserInfoWrapper().getPhotoUrl();
        if (url != null) {
            holder.avatar.setImageURI(Uri.parse(url));
        }
    }

    /**
     * count number of comments will be shown in comment activity
     *
     * @return number of comments if adapter get data from view model
     */
    @Override
    public int getItemCount() {
        if (artifactComments != null) {
            return artifactComments.size();
        }
        return 0;
    }

    /**
     * set comments array with new data
     *
     * @param newData an array of comments
     */
    public void setData(List<CommentWrapper> newData) {
        artifactComments = newData;
        notifyDataSetChanged();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {

        public ImageView avatar;
        public TextView username, comment, time;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            this.time = itemView.findViewById(R.id.time);
            this.username = itemView.findViewById(R.id.username);
            this.comment = itemView.findViewById(R.id.comment);
            this.avatar = itemView.findViewById(R.id.avatar);
        }

    }
}
