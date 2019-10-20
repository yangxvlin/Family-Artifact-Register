package com.unimelb.family_artifact_register.UI.Post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    public Context mContext;
    public List<Post> mPost;

    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, null);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Post post = mPost.get(position);

        Glide.with(mContext).load(post.getPostimage()).into(holder.mPostImage);

        if (post.getDescription().equals("")){
            holder.mDescrption.setVisibility(View.GONE);
        } else {
            holder.mDescrption.setVisibility(View.VISIBLE);
            holder.mDescrption.setText(post.getDescription());
        }

        publisherInfo(holder.mAvatar, holder.mUsername, holder.mPublisher, post.getPublisher());

    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView mAvatar, mPostImage, mLike, mComment, mSave;
        public TextView mUsername, mLikes, mPublisher, mDescrption, mTimeline;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);

            mAvatar = itemView.findViewById(R.id.avatar);
            mPostImage = itemView.findViewById(R.id.post_image);
            mComment = itemView.findViewById(R.id.comment);
            mSave = itemView.findViewById(R.id.view_detail);
            mUsername = itemView.findViewById(R.id.username);
            mLike = itemView.findViewById(R.id.like);
            mLikes = itemView.findViewById(R.id.likes);
            mPublisher = itemView.findViewById(R.id.publisher);
            mDescrption = itemView.findViewById(R.id.description);
            mTimeline = itemView.findViewById(R.id.timeline);

        }
    }

    private void publisherInfo(ImageView avatar, TextView username, TextView publisher, String userid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Glide.with(mContext).load(user.getAvatar()).into(avatar);
                username.setText(user.getUsername());
                publisher.setText(user.getUsername());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
