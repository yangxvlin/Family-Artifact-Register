package com.example.family_artifact_register.UI.ArtifactHub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.ArtifactTimeline.TimelineActivity;
import com.example.family_artifact_register.UI.Util.ImagesRecyclerViewAdapter;
import com.example.family_artifact_register.UI.Util.MyArtifactsRecyclerViewHolder;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.family_artifact_register.UI.ArtifactTimeline.TimelineActivity.TIMELINE_ID_KEY;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;

/**
 * @author Haichao Song 854035,
 * @time 2019-9-18 14:28:43
 * @description Adapter for models recycler view
 */
public class HubModelAdapter extends RecyclerView.Adapter<HubModelViewHolder> {

    private static final String TAG = com.example.family_artifact_register.UI.Util.MyArtifactsRecyclerViewAdapter.class.getSimpleName();
    private List<ArtifactItem> artifactItemList;

//    private FragmentManager fm;

    private Context context;

    public HubModelAdapter(Context context) {
        this.artifactItemList = new ArrayList<>();
        this.context = context;
//        this.fm = fm;
    }

    @NonNull
    @Override
    public HubModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new HubModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HubModelViewHolder holder, int position) {
        ArtifactItem artifactItem = artifactItemList.get(position);

        holder.username.setText(artifactItem.getUid());
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
            holder.postImage.setLayoutParams(layoutParam);
            holder.postImage.addView(imageRecyclerView);
            // video view
        } else if (artifactItem.getMediaType() == TYPE_VIDEO) {
            // set frame layout param
            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParam.gravity = Gravity.CENTER;

            // set media
            VideoView mediaView = new VideoView(context);
            mediaView.setLayoutParams(new FrameLayout.LayoutParams(
                    800,
                    1200)
            );
            mediaView.setVideoURI(mediaList.get(0));
            mediaView.setMediaController(new MediaController(context));
            mediaView.start();
            mediaView.requestFocus();
            mediaView.setOnCompletionListener(mp -> {
                Log.d(TAG, "Video play finish.");
            });
            mediaView.setOnErrorListener((mp, what, extra) -> {
                Log.d(TAG, "Video play error!!!");
                return false;
            });

            holder.postImage.setLayoutParams(layoutParam);
            holder.postImage.addView(mediaView);
        } else {
            Log.e(TAG, "unknown media type !!!");
        }

        holder.timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timelineId = artifactItem.getArtifactTimelineId();
                Log.d(TAG, "Timeline ID: " + timelineId);
                Intent i = new Intent(view.getContext(), TimelineActivity.class);
                i.putExtra(TIMELINE_ID_KEY, timelineId);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() { return artifactItemList.size(); }

    public void setData(List<ArtifactItem> newData) {
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
