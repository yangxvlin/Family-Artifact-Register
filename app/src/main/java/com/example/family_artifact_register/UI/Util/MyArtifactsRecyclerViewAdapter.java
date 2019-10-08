package com.example.family_artifact_register.UI.Util;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-21 15:57:25
 * @description
 */
public class MyArtifactsRecyclerViewAdapter extends RecyclerView.Adapter<MyArtifactsRecyclerViewHolder> {

    private static final String TAG = MyArtifactsRecyclerViewAdapter.class.getSimpleName();
    private List<ArtifactItem> artifactItemList;

//    private FragmentManager fm;

    private Context context;

    public MyArtifactsRecyclerViewAdapter(Context context) {
        this.artifactItemList = new ArrayList<>();
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
        ArtifactItem artifactItem = artifactItemList.get(position);

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
            layoutParam.topMargin = 20;

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
            holder.frame.setLayoutParams(layoutParam);
            holder.frame.addView(imageRecyclerView);
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

            holder.frame.setLayoutParams(layoutParam);
            holder.frame.addView(mediaView);
        } else {
            Log.e(TAG, "unknown media type !!!");
        }
    }

    @Override
    public int getItemCount() { return artifactItemList.size(); }

    public void setData(List<ArtifactItem> newData) {
        Collections.sort(newData, new Comparator<ArtifactItem>() {
            @Override
            public int compare(ArtifactItem artifactItem, ArtifactItem t1) {
                return -1 * artifactItem.getLastUpdateDateTime().compareTo(t1.getLastUpdateDateTime());
            }
        });
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
