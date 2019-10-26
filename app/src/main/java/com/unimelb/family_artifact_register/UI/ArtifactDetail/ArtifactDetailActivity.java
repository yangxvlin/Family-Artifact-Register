package com.unimelb.family_artifact_register.UI.ArtifactDetail;


import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactDetailPresenter.DetailViewModel;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactDetailPresenter.DetailViewModelFactory;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter.UserInfoWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.Util.Pair;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.ArtifactComment.ArtifactCommentActivity;
import com.unimelb.family_artifact_register.UI.ArtifactTimeline.TimelineActivity;
import com.unimelb.family_artifact_register.UI.MapServiceFragment.MapDisplayFragment;
import com.unimelb.family_artifact_register.UI.MapServiceFragment.MarkerHelper;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.unimelb.family_artifact_register.UI.MapServiceFragment.MarkerHelper.getAddress;
import static com.unimelb.family_artifact_register.UI.MapServiceFragment.MarkerHelper.getCreateAt;
import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getImageRecyclerView;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getVideoPlayIcon;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getVideoThumbnail;
import static com.unimelb.family_artifact_register.Util.ScreenUnitHelper.convertDpToPixel;

/**
 * @author Haichao Song 854035,
 * @time 2019-10-4 22:13:05
 * @description activity for showing artifact detail.
 */
public class ArtifactDetailActivity extends AppCompatActivity {

    // get class tag
    public static final String TAG = ArtifactDetailActivity.class.getSimpleName();

    private DetailImageAdapter detailImageAdapter;
    private DetailViewModel viewModel;

    private String PostID;
    private TextView desc, user, time;
    private ImageView avatar;
    public MaterialButton timeline;
    private FrameLayout postImage;

    private FragmentManager fm = getSupportFragmentManager();

    public static final String ARTIFACT_ITEM_ID_KEY = "artifactItemPostId";

    private MapDisplayFragment happenedMap = MapDisplayFragment.newInstance(Collections.emptyList());
    private MapDisplayFragment storedMap = MapDisplayFragment.newInstance(Collections.emptyList());

    private ImageView commentButton;
    private ImageView likeButton;
    private TextView likesNumber;
    private TextView createLocationText;
    private TextView storeLocationHint;

    private FrameLayout storedLocationMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get artifact id in intent to distinguish which artifact is clicked
        setContentView(R.layout.activity_artifact_detail);
        Intent intent = getIntent();
        PostID = intent.getStringExtra(ARTIFACT_ITEM_ID_KEY);


        detailImageAdapter = new DetailImageAdapter(this);

        desc = findViewById(R.id.desc);
        user = findViewById(R.id.user);
        postImage = findViewById(R.id.post_image);
        avatar = findViewById(R.id.avatarIv);
        time = findViewById(R.id.publisher);
        timeline = findViewById(R.id.view_timeline);
        likeButton = findViewById(R.id.activity_artifact_detail_likes);
        likesNumber = findViewById(R.id.activity_artifact_detail_likes_text);
        commentButton = findViewById(R.id.activity_artifact_detail_comment);
        createLocationText = findViewById(R.id.activity_artifact_detail_create_location);
        storeLocationHint = findViewById(R.id.activity_artifact_detail_stored_location_map_title);
        storedLocationMap = findViewById(R.id.activity_artifact_detail_stored_location_map);

        viewModel = ViewModelProviders.of(this,
                new DetailViewModelFactory(getApplication())).get(DetailViewModel.class);

        ArtifactDetailActivity artifactDetailActivity = this;

        // get the artifact item wrapper from view model
        viewModel.getArtifactItem(PostID).observe(this,
                new Observer<Pair<ArtifactItemWrapper, MapLocation>>() {

            /**
             * when data get from view model changes, means live data arrives
             * set artifact item and map location in activity
             * @param artifactItemWrapperMapLocationPair Pair an artifact item with map location
             */
            @Override
            public void onChanged(Pair<ArtifactItemWrapper, MapLocation>
                                          artifactItemWrapperMapLocationPair) {
                ArtifactItemWrapper artifactItemWrapper =
                        artifactItemWrapperMapLocationPair.getFst();
                MapLocation mapLocation = artifactItemWrapperMapLocationPair.getSnd();

                Log.d(TAG, "Some changes happen");

                // get artifact poster information from view model
                viewModel.getPosterInfo(artifactItemWrapper.getUid()).observe(
                        artifactDetailActivity, new Observer<UserInfoWrapper>() {
                    /**
                     * when data get from view model changes, means live data arrives
                     * set poster display name and avatar in the activity
                     * @param userInfoWrapper the poster information
                     */
                    @Override
                    public void onChanged(UserInfoWrapper userInfoWrapper) {
                        String url = userInfoWrapper.getPhotoUrl();
                        user.setText(userInfoWrapper.getDisplayName());
                        if(url != null) {
                            avatar.setImageURI(Uri.parse(url));
                        }
                    }
                });

                // distinguish if crrent user already likes the post
                // and set like image according to it
                likesNumber.setText(Integer.toString(artifactItemWrapperMapLocationPair.getFst().getLikes().size()));
                if ((artifactItemWrapper.getLikes().size() != 0) &&
                        (artifactItemWrapper.getLikes().containsKey(viewModel.getCurrentUid()))) {
                    likeButton.setImageResource(R.drawable.ic_liked);
                    likeButton.setTag("liked");
                } else {
                    likeButton.setImageResource(R.drawable.ic_like);
                    likeButton.setTag("unliked");
                }

                // go to comment activity when user press comment button
                commentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String pid = artifactItemWrapperMapLocationPair.getFst().getPostId();
                        Intent i = new Intent(view.getContext(), ArtifactCommentActivity.class);
                        i.putExtra("artifactItemPostId", pid);
                        startActivity(i);
                    }
                });

