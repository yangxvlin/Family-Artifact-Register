package com.example.family_artifact_register.UI.ArtifactDetail;

import android.content.Context;
import android.media.Image;
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
import com.example.family_artifact_register.UI.ArtifactHub.HubModelViewHolder;
import com.example.family_artifact_register.UI.Util.ImagesRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;

public class DetailImageAdapter extends RecyclerView.Adapter<DetailImageViewHolder>{

    private static final String TAG = com.example.family_artifact_register.UI.Util.
            MyArtifactsRecyclerViewAdapter.class.getSimpleName();
//    private ArtifactItem arti
    private List<String> postImages;

//    private FragmentManager fm;

    private Context context;

    public DetailImageAdapter(Context context) {
        this.context = context;
//        this.fm = fm;
    }

    @NonNull
    @Override
    public DetailImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_image, parent, false);
        return new DetailImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailImageViewHolder holder, int position) {

        String url = postImages.get(position);
        if(url != null) {
            holder.postImage.setImageURI(Uri.parse(url));
        }

//        List<Uri> mediaList = new ArrayList<>();
//        for (String mediaUrl: artifactItem.getMediaDataUrls()) {
//            Log.d(TAG, "media uri" + mediaUrl);
//            mediaList.add(Uri.parse(mediaUrl));
//        }
//
//        // image view
//        if (artifactItem.getMediaType() == TYPE_IMAGE) {
//            // recycler view adapter for display images
//            ImagesRecyclerViewAdapter imagesRecyclerViewAdapter;
//            // recycler view for display images
//            RecyclerView imageRecyclerView;
//
//            // set frame layout param
//            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
//                    FrameLayout.LayoutParams.MATCH_PARENT,
//                    FrameLayout.LayoutParams.WRAP_CONTENT
//            );
//            layoutParam.gravity = Gravity.CENTER;
//
//            // set recycler view images
//            RecyclerView.LayoutParams recyclerViewParam = new RecyclerView.LayoutParams(
//                    RecyclerView.LayoutParams.MATCH_PARENT,
//                    RecyclerView.LayoutParams.WRAP_CONTENT
//            );
//            imageRecyclerView = new RecyclerView(context);
//            imageRecyclerView.setLayoutParams(recyclerViewParam);
//
//            // images horizontally
//            LinearLayoutManager layoutManager = new LinearLayoutManager(
//                    context,
//                    LinearLayoutManager.HORIZONTAL,
//                    false
//            );
//            imageRecyclerView.setLayoutManager(layoutManager);
//
//            // image adapter
//            imagesRecyclerViewAdapter = new ImagesRecyclerViewAdapter(
//                    200,
//                    200,
//                    context
//            );
//            for (Uri image: mediaList) {
//                imagesRecyclerViewAdapter.addData(image);
//            }
//            imageRecyclerView.setAdapter(imagesRecyclerViewAdapter);
//            holder.postImage.setLayoutParams(layoutParam);
//            holder.postImage.addView(imageRecyclerView);
//            // video view
//        } else if (artifactItem.getMediaType() == TYPE_VIDEO) {
//            // set frame layout param
//            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
//                    FrameLayout.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT
//            );
//            layoutParam.gravity = Gravity.CENTER;
//
//            // set media
//            VideoView mediaView = new VideoView(context);
//            mediaView.setLayoutParams(new FrameLayout.LayoutParams(
//                    800,
//                    1200)
//            );
//            mediaView.setVideoURI(mediaList.get(0));
//            mediaView.setMediaController(new MediaController(context));
//            mediaView.start();
//            mediaView.requestFocus();
//            mediaView.setOnCompletionListener(mp -> {
//                Log.d(TAG, "Video play finish.");
//            });
//            mediaView.setOnErrorListener((mp, what, extra) -> {
//                Log.d(TAG, "Video play error!!!");
//                return false;
//            });
//
//            holder.postImage.setLayoutParams(layoutParam);
//            holder.postImage.addView(mediaView);
//        } else {
//            Log.e(TAG, "unknown media type !!!");
//        }
    }

    @Override
    public int getItemCount() {
        if(postImages != null) {
            return postImages.size();
        }
        return 0;
    }

    public void setData(List<String> newData) {
        postImages = newData;
        Log.d(TAG,"Post New Data: " + newData);
        notifyDataSetChanged();
    }

}
