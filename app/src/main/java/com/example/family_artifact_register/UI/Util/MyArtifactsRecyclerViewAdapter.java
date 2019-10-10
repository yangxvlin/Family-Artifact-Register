package com.example.family_artifact_register.UI.Util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.ArtifactTimeline.TimelineActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.family_artifact_register.UI.ArtifactTimeline.TimelineActivity.TIMELINE_ID_KEY;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getImageRecyclerView;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getVideoPlayIcon;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getVideoThumbnail;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-21 15:57:25
 * @description
 */
public class MyArtifactsRecyclerViewAdapter extends RecyclerView.Adapter<MyArtifactsRecyclerViewHolder> {

    private static final String TAG = MyArtifactsRecyclerViewAdapter.class.getSimpleName();
    private List<ArtifactItemWrapper> artifactItemWrapperList;

//    private FragmentManager fm;

    private Context context;

    private List<Uri> mediaList;

    public MyArtifactsRecyclerViewAdapter(Context context) {
        this.artifactItemWrapperList = new ArrayList<>();
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
        ArtifactItemWrapper artifactItemWrapper = artifactItemWrapperList.get(position);

        holder.time.setText(artifactItemWrapper.getLastUpdateDateTime());
        holder.description.setText(artifactItemWrapper.getDescription());

        mediaList = new ArrayList<>();
        for (String mediaUrl: artifactItemWrapper.getLocalMediaDataUrls()) {
            Log.d(TAG, "media uri" + mediaUrl);
            mediaList.add(Uri.parse(mediaUrl));
        }

        holder.clearFrame();
        // set frame layout param
        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParam.gravity = Gravity.CENTER;
        layoutParam.topMargin = 20;
        layoutParam.bottomMargin = 20;

        // image view
        if (artifactItemWrapper.getMediaType() == TYPE_IMAGE) {

            View imagesRecyclerView = getImageRecyclerView(200, 200, mediaList, context);

            holder.frame.addView(imagesRecyclerView);
            holder.frame.setLayoutParams(layoutParam);
        // video view
        } else if (artifactItemWrapper.getMediaType() == TYPE_VIDEO) {
            ImageView iv = getVideoThumbnail(200, 200, mediaList.get(0), context);

            ImageView playIcon = getVideoPlayIcon(context);

            // set frame's layout and add image view to it programmatically
            holder.frame.addView(iv);
            holder.frame.addView(playIcon);
            holder.frame.setLayoutParams(layoutParam);
        } else {
            Log.e(TAG, "unknown media type !!!");
        }

        holder.navigateToArtifactTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "#" + position + " holder.navigateToArtifactTimeline clicked");
                Intent activityChangeIntent = new Intent(context, TimelineActivity.class);
                activityChangeIntent.putExtra(TIMELINE_ID_KEY, artifactItemWrapperList.get(position).getArtifactTimelineId());
                context.startActivity(activityChangeIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (artifactItemWrapperList == null) {
            return 0;
        }
        return artifactItemWrapperList.size();
    }

    public void setData(List<ArtifactItemWrapper> newData) {
        artifactItemWrapperList.clear();

        Collections.sort(newData, new Comparator<ArtifactItemWrapper>() {
            @Override
            public int compare(ArtifactItemWrapper artifactItemWrapper, ArtifactItemWrapper t1) {
                return -1 * artifactItemWrapper.getLastUpdateDateTime().compareTo(t1.getLastUpdateDateTime());
            }
        });
        artifactItemWrapperList.addAll(newData);
        notifyDataSetChanged();
    }

    // *************************************** getter & setters ***********************************


//    public void addData(ArtifactItem artifactItem) {
//        // 0 to add data at start
//        this.artifactItemWrapperList.add(0, artifactItem);
//        notifyDataSetChanged();
//    }
}
