package com.unimelb.family_artifact_register.UI.ArtifactDetail;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.R;

import java.util.ArrayList;
import java.util.List;

import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getImageRecyclerView;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getVideoPlayIcon;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getVideoThumbnail;

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
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_artifact_detail, parent, false);
        return new DetailImageViewHolder(view);
    }

    //TODO: add map images after post images
    @Override
    public void onBindViewHolder(@NonNull DetailImageViewHolder holder, int position) {
//        ArtifactItemWrapper artifactItemWrapper = artifactItemWrapper.get(position);

        mediaList = new ArrayList<>();
        for (String mediaUrl: artifactItemWrapper.getLocalMediaDataUrls()) {
            Log.d(TAG, "media uri" + mediaUrl);
            mediaList.add(Uri.parse(mediaUrl));
        }

//        holder.clearFrame();
//        // set frame layout param
//        GridLayout.LayoutParams layoutParam = new GridLayout.LayoutParams(
//                GridLayout.Spec 3,
//                GridLayout.Spec
//        );
//        layoutParam.gravity = Gravity.CENTER;
//        layoutParam.topMargin = 20;
//        layoutParam.bottomMargin = 20;
//        layoutParam.leftMargin = 20;
//        layoutParam.rightMargin = 20;
//
        // image view
        if (artifactItemWrapper.getMediaType() == TYPE_IMAGE) {

            View imagesRecyclerView = getImageRecyclerView(500, 500, mediaList, context);

            holder.postImage.addView(imagesRecyclerView);
//            holder.postImage.setLayoutParams(layoutParam);
            // video view
        } else if (artifactItemWrapper.getMediaType() == TYPE_VIDEO) {
            ImageView iv = getVideoThumbnail(750, 750, mediaList.get(0), context);

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
