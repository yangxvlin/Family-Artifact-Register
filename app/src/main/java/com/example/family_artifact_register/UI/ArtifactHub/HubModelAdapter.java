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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.ArtifactTimeline.TimelineActivity;
import com.example.family_artifact_register.UI.ArtifactDetail.ArtifactDetailActivity;
//import com.example.family_artifact_register.UI.ArtifactDetail.DetailFragment;
import com.example.family_artifact_register.UI.Util.ImagesRecyclerViewAdapter;
import com.example.family_artifact_register.UI.Util.MyArtifactsRecyclerViewHolder;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.sql.Time;
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
 * @author Haichao Song 854035,
 * @time 2019-9-18 14:28:43
 * @description Adapter for models recycler view
 */
public class HubModelAdapter extends RecyclerView.Adapter<HubModelViewHolder> {

    private static final String TAG = HubModelAdapter.class.getSimpleName();
    private List<ArtifactItemWrapper> artifactItemWrapperList;

//    private FragmentManager fm;

    private Context context;

    private List<Uri> mediaList;

    public HubModelAdapter(Context context) {
        this.artifactItemWrapperList = new ArrayList<>();
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
        ArtifactItemWrapper artifactItemWrapper = artifactItemWrapperList.get(position);

        holder.username.setText(artifactItemWrapper.getUid());
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
        layoutParam.leftMargin = 20;
        layoutParam.rightMargin = 20;

        // image view
        if (artifactItemWrapper.getMediaType() == TYPE_IMAGE) {

            View imagesRecyclerView = getImageRecyclerView(500, 500, mediaList, context);

            holder.postImage.addView(imagesRecyclerView);
            holder.postImage.setLayoutParams(layoutParam);
            // video view
        } else if (artifactItemWrapper.getMediaType() == TYPE_VIDEO) {
            ImageView iv = getVideoThumbnail(750, 750, mediaList.get(0), context);

            ImageView playIcon = getVideoPlayIcon(context);

            // set frame's layout and add image view to it programmatically
            holder.postImage.addView(iv);
            holder.postImage.addView(playIcon);
            holder.postImage.setLayoutParams(layoutParam);
        } else {
            Log.e(TAG, "unknown media type !!!");
        }

        holder.viewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pid = artifactItemWrapper.getPostId();
                Intent i = new Intent(view.getContext(), ArtifactDetailActivity.class);
                i.putExtra("artifactItemPostId", pid);
                context.startActivity(i);
            }
        });

        holder.timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tid = artifactItemWrapper.getArtifactTimelineId();
                Intent i = new Intent(view.getContext(), TimelineActivity.class);
                i.putExtra("timelineID", tid);
                context.startActivity(i);
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

        if(newData != null) {
            Collections.sort(newData, new Comparator<ArtifactItemWrapper>() {
                @Override
                public int compare(ArtifactItemWrapper artifactItemWrapper, ArtifactItemWrapper t1) {
                    return -1 * artifactItemWrapper.getLastUpdateDateTime().compareTo(t1.getLastUpdateDateTime());
                }
            });
            artifactItemWrapperList.addAll(newData);
        }
        notifyDataSetChanged();
    }


    // *************************************** getter & setters ***********************************
    public void addData(ArtifactItemWrapper artifactItemWrapper) {
        // 0 to add data at start
        this.artifactItemWrapperList.add(0, artifactItemWrapper);
        notifyDataSetChanged();
    }
}
