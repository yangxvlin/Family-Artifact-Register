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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.PresentationLayer.ArtifactDetailPresenter.DetailViewModel;
import com.example.family_artifact_register.PresentationLayer.ArtifactDetailPresenter.DetailViewModelFactory;
import com.example.family_artifact_register.PresentationLayer.HubPresenter.PostDetailViewModel;
import com.example.family_artifact_register.PresentationLayer.HubPresenter.PostDetailViewModelFactory;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.Util.MediaProcessHelper;


/**
 * @author Haichao Song 854035,
 * @time 2019-9-19 11:54:23
 * @description activity for artifact detail view.
 */
public class ArtifactDetailActivity extends AppCompatActivity {

    public static final String TAG = ArtifactDetailActivity.class.getSimpleName();

    private DetailViewModel viewModel;

    TextView mTitleTv, mDescTv, mUserTv;
    ImageView mAvatarIv;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);

        // force the system not to display action bar title
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTitleTv = findViewById(R.id.publisher);
        mDescTv = findViewById(R.id.desc);
        mUserTv = findViewById(R.id.user);
//        mImageIv = findViewById(R.id.imageIv);
        mAvatarIv = findViewById(R.id.avatarIv);
        recyclerView = findViewById(R.id.detail_recycler_view);

        // Use intent to send information to artifact detail activity
        Intent intent = getIntent();
        String artifactItemPostId = intent.getStringExtra("artifactItemPostId");

        viewModel = ViewModelProviders.of(this, new DetailViewModelFactory(getApplication())).get(DetailViewModel.class);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(this.getDrawable(R.drawable.gradient_background));


        GridLayoutManager manager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(manager);


        DetailImageAdapter detailImageAdapter = new DetailImageAdapter(this);

        recyclerView.setAdapter(detailImageAdapter);

        Observer<ArtifactItem> postObserver = new Observer<ArtifactItem>() {
            @Override
            public void onChanged(ArtifactItem artifactItem) {
                Log.i(TAG, "some changes happened");

                // Set artifact information the same as activity hub
                mTitleTv.setText(artifactItem.getPostId());
                mDescTv.setText(artifactItem.getDescription());
                mUserTv.setText(artifactItem.getUid());

                if (manager.getSpanSizeLookup() != null) {
                    if (artifactItem.getMediaDataUrls().size() <= 1) {
                        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {
                                return 3;
                            }
                        });
                    } else {
                        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {
                                return 1;
                            }
                        });
                    }
                }

                artifactItem.getMediaDataUrls();
                switch ( artifactItem.getMediaType()) {
                    case (MediaProcessHelper.TYPE_IMAGE): {
                        detailImageAdapter.setData(artifactItem.getMediaDataUrls());
                        break;
                    }
                    case (MediaProcessHelper.TYPE_VIDEO): {
                        break;
                    }
                }

            }
        };

        viewModel.getArtifactItem(artifactItemPostId).observe(this, postObserver);




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
//        return R.layout.fragment_detail;
//    }
}
