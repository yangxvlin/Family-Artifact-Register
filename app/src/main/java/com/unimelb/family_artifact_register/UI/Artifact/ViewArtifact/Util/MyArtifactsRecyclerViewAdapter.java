package com.unimelb.family_artifact_register.UI.Artifact.ViewArtifact.Util;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getVideoPlayIcon;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getVideoThumbnail;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.setImagesViewPager;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-21 15:57:25
 * @description recycler view for artifact item
 * Deprecated because no longer use ViewMediaFragment
 * Not deleted because open-close principle
 */
@Deprecated
public class MyArtifactsRecyclerViewAdapter extends RecyclerView.Adapter<MyArtifactsRecyclerViewHolder> {

    private static final String TAG = MyArtifactsRecyclerViewAdapter.class.getSimpleName();

    private List<ArtifactItemWrapper> artifactItemWrapperList;

    private Context context;

    private List<Uri> mediaList;

    public MyArtifactsRecyclerViewAdapter(Context context) {
        this.artifactItemWrapperList = new ArrayList<>();
        this.context = context;
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
}
