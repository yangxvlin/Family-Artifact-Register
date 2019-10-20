package com.example.family_artifact_register.UI.ArtifactComment;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.Artifact;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactComment;
import com.example.family_artifact_register.PresentationLayer.ArtifactCommentPresenter.CommentWrapper;
import com.example.family_artifact_register.R;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

public class CommentAdapter  extends RecyclerView.Adapter<CommentViewHolder> {

    private static final String TAG = CommentAdapter.class.getSimpleName();

    private List<CommentWrapper> artifactComments;

    private Context context;

    public CommentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentWrapper artifactComment = artifactComments.get(position);
        Log.d(TAG, "onBindViewHolder: check avatar uri:" + artifactComment.getUserInfoWrapper().getPhotoUrl());
        holder.username.setText(artifactComment.getUserInfoWrapper().getDisplayName());
        holder.comment.setText(artifactComment.getArtifactComment().getContent());
        holder.time.setText(artifactComment.getArtifactComment().getLastUpdateDateTime());
        String url = artifactComment.getUserInfoWrapper().getPhotoUrl();
        if (url != null) {
            holder.avatar.setImageURI(Uri.parse(url));
        }
    }


    @Override
    public int getItemCount() {
        if(artifactComments != null) {
            return artifactComments.size();
        }
        return 0;
    }

    public void setData(List<CommentWrapper> newData) {
        artifactComments = newData;
        notifyDataSetChanged();
    }

}
