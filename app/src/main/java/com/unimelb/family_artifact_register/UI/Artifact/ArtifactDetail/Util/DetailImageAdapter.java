package com.unimelb.family_artifact_register.UI.Artifact.ArtifactDetail.Util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.PresentationLayer.Util.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.R;

import java.util.ArrayList;
import java.util.List;

import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getImageRecyclerView;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getVideoPlayIcon;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getVideoThumbnail;

/**
 * @author Haichao Song 854035,
 * @time 2019-10-3 12:35:41
 * @description adapter for artifact fragment. Deprecated because now detail page is an activity.
 */
@Deprecated
public class DetailImageAdapter extends RecyclerView.Adapter<DetailImageViewHolder> {

    private static final String TAG = DetailImageAdapter.class.getSimpleName();
    private ArtifactItemWrapper artifactItemWrapper;

    private Context context;

    private List<Uri> mediaList;

    public DetailImageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DetailImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_artifact_detail, parent, false);
        return new DetailImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailImageViewHolder holder, int position) {
//        ArtifactItemWrapper artifactItemWrapper = artifactItemWrapper.get(position);

        mediaList = new ArrayList<>();
        for (String mediaUrl : artifactItemWrapper.getLocalMediaDataUrls()) {
            Log.d(TAG, "media uri" + mediaUrl);
            mediaList.add(Uri.parse(mediaUrl));
        }

        holder.clearFrame();
        // image view
        if (artifactItemWrapper.getMediaType() == TYPE_IMAGE) {

            View imagesRecyclerView = getImageRecyclerView(500,
                    500, mediaList, context);

            holder.postImage.addView(imagesRecyclerView);
//            holder.postImage.setLayoutParams(layoutParam);
            // video view
        } else if (artifactItemWrapper.getMediaType() == TYPE_VIDEO) {
            ImageView iv = getVideoThumbnail(750,
                    750, mediaList.get(0), context);

            ImageView playIcon = getVideoPlayIcon(context);

            // set frame's layout and add image view to it programmatically
            holder.postImage.addView(iv);
            holder.postImage.addView(playIcon);
//            holder.postImage.setLayoutParams(layoutParam);
        } else {
            Log.e(TAG, "unknown media type !!!");
        }
    }

    @Override
    public int getItemCount() {
        if (artifactItemWrapper == null) {
            return 0;
        }
        return 1;
    }

    public void setData(ArtifactItemWrapper newData) {
        artifactItemWrapper = newData;
        notifyDataSetChanged();
    }


    // *************************************** getter & setters ***********************************
    public void addData(ArtifactItemWrapper artifactItemWrapper) {
        // 0 to add data at start
        this.artifactItemWrapper = artifactItemWrapper;
        notifyDataSetChanged();
    }
}