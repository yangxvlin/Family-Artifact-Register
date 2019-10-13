package com.example.family_artifact_register.UI.ArtifactDetail;//package com.example.family_artifact_register.UI.ArtifactDetail;
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
//import com.example.family_artifact_register.R;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
//import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;
//import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getImageRecyclerView;
//import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getVideoPlayIcon;
//import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getVideoThumbnail;
//
//public class DetailImageAdapter extends RecyclerView.Adapter<DetailImageViewHolder>{
//
//    private static final String TAG = DetailImageAdapter.class.getSimpleName();
//    private ArtifactItemWrapper artifactItemWrapper;
//
////    private FragmentManager fm;
//
//    private Context context;
//
//    private List<Uri> mediaList;
//
//    public DetailImageAdapter(Context context) {
//        this.context = context;
//
////        this.fm = fm;
//    }
//
//    @NonNull
//    @Override
//    public DetailImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_image, parent, false);
//        return new DetailImageViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull DetailImageViewHolder holder, int position) {
//
//        mediaList = new ArrayList<>();
//        for (String mediaUrl: artifactItemWrapper.getLocalMediaDataUrls()) {
//            Log.d(TAG, "media uri" + mediaUrl);
//            mediaList.add(Uri.parse(mediaUrl));
//        }
//
//        holder.clearFrame();
//        // set frame layout param
//        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT
//        );
//        layoutParam.gravity = Gravity.CENTER;
//        layoutParam.topMargin = 20;
//        layoutParam.bottomMargin = 20;
//        layoutParam.leftMargin = 20;
//        layoutParam.rightMargin = 20;
//
//        // image view
//        if (artifactItemWrapper.getMediaType() == TYPE_IMAGE) {
//
//            View imagesRecyclerView = getImageRecyclerView(500, 500, mediaList, context);
//
//            holder.postImage.addView(imagesRecyclerView);
//            holder.postImage.setLayoutParams(layoutParam);
//            // video view
//        } else if (artifactItemWrapper.getMediaType() == TYPE_VIDEO) {
//            ImageView iv = getVideoThumbnail(750, 750, mediaList.get(0), context);
//
//            ImageView playIcon = getVideoPlayIcon(context);
//
//            // set frame's layout and add image view to it programmatically
//            holder.postImage.addView(iv);
//            holder.postImage.addView(playIcon);
//            holder.postImage.setLayoutParams(layoutParam);
//        } else {
//            Log.e(TAG, "unknown media type !!!");
//        }
//
////        List<Uri> mediaList = new ArrayList<>();
////        for (String mediaUrl: artifactItem.getMediaDataUrls()) {
////            Log.d(TAG, "media uri" + mediaUrl);
////            mediaList.add(Uri.parse(mediaUrl));
////        }
////
////        // image view
////        if (artifactItem.getMediaType() == TYPE_IMAGE) {
////            // recycler view adapter for display images
////            ImagesRecyclerViewAdapter imagesRecyclerViewAdapter;
////            // recycler view for display images
////            RecyclerView imageRecyclerView;
////
////            // set frame layout param
////            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
////                    FrameLayout.LayoutParams.MATCH_PARENT,
////                    FrameLayout.LayoutParams.WRAP_CONTENT
////            );
////            layoutParam.gravity = Gravity.CENTER;
////
////            // set recycler view images
////            RecyclerView.LayoutParams recyclerViewParam = new RecyclerView.LayoutParams(
////                    RecyclerView.LayoutParams.MATCH_PARENT,
////                    RecyclerView.LayoutParams.WRAP_CONTENT
////            );
////            imageRecyclerView = new RecyclerView(context);
////            imageRecyclerView.setLayoutParams(recyclerViewParam);
////
////            // images horizontally
////            LinearLayoutManager layoutManager = new LinearLayoutManager(
////                    context,
////                    LinearLayoutManager.HORIZONTAL,
////                    false
////            );
////            imageRecyclerView.setLayoutManager(layoutManager);
////
////            // image adapter
////            imagesRecyclerViewAdapter = new ImagesRecyclerViewAdapter(
////                    200,
////                    200,
////                    context
////            );
////            for (Uri image: mediaList) {
////                imagesRecyclerViewAdapter.addData(image);
////            }
////            imageRecyclerView.setAdapter(imagesRecyclerViewAdapter);
////            holder.postImage.setLayoutParams(layoutParam);
////            holder.postImage.addView(imageRecyclerView);
////            // video view
////        } else if (artifactItem.getMediaType() == TYPE_VIDEO) {
////            // set frame layout param
////            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
////                    FrameLayout.LayoutParams.MATCH_PARENT,
////                    ViewGroup.LayoutParams.WRAP_CONTENT
////            );
////            layoutParam.gravity = Gravity.CENTER;
////
////            // set media
////            VideoView mediaView = new VideoView(context);
////            mediaView.setLayoutParams(new FrameLayout.LayoutParams(
////                    800,
////                    1200)
////            );
////            mediaView.setVideoURI(mediaList.get(0));
////            mediaView.setMediaController(new MediaController(context));
////            mediaView.start();
////            mediaView.requestFocus();
////            mediaView.setOnCompletionListener(mp -> {
////                Log.d(TAG, "Video play finish.");
////            });
////            mediaView.setOnErrorListener((mp, what, extra) -> {
////                Log.d(TAG, "Video play error!!!");
////                return false;
////            });
////
////            holder.postImage.setLayoutParams(layoutParam);
////            holder.postImage.addView(mediaView);
////        } else {
////            Log.e(TAG, "unknown media type !!!");
////        }
//    }
//
//    @Override
//    public int getItemCount() {
//        if (artifactItemWrapper == null) {
//            return 0;
//        }
//        return 1;
//    }
//
//    public void setData(ArtifactItemWrapper newData) {
//        artifactItemWrapper = newData;
//        notifyDataSetChanged();
//    }
//
//    // *************************************** getter & setters ***********************************
//    public void addData(ArtifactItemWrapper artifactItemWrapper) {
//        // 0 to add data at start
//        this.artifactItemWrapper = artifactItemWrapper;
//        notifyDataSetChanged();
//    }
//
//}

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
import com.example.family_artifact_register.UI.ArtifactDetail.ArtifactDetailActivity;
import com.example.family_artifact_register.UI.ArtifactDetail.DetailImageViewHolder;
import com.example.family_artifact_register.UI.ArtifactHub.HubModelViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getImageRecyclerView;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getVideoPlayIcon;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getVideoThumbnail;

public class DetailImageAdapter extends RecyclerView.Adapter<DetailImageViewHolder> {

    private static final String TAG = DetailImageAdapter.class.getSimpleName();
    private ArtifactItemWrapper artifactItemWrapper;

//    private FragmentManager fm;

    private Context context;

    private List<Uri> mediaList;

    public DetailImageAdapter(Context context) {
        this.context = context;
//        this.fm = fm;
    }

    @NonNull
    @Override
    public DetailImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_detail, parent, false);
        return new DetailImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailImageViewHolder holder, int position) {
//        ArtifactItemWrapper artifactItemWrapper = artifactItemWrapper.get(position);

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
    }



    // *************************************** getter & setters ***********************************
    public void addData(ArtifactItemWrapper artifactItemWrapper) {
        // 0 to add data at start
        this.artifactItemWrapper = artifactItemWrapper;
        notifyDataSetChanged();
    }
}

