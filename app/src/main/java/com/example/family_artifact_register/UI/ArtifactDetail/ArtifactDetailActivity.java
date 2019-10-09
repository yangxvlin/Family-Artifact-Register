package com.example.family_artifact_register.UI.ArtifactDetail;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.PresentationLayer.HubPresenter.PostDetailViewModel;
import com.example.family_artifact_register.PresentationLayer.HubPresenter.PostDetailViewModelFactory;
import com.example.family_artifact_register.R;


/**
 * @author Haichao Song 854035,
 * @time 2019-9-19 11:54:23
 * @description activity for artifact detail view.
 */
public class ArtifactDetailActivity extends AppCompatActivity {

    public static final String TAG = ArtifactDetailActivity.class.getSimpleName();

    private PostDetailViewModel viewModel;

    TextView mTitleTv, mDescTv, mUserTv;
    ImageView mImageIv, mAvatarIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artifact_detail);

        // force the system not to display action bar title
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTitleTv = findViewById(R.id.publisher);
        mDescTv = findViewById(R.id.desc);
        mUserTv = findViewById(R.id.user);
        mImageIv = findViewById(R.id.imageIv);
        mAvatarIv = findViewById(R.id.avatarIv);

        // Use intent to send information to artifact detail activity
        Intent intent = getIntent();
        String selectedPid = intent.getStringExtra("selectedPid");

        viewModel = ViewModelProviders.of(this, new PostDetailViewModelFactory(
                getApplication(), selectedPid)).get(PostDetailViewModel.class);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(this.getDrawable(R.drawable.gradient_background));

        Observer<ArtifactItem> postObserver = new Observer<ArtifactItem>() {
            @Override
            public void onChanged(ArtifactItem artifactItem) {
                Log.i(TAG, "some changes happened");

                // Set artifact information the same as activity hub
                mTitleTv.setText(artifactItem.getUid());
                mDescTv.setText(artifactItem.getDescription());
                mUserTv.setText(artifactItem.getUid());
            }
        };

        viewModel.getPost().observe(this, postObserver);

//        String mTitle = intent.getStringExtra("iTitle");
//        String mDesc = intent.getStringExtra("iDesc");
//        String mUser = intent.getStringExtra("iUser");
//
//        byte[] mImageBytes = getIntent().getByteArrayExtra("iImage");
//        byte[] mAvatarBytes = getIntent().getByteArrayExtra("iImage");
//        Bitmap imageBitmap = BitmapFactory.decodeByteArray(mAvatarBytes, 0, mImageBytes.length);
//        Bitmap avatarBitmap = BitmapFactory.decodeByteArray(mAvatarBytes, 0, mAvatarBytes.length);
//
//        // Set action bar title to be artifact title
//        getMyActionBar().setTitle(mTitle);
//
//        // Set artifact information the same as activity hub
//        mTitleTv.setText(mTitle);
//        mDescTv.setText(mDesc);
//        mUserTv.setText(mUser);
//        mImageIv.setImageBitmap(imageBitmap);
//        mAvatarIv.setImageBitmap(avatarBitmap);
    }

//    @Override
//    protected int getLayoutResource() {
//        return R.layout.activity_artifact_detail;
//    }
}
