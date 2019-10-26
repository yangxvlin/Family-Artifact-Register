package com.unimelb.family_artifact_register.UI.ArtifactComment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactCommentPresenter.CommentViewModel;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactCommentPresenter.CommentViewModelFactory;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactCommentPresenter.CommentWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter.UserInfoWrapper;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.ArtifactDetail.ArtifactDetailActivity;

import java.util.List;

/**
 * @author Haichao Song 854035,
 * @time 2019-10-13 14:33:40
 * @description activity for editing and showing comments to user.
 */
public class ArtifactCommentActivity extends AppCompatActivity {

    public static final String TAG = ArtifactDetailActivity.class.getSimpleName();

    private RecyclerView recyclerView;

    private CommentAdapter commentAdapter;

    private CommentViewModel viewModel;

    private String PostID;

    private ImageView avatar;

    private TextView comment, post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artifact_comment);
        Intent intent = getIntent();
        PostID = intent.getStringExtra("artifactItemPostId");

        avatar = findViewById(R.id.avatar);
        comment = findViewById(R.id.comment);
        post = findViewById(R.id.post);
        recyclerView = findViewById(R.id.comment_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(this);
        recyclerView.setAdapter(commentAdapter);

        viewModel = ViewModelProviders.of(this, new CommentViewModelFactory(getApplication()
                , PostID)).get(CommentViewModel.class);

//        viewModel.getArtifactItem(PostID).observe(owner, new Observer<ArtifactItem>() {
//            @Override
//            public void onChanged(ArtifactItem artifactItem) {
//                Log.d(TAG, "Some changes happen");
//            }
//        });

        /**
         * get current user information and its avatar
         */
        viewModel.getCurrentUserInfo().observe(this, new Observer<UserInfoWrapper>() {
            @Override
            public void onChanged(UserInfoWrapper wrapper) {
                String url = wrapper.getPhotoUrl();
                if(url != null) {
                    avatar.setImageURI(Uri.parse(url));
                }
            }
        });

        /**
         * set action bar
         */
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setBackgroundDrawable(this.getDrawable(R.drawable.gradient_background));
        }

        /**
         * get comments from backend database by adapter and view model
         */
        viewModel.getComments().observe(this, new Observer<List<CommentWrapper>>() {
            @Override
            public void onChanged(List<CommentWrapper> commentWrappers) {
                commentAdapter.setData(commentWrappers);
            }
        });


        /**
         * post user's comment if it is not null when user click post button
         */
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comment.getText().toString().equals("")) {
                    Toast.makeText(ArtifactCommentActivity.this,
                            "Don't send empty comments", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.addComment(comment.getText().toString());
                    comment.setText("");
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // https://stackoverflow.com/a/30059647
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
