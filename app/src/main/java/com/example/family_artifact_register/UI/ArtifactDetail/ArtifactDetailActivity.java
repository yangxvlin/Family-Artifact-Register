package com.example.family_artifact_register.UI.ArtifactDetail;


import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.example.family_artifact_register.PresentationLayer.ArtifactDetailPresenter.DetailViewModel;
import com.example.family_artifact_register.PresentationLayer.ArtifactDetailPresenter.DetailViewModelFactory;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.UserInfoWrapper;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.MapServiceFragment.MapDisplayFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getImageRecyclerView;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getVideoPlayIcon;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getVideoThumbnail;

public class ArtifactDetailActivity extends AppCompatActivity {

    public static final String TAG = ArtifactDetailActivity.class.getSimpleName();

    private RecyclerView recyclerView;

    private DetailImageAdapter detailImageAdapter;

    private DetailViewModel viewModel;

    private ArtifactItem artifactItem;

    private String PostID;

    private TextView desc, user;

    private ImageView avatar;

    private FrameLayout postImage;

    private FragmentManager fm = getSupportFragmentManager();

    public static final String ARTIFACT_ITEM_ID_KEY = "artifactItemPostId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artifact_detail);
        Intent intent = getIntent();
        PostID = intent.getStringExtra(ARTIFACT_ITEM_ID_KEY);


        detailImageAdapter = new DetailImageAdapter(this);

        desc = findViewById(R.id.desc);
        user = findViewById(R.id.user);
        postImage = findViewById(R.id.post_image);
        avatar = findViewById(R.id.avatarIv);

        viewModel = ViewModelProviders.of(this, new DetailViewModelFactory(getApplication()))
                .get(DetailViewModel.class);

        ArtifactDetailActivity artifactDetailActivity = this;

        viewModel.getArtifactItem(PostID).observe(this, new Observer<ArtifactItemWrapper>() {
            @Override
            public void onChanged(ArtifactItemWrapper artifactItemWrapper) {
                Log.d(TAG, "Some changes happen");

                viewModel.getPosterInfo(artifactItemWrapper.getUid()).observe(artifactDetailActivity, new Observer<UserInfoWrapper>() {
                    @Override
                    public void onChanged(UserInfoWrapper userInfoWrapper) {
                        String url = userInfoWrapper.getPhotoUrl();
                        user.setText(userInfoWrapper.getDisplayName());
                        if(url != null) {
                            avatar.setImageURI(Uri.parse(url));
                        }
                    }
                });

                // Set artifact information the same as activity hub
                desc.setText(artifactItemWrapper.getDescription());

                List<Uri> mediaList = new ArrayList<>();
                for (String mediaUrl: artifactItemWrapper.getLocalMediaDataUrls()) {
                    Log.d(TAG, "media uri" + mediaUrl);
                    mediaList.add(Uri.parse(mediaUrl));
                }

                if (mediaList.size() > 0) {
                    postImage.removeAllViews();
                    int width = postImage.getWidth();
                    int span = Math.min((int) Math.ceil(
                            Math.sqrt(
                                    (double) artifactItemWrapper
                                            .getLocalMediaDataUrls().size()
                            )
                    ), 3);
                    int imageLength = (width / span);
                    GridLayoutManager layoutManager = new GridLayoutManager(artifactDetailActivity, span);
                    if (layoutManager.getSpanSizeLookup() != null) {
                        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {
                                return 1;
                            }
                        });
                    }

                    View mediaView;

                    // image view
                    if (artifactItemWrapper.getMediaType() == TYPE_IMAGE) {

                        mediaView = getImageRecyclerView(imageLength, imageLength,
                                mediaList, artifactDetailActivity);
                        ((RecyclerView) mediaView).setLayoutManager(layoutManager);
                        ((RecyclerView) mediaView).addItemDecoration(new RecyclerView.ItemDecoration() {
                            @Override
                            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                                int position = parent.getChildAdapterPosition(view); // item position
                                int spanCount = 2;
                                int spacing = 10;//spacing between views in grid

                                if (position >= 0) {
                                    int column = position % spanCount; // item column

                                    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                                    outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                                    if (position < spanCount) { // top edge
                                        outRect.top = spacing;
                                    }
                                    outRect.bottom = spacing; // item bottom
                                } else {
                                    outRect.left = 0;
                                    outRect.right = 0;
                                    outRect.top = 0;
                                    outRect.bottom = 0;
                                }
                            }
                        });
                        postImage.addView(mediaView);
                    } else if (artifactItemWrapper.getMediaType() == TYPE_VIDEO) {
                        // video view
                        mediaView = getVideoThumbnail(postImage.getWidth(), postImage.getWidth(),
                                mediaList.get(0), artifactDetailActivity);

                        ImageView playIcon = getVideoPlayIcon(artifactDetailActivity);
                        postImage.addView(mediaView);
                        postImage.addView(playIcon);
                    } else {
                        Log.e(TAG, "unknown media type !!!");
                    }
                }


