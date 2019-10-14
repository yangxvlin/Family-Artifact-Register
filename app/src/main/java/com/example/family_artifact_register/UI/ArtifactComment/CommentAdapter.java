package com.example.family_artifact_register.UI.ArtifactComment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactComment;
import com.example.family_artifact_register.R;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

public class CommentAdapter  extends RecyclerView.Adapter<CommentViewHolder> {

    private static final String TAG = CommentAdapter.class.getSimpleName();

    private List<ArtifactComment> artifactComments;

    private Context context;

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        ArtifactComment artifactComment = artifactComments.get(position);
    }

    @Override
    public int getItemCount() {
        if(artifactComments != null) {
            return artifactComments.size();
        }
        return 0;
    }

    public void setData(List<ArtifactComment> newData) {
        artifactComments = newData;
        notifyDataSetChanged();
    }

}