                // like or unlike post when user press like image
                // set local likes number change and also pass change to backend
                likeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int likes_before = Integer.valueOf(likesNumber.getText().toString());
                        Log.d(TAG, "number of likes before:" + likes_before);
                        if (likeButton.getTag() == "liked") {
                            likeButton.setImageResource(R.drawable.ic_like);
                            likeButton.setTag("unlike");
                            likesNumber.setText(String.valueOf(likes_before-1));
                        } else {
                            likeButton.setImageResource(R.drawable.ic_liked);
                            likeButton.setTag("liked");
                            likesNumber.setText(String.valueOf(likes_before+1));
                        }
                        viewModel.getLikeChange(likeButton.getTag().toString(), artifactItemWrapper.getPostId());
                    }
                });

                // Set artifact information the same as activity hub
                desc.setText(artifactItemWrapper.getDescription());
                time.setText(getCreateAt(new Pair<>(artifactItemWrapper, null), getApplicationContext()));

                // current user is the owner
                if (UserInfoManager.getInstance().getCurrentUid().equals(artifactItemWrapperMapLocationPair.getFst().getUid())) {
                    RelativeLayout.LayoutParams frameParam = new RelativeLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            (int)convertDpToPixel(200, getApplicationContext()));
                    frameParam.addRule(RelativeLayout.BELOW, R.id.activity_artifact_detail_stored_location_map_title);
                    storedLocationMap.setLayoutParams(frameParam);
                    storedLocationMap.setVisibility(View.VISIBLE);
                    storeLocationHint.setVisibility(View.VISIBLE);

                    viewModel.getStoredLocation(PostID).observe(artifactDetailActivity, new Observer<MapLocation>() {
                        /**
                         * when data get from view model changes, means live data arrives
                         * get map location and display on the map
                         * @param mapLocation stored location of artifact item
                         */
                        @Override
                        public void onChanged(MapLocation mapLocation) {
                            fm.beginTransaction().replace(R.id.activity_artifact_detail_stored_location_map, storedMap).commit();
                            storedMap.addDisplayArtifactItems(new Pair<>(artifactItemWrapper, mapLocation));
                        }
                    });
                }

                fm.beginTransaction().replace(R.id.map_happened, happenedMap).commit();
                happenedMap.addDisplayArtifactItems(artifactItemWrapperMapLocationPair);

                List<Uri> mediaList = new ArrayList<>();
                for (String mediaUrl: artifactItemWrapper.getLocalMediaDataUrls()) {
                    Log.d(TAG, "media uri" + mediaUrl);
                    mediaList.add(Uri.parse(mediaUrl));
                }

                // set images format according to number of images pass to activity
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

                    // render images or video on the view
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

                // set sored location on the button
                Log.d(TAG, "Ready to run get store pair");
                viewModel.getUploadLocation(PostID).observeForever(new Observer<MapLocation>() {
                    @Override
                    public void onChanged(MapLocation mapLocation) {
                        Log.d(TAG, "upload location: " + mapLocation.toString());
                        Pair<ArtifactItemWrapper, MapLocation> pair = new Pair<>(artifactItemWrapper, mapLocation);
                        createLocationText.setText(getAddress(pair, artifactDetailActivity, getString(R.string.upload_at)));
                    }
                });

                // go to timeline activity when user press timeline button
                timeline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tid = artifactItemWrapper.getArtifactTimelineId();
                        Intent i = new Intent(view.getContext(), TimelineActivity.class);
                        i.putExtra("timelineID", tid);
                        startActivity(i);
                    }
                });

            }
        });

    }

}
