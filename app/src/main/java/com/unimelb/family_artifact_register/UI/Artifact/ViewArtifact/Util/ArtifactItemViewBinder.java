package com.unimelb.family_artifact_register.UI.Artifact.ViewArtifact.Util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.tmall.ultraviewpager.UltraViewPager;
import com.unimelb.family_artifact_register.PresentationLayer.Util.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.Artifact.ArtifactDetail.ArtifactDetailActivity;
import com.unimelb.family_artifact_register.UI.Artifact.ArtifactTimeline.TimelineActivity;

import java.util.ArrayList;
import java.util.List;

import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;
import tellh.com.stickyheaderview_rv.adapter.ViewBinder;

import static com.unimelb.family_artifact_register.UI.Artifact.ArtifactDetail.ArtifactDetailActivity.ARTIFACT_ITEM_ID_KEY;
import static com.unimelb.family_artifact_register.UI.Artifact.ArtifactTimeline.TimelineActivity.TIMELINE_ID_KEY;
import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getVideoPlayIcon;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getVideoThumbnail;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.setImagesViewPager;

/**
 * sticky item adapter
 */
public class ArtifactItemViewBinder extends ViewBinder<StickyArtifactItemItem, ArtifactItemViewBinder.ViewHolder> {
    /**
     * class tag
     */
    public static final String TAG = ArtifactItemViewBinder.class.getSimpleName();

    /**
     * list of images or video
     */
    private List<Uri> mediaList;

    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(StickyHeaderViewAdapter adapter, ViewHolder holder, int position, StickyArtifactItemItem entity) {
        ArtifactItemWrapper artifactItemWrapper = entity.getArtifactItemWrapper();
        Context context = entity.getContext();

        holder.time.setText(artifactItemWrapper.getLastUpdateDateTime());
        holder.description.setText(artifactItemWrapper.getDescription());

        mediaList = new ArrayList<>();
        for (String mediaUrl : artifactItemWrapper.getLocalMediaDataUrls()) {
            Log.d(TAG, "media uri: " + mediaUrl);
            mediaList.add(Uri.parse(mediaUrl));
        }

        holder.clearFrame();

        // image view
        if (artifactItemWrapper.getMediaType() == TYPE_IMAGE) {
            setImagesViewPager(mediaList, context, holder.ultraViewPager, true);
            holder.frame.setVisibility(View.GONE);
            holder.ultraViewPager.setVisibility(View.VISIBLE);
            // video view
        } else if (artifactItemWrapper.getMediaType() == TYPE_VIDEO) {
            ImageView iv = getVideoThumbnail(mediaList.get(0), context);

            ImageView playIcon = getVideoPlayIcon(context);

            // set frame's layout and add image view to it programmatically
            holder.frame.addView(iv);
            holder.frame.addView(playIcon);
            holder.ultraViewPager.setVisibility(View.GONE);
        } else {
            Log.e(TAG, "unknown media type !!!");
        }

        holder.timelineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "timeline button is clicked");
                Intent timelineActivity = new Intent(view.getContext(), TimelineActivity.class);
                timelineActivity.putExtra(TIMELINE_ID_KEY, artifactItemWrapper.getArtifactTimelineId());
                view.getContext().startActivity(timelineActivity);
            }
        });

        holder.detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "detail button is clicked");
                Intent detailActivity = new Intent(view.getContext(), ArtifactDetailActivity.class);
                detailActivity.putExtra(ARTIFACT_ITEM_ID_KEY, artifactItemWrapper.getPostId());
                view.getContext().startActivity(detailActivity);
            }
        });
    }

    @Override
    public int getItemLayoutId(StickyHeaderViewAdapter adapter) {
        return R.layout.item_my_artifact;
    }

    /**
     * view holder for ArtifactItemViewBinder
     */
    static class ViewHolder extends ViewBinder.ViewHolder {
        /**
         * artifact item's creation time
         */
        TextView time;

        /**
         * artifact item's  description
         */
        TextView description;

        /**
         * artifact item's video frame
         */
        FrameLayout frame;

        /**
         * artifact item's image view pager
         */
        UltraViewPager ultraViewPager;

        /**
         * artifact item's timeline button
         */
        MaterialButton timelineButton;

        /**
         * artifact item's detail button
         */
        MaterialButton detailButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.item_my_artifact_time);
            description = itemView.findViewById(R.id.item_my_artifact_description);
            frame = itemView.findViewById(R.id.item_my_artifact_media);
            ultraViewPager = itemView.findViewById(R.id.ultra_viewpager);
            timelineButton = itemView.findViewById(R.id.item_my_artifact_timeline_button);
            detailButton = itemView.findViewById(R.id.item_my_artifact_more_button);
        }

        public void clearFrame() {
            frame.removeAllViews();
        }
    }
}
