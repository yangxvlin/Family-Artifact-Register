package com.example.family_artifact_register.UI.ArtifactDetail;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactManager;
import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.PresentationLayer.ArtifactDetailPresenter.DetailFragmentPresenter;
import com.example.family_artifact_register.PresentationLayer.ArtifactDetailPresenter.DetailViewModel;
import com.example.family_artifact_register.PresentationLayer.ArtifactDetailPresenter.DetailViewModelFactory;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.PresentationLayer.HubPresenter.PostDetailViewModel;
import com.example.family_artifact_register.PresentationLayer.HubPresenter.PostDetailViewModelFactory;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.Util.MediaProcessHelper;

import java.util.List;

import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getImageRecyclerView;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getVideoPlayIcon;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getVideoThumbnail;


/**
 * @author Haichao Song 854035,
 * @time 2019-9-19 11:54:23
 * @description activity for artifact detail view.
 */
public class ArtifactDetailActivity extends AppCompatActivity implements DetailFragmentPresenter.IView, IFragment {

    public static final String TAG = ArtifactDetailActivity.class.getSimpleName();

    private ArtifactItemWrapper artifactItemWrapper;

    private DetailViewModel viewModel;

    private DetailImageAdapter detailImageAdapter;

    private DetailFragmentPresenter dfp;

    TextView mTitleTv, mDescTv, mUserTv;
    ImageView mAvatarIv;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);

        // Use intent to send information to artifact detail activity
        Intent intent = getIntent();
        String artifactItemPostId = intent.getStringExtra("artifactItemPostId");

        viewModel = ViewModelProviders.of(this, new DetailViewModelFactory(getApplication())).get(DetailViewModel.class);

        // force the system not to display action bar title
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTitleTv = findViewById(R.id.publisher);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(detailImageAdapter);
        mDescTv = findViewById(R.id.desc);
        mUserTv = findViewById(R.id.user);
        mAvatarIv = findViewById(R.id.avatarIv);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.
                getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        Log.v(TAG, "recycler view created");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(this.getDrawable(R.drawable.gradient_background));

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        DetailImageAdapter detailImageAdapter = new DetailImageAdapter(this);

        recyclerView.setAdapter(detailImageAdapter);

        viewModel.getArtifactItem(artifactItemPostId).observe(this, new Observer<ArtifactItemWrapper>() {
            @Override
            public void onChanged(ArtifactItemWrapper artifactItemWrapper) {
                detailImageAdapter.setData(artifactItemWrapper);
                Log.i(TAG, "some changes happened");

                // Set artifact information the same as activity hub
                mTitleTv.setText(artifactItemWrapper.getPostId());
                mDescTv.setText(artifactItemWrapper.getDescription());
                mUserTv.setText(artifactItemWrapper.getUid());

//                if (layoutManager.getSpanSizeLookup() != null) {
//                    if (artifactItemWrapper.getLocalMediaDataUrls().size() <= 1) {
//                        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                            @Override
//                            public int getSpanSize(int position) {
//                                return 3;
//                            }
//                        });
//                    } else {
//                        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                            @Override
//                            public int getSpanSize(int position) {
//                                return 1;
//                            }
//                        });
//                    }
//                }
            }
        });


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
    /**
     * @return created me fragment
     */
    public static ArtifactDetailActivity newInstance() { return new ArtifactDetailActivity(); }

    // ********************************** implement presenter ************************************
    @Override
    public void addData(ArtifactItem artifactItem) {
        Log.d(TAG, "addData");
    }

    @Override
    public String getFragmentTag() { return TAG; }
}
