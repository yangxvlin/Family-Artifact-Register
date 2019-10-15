package com.example.family_artifact_register.UI.ArtifactComment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.Artifact;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactComment;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.PresentationLayer.ArtifactCommentPresenter.CommentViewModel;
import com.example.family_artifact_register.PresentationLayer.ArtifactCommentPresenter.CommentViewModelFactory;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.ArtifactDetail.ArtifactDetailActivity;

import java.util.ArrayList;

public class ArtifactCommentActivity extends AppCompatActivity {

    public static final String TAG = ArtifactDetailActivity.class.getSimpleName();

    private RecyclerView recyclerView;

    private CommentAdapter commentAdapter;

    private CommentViewModel viewModel;

    private ArtifactItem artifactItem;

    private String PostID;

    private LifecycleOwner owner;

    private ImageView avatar;

    private TextView comment, post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artifact_comment);
        owner = this;
        Intent intent = getIntent();
        PostID = intent.getStringExtra("artifactItemPostId");

        commentAdapter = new CommentAdapter(this);
        recyclerView.setAdapter(commentAdapter);

        avatar = findViewById(R.id.avatar);
        comment = findViewById(R.id.comment);
        post = findViewById(R.id.post);

        viewModel = ViewModelProviders.of(this, new CommentViewModelFactory(getApplication()
                , PostID)).get(CommentViewModel.class);

        viewModel.getArtifactItem(PostID).observe(owner, new Observer<ArtifactItem>() {
            @Override
            public void onChanged(ArtifactItem artifactItem) {
                Log.d(TAG, "Some changes happen");
            }
        });
    }



}
