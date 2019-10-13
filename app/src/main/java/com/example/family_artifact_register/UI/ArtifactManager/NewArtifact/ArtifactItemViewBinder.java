package com.example.family_artifact_register.UI.ArtifactManager.NewArtifact;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.ArtifactTimeline.TimelineActivity;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.List;

import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;
import tellh.com.stickyheaderview_rv.adapter.ViewBinder;

import static com.example.family_artifact_register.UI.ArtifactTimeline.TimelineActivity.TIMELINE_ID_KEY;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getVideoPlayIcon;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getVideoThumbnail;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.setImagesViewPager;

public class ArtifactItemViewBinder extends ViewBinder<StickyArtifactItemItem, ArtifactItemViewBinder.ViewHolder> {
    public static final String TAG = ArtifactItemViewBinder.class.getSimpleName();

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
        for (String mediaUrl: artifactItemWrapper.getLocalMediaDataUrls()) {
            Log.d(TAG, "media uri: " + mediaUrl);
            mediaList.add(Uri.parse(mediaUrl));
        }

        holder.clearFrame();
        // set frame layout param
//        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT
//        );
//        layoutParam.gravity = Gravity.CENTER;
//        layoutParam.topMargin = 20;
//        layoutParam.bottomMargin = 20;

        // image view
        if (artifactItemWrapper.getMediaType() == TYPE_IMAGE) {

            // View imagesRecyclerView = getImageRecyclerView(200, 200, mediaList, context);

            // holder.frame.addView(imagesRecyclerView);

//            View imageSlider = getImagesSliderView(400, 400, mediaList, context);
//            holder.frame.addView(imageSlider);

            setImagesViewPager(mediaList, context, holder.ultraViewPager, true);
            holder.frame.setVisibility(View.GONE);
//            holder.ultraViewPager.setPadding(0, 20, 0, 20);
            holder.ultraViewPager.setVisibility(View.VISIBLE);
//            holder.frame.addView(imagesViewPager);

//            holder.frame.setLayoutParams(layoutParam);
            // video view
        } else if (artifactItemWrapper.getMediaType() == TYPE_VIDEO) {
            ImageView iv = getVideoThumbnail(mediaList.get(0), context);

            ImageView playIcon = getVideoPlayIcon(context);

            // set frame's layout and add image view to it programmatically
            holder.frame.addView(iv);
            holder.frame.addView(playIcon);
//            holder.frame.setLayoutParams(layoutParam);
            holder.ultraViewPager.setVisibility(View.GONE);
        } else {
            Log.e(TAG, "unknown media type !!!");
        }

        holder.navigateToArtifactTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "#" + position + " holder.navigateToArtifactTimeline clicked");
                Intent activityChangeIntent = new Intent(context, TimelineActivity.class);
                activityChangeIntent.putExtra(TIMELINE_ID_KEY, artifactItemWrapper.getArtifactTimelineId());
                context.startActivity(activityChangeIntent);
            }
        });
    }

    @Override
    public int getItemLayoutId(StickyHeaderViewAdapter adapter) {
        return R.layout.item_my_artifact;
    }

    static class ViewHolder extends ViewBinder.ViewHolder {

        TextView time;

        TextView description;

        FrameLayout frame;

        ImageView navigateToArtifactTimeline;

        UltraViewPager ultraViewPager;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.item_my_artifact_time);
            description = itemView.findViewById(R.id.item_my_artifact_description);
            frame = itemView.findViewById(R.id.item_my_artifact_media);
            navigateToArtifactTimeline = itemView.findViewById(R.id.item_my_artifact_right_arrow);
            ultraViewPager = itemView.findViewById(R.id.ultra_viewpager);
        }

        public void clearFrame() {
            frame.removeAllViews();
        }
    }
}
