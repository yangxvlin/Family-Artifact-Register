package com.example.family_artifact_register.UI.Util;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.ArtifactTimeline.TimelineActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.family_artifact_register.UI.ArtifactTimeline.TimelineActivity.TIMELINE_ID_KEY;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-21 15:57:25
 * @description
 */
public class MyArtifactsRecyclerViewAdapter extends RecyclerView.Adapter<MyArtifactsRecyclerViewHolder> {

    private static final String TAG = MyArtifactsRecyclerViewAdapter.class.getSimpleName();
    private List<ArtifactItem> artifactItemList;

//    private FragmentManager fm;

    private Context context;

    private boolean isFullScreen = false;

    public MyArtifactsRecyclerViewAdapter(Context context) {
        this.artifactItemList = new ArrayList<>();
        this.context = context;
//        this.fm = fm;
    }

    @NonNull
    @Override
    public MyArtifactsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_artifact, parent, false);
        return new MyArtifactsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyArtifactsRecyclerViewHolder holder, int position) {
        ArtifactItem artifactItem = artifactItemList.get(position);

        holder.time.setText(artifactItem.getLastUpdateDateTime());
        holder.description.setText(artifactItem.getDescription());

        List<Uri> mediaList = new ArrayList<>();
        for (String mediaUrl: artifactItem.getMediaDataUrls()) {
            Log.d(TAG, "media uri" + mediaUrl);
            mediaList.add(Uri.parse(mediaUrl));
        }


        // image view
        if (artifactItem.getMediaType() == TYPE_IMAGE) {
            // recycler view adapter for display images
            ImagesRecyclerViewAdapter imagesRecyclerViewAdapter;
            // recycler view for display images
            RecyclerView imageRecyclerView;

            // set frame layout param
            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParam.gravity = Gravity.CENTER;
            layoutParam.topMargin = 20;
            layoutParam.bottomMargin = 20;

            // set recycler view images
            RecyclerView.LayoutParams recyclerViewParam = new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
            );
            imageRecyclerView = new RecyclerView(context);
            imageRecyclerView.setLayoutParams(recyclerViewParam);


            // images horizontally
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
            );
            imageRecyclerView.setLayoutManager(layoutManager);

            // set the divider between list item
            DividerItemDecoration divider = new DividerItemDecoration(imageRecyclerView.getContext(), layoutManager.getOrientation());
            divider.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider_wechat_white));
            imageRecyclerView.addItemDecoration(divider);

            // image adapter
            imagesRecyclerViewAdapter = new ImagesRecyclerViewAdapter(
                    200,
                    200,
                    context
            );
            for (Uri image: mediaList) {
                imagesRecyclerViewAdapter.addData(image);
            }
            imageRecyclerView.setAdapter(imagesRecyclerViewAdapter);
            holder.frame.setLayoutParams(layoutParam);
            holder.frame.addView(imageRecyclerView);
        // video view
        } else if (artifactItem.getMediaType() == TYPE_VIDEO) {
            // set frame layout param
            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParam.gravity = Gravity.CENTER;
            layoutParam.topMargin = 20;
            layoutParam.bottomMargin = 20;

            // set up image view layout
            ImageView iv = new ImageView(context);
            iv.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
            // image cropped in center to ge square
            iv.setAdjustViewBounds(true);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // set thumbnail from video to image
            Glide.with(context)
                    .load(mediaList.get(0)) // or URI/path
                    .into(iv); //imageview to set thumbnail to
            // start video when clicked the thumbnail
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // whole screen dialog of image
                    Dialog dia = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

                    VideoView videoView = new VideoView(context);
                    videoView.setLayoutParams(
                            new ActionBar.LayoutParams(
                                    ActionBar.LayoutParams.MATCH_PARENT,
                                    ActionBar.LayoutParams.MATCH_PARENT
                            )
                    );
                    videoView.setVideoURI(mediaList.get(0));
                    videoView.setMediaController(new MediaController(context));
                    videoView.start();
                    videoView.requestFocus();
                    videoView.setOnCompletionListener(mp -> {
                        Log.d(TAG, "Video play finish.");
                    });
                    videoView.setOnErrorListener((mp, what, extra) -> {
                        Log.d(TAG, "Video play error!!!");
                        return false;
                    });
                    // click to return
                    videoView.setOnClickListener(v -> {
                        dia.dismiss();
                    });
                    dia.setContentView(videoView);
                    dia.show();

                    dia.setCanceledOnTouchOutside(true); // Sets whether this dialog is
                    Window w = dia.getWindow();
                    WindowManager.LayoutParams lp = w.getAttributes();
                    lp.x = 0;
                    lp.y = 40;
                    dia.onWindowAttributesChanged(lp);
                }
            });

            // add a play icon to the thumbnail
            ImageView playIcon = new ImageView(context);
            LinearLayout.LayoutParams playIconLayout = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            playIconLayout.gravity = Gravity.CENTER;
            playIcon.setLayoutParams(playIconLayout);
            playIcon.setImageResource(R.drawable.ic_play_circle_filled_white);

            // set frame's layout and add image view to it programmatically
            holder.frame.setLayoutParams(layoutParam);
            holder.frame.addView(iv);
            holder.frame.addView(playIcon);
        } else {
            Log.e(TAG, "unknown media type !!!");
        }

        holder.navigateToArtifactTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "#" + position + " holder.navigateToArtifactTimeline clicked");
                Intent activityChangeIntent = new Intent(context, TimelineActivity.class);
                activityChangeIntent.putExtra(TIMELINE_ID_KEY, artifactItemList.get(position).getArtifactTimelineId());
                context.startActivity(activityChangeIntent);
            }
        });
    }

    @Override
    public int getItemCount() { return artifactItemList.size(); }

    public void setData(List<ArtifactItem> newData) {
        Collections.sort(newData, new Comparator<ArtifactItem>() {
            @Override
            public int compare(ArtifactItem artifactItem, ArtifactItem t1) {
                return -1 * artifactItem.getLastUpdateDateTime().compareTo(t1.getLastUpdateDateTime());
            }
        });
        artifactItemList = newData;
        notifyDataSetChanged();
    }

    // *************************************** getter & setters ***********************************
    public void addData(ArtifactItem artifactItem) {
        // 0 to add data at start
        this.artifactItemList.add(0, artifactItem);
        notifyDataSetChanged();
    }
}