                Log.d(TAG, "Set Data");
            }
        });

        viewModel.getLocationHappened(PostID).observeForever(new Observer<MapLocation>() {
            @Override
            public void onChanged(MapLocation mapLocation) {
                // TODO map location data from DB
                Log.d(TAG, "Happened Map Location: " + mapLocation.toString());
                MapDisplayFragment happened = MapDisplayFragment.newInstance(Collections.emptyList());
                fm.beginTransaction().replace(R.id.map_happened, happened).commit();
                happened.setDisplayArtifactItems(new ArtifactItemWrapper(artifactItem), mapLocation);
            }
        });
    }



}

///**
// * @author Haichao Song 854035,
// * @time 2019-9-19 11:54:23
// * @description activity for artifact detail view.
// */
//public class ArtifactDetailActivity extends AppCompatActivity implements DetailFragmentPresenter.IView, IFragment {
//
//    public static final String TAG = ArtifactDetailActivity.class.getSimpleName();
//
//    private ArtifactItemWrapper artifactItemWrapper;
//
//    private DetailViewModel viewModel;
//
//    private DetailImageAdapter detailImageAdapter;
//
//    private DetailFragmentPresenter dfp;
//
////    TextView mTitleTv, mDescTv, mUserTv;
////    ImageView mAvatarIv;
//    RecyclerView recyclerView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_artifact_detail);
//
//        // Use intent to send information to artifact detail activity
//        Intent intent = getIntent();
//        String artifactItemPostId = intent.getStringExtra("artifactItemPostId");
//
//        viewModel = ViewModelProviders.of(this, new DetailViewModelFactory(getApplication())).get(DetailViewModel.class);
//
//        // force the system not to display action bar title
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//        recyclerView = findViewById(R.id.recycler_view);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(detailImageAdapter);
//
////        mDescTv = findViewById(R.id.desc);
////        mUserTv = findViewById(R.id.user);
////        mAvatarIv = findViewById(R.id.avatarIv);
//
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.
//                getContext(), layoutManager.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
//        Log.v(TAG, "recycler view created");
//
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(this.getDrawable(R.drawable.gradient_background));
//
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(manager);
//
//        DetailImageAdapter detailImageAdapter = new DetailImageAdapter(this);
//
//        recyclerView.setAdapter(detailImageAdapter);
//
//        viewModel.getArtifactItem(artifactItemPostId).observe(this, new Observer<ArtifactItemWrapper>() {
//            @Override
//            public void onChanged(ArtifactItemWrapper artifactItemWrapper) {
//                detailImageAdapter.setData(artifactItemWrapper);
//                Log.i(TAG, "some changes happened");
//
////                // Set artifact information the same as activity hub
////                mTitleTv.setText(artifactItemWrapper.getPostId());
////                mDescTv.setText(artifactItemWrapper.getDescription());
////                mUserTv.setText(artifactItemWrapper.getUid());
//
////                if (layoutManager.getSpanSizeLookup() != null) {
////                    if (artifactItemWrapper.getLocalMediaDataUrls().size() <= 1) {
////                        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
////                            @Override
////                            public int getSpanSize(int position) {
////                                return 3;
////                            }
////                        });
////                    } else {
////                        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
////                            @Override
////                            public int getSpanSize(int position) {
////                                return 1;
////                            }
////                        });
////                    }
////                }
//            }
//        });
//
//
////        String mTitle = intent.getStringExtra("iTitle");
////        String mDesc = intent.getStringExtra("iDesc");
////        String mUser = intent.getStringExtra("iUser");
////
////        byte[] mImageBytes = getIntent().getByteArrayExtra("iImage");
////        byte[] mAvatarBytes = getIntent().getByteArrayExtra("iImage");
////        Bitmap imageBitmap = BitmapFactory.decodeByteArray(mAvatarBytes, 0, mImageBytes.length);
////        Bitmap avatarBitmap = BitmapFactory.decodeByteArray(mAvatarBytes, 0, mAvatarBytes.length);
////
////        // Set action bar title to be artifact title
////        getMyActionBar().setTitle(mTitle);
////
////        // Set artifact information the same as activity hub
////        mTitleTv.setText(mTitle);
////        mDescTv.setText(mDesc);
////        mUserTv.setText(mUser);
////        mImageIv.setImageBitmap(imageBitmap);
////        mAvatarIv.setImageBitmap(avatarBitmap);
//    }
//
////    @Override
////    protected int getLayoutResource() {
////        return R.layout.activity_artifact_detail;
////    }
//    /**
//     * @return created me fragment
//     */
//    public static ArtifactDetailActivity newInstance() { return new ArtifactDetailActivity(); }
//
//    // ********************************** implement presenter ************************************
//    @Override
//    public void addData(ArtifactItem artifactItem) {
//        Log.d(TAG, "addData");
//    }
//
//    @Override
//    public String getFragmentTag() { return TAG; }
//}
