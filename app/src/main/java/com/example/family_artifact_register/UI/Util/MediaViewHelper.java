package com.example.family_artifact_register.UI.Util;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.family_artifact_register.R;

import java.util.List;

public class MediaViewHelper {
    public static final String TAG = MediaViewHelper.class.getSimpleName();

    public static RecyclerView getImageRecyclerView(int imageWidth, int imageHeight, List<Uri> images, Context context) {
        // recycler view adapter for display images
        ImagesRecyclerViewAdapter imagesRecyclerViewAdapter;
        // recycler view for display images
        RecyclerView imageRecyclerView;

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

        // set the divider between list item
        DividerItemDecoration divider = new DividerItemDecoration(
                imageRecyclerView.getContext(),
                layoutManager.getOrientation()
        );
        divider.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider_wechat_white));
        imageRecyclerView.addItemDecoration(divider);

        // image adapter
        imagesRecyclerViewAdapter = new ImagesRecyclerViewAdapter(
                imageHeight,
                imageWidth,
                context
        );
        for (Uri image: images) {
            imagesRecyclerViewAdapter.addData(image);
        }
        imageRecyclerView.setAdapter(imagesRecyclerViewAdapter);

        return imageRecyclerView;
    }

    public static ImageView getVideoThumbnail(int thumbnailWidth, int thumbnailHeight, Uri video, Context context) {
        // set up image view layout
        ImageView iv = new ImageView(context);
        iv.setLayoutParams(new LinearLayout.LayoutParams(thumbnailWidth, thumbnailHeight));
        // image cropped in center to ge square
        iv.setAdjustViewBounds(true);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // set thumbnail from video to image
        Glide.with(context)
                .load(video) // or URI/path
                .into(iv); //imageview to set thumbnail to
        // start video when clicked the thumbnail
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // whole screen dialog of image
                Dialog dia = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

                VideoView videoView = new VideoView(context);
                videoView.setLayoutParams(
                        new ActionBar.LayoutParams(
                                ActionBar.LayoutParams.MATCH_PARENT,
                                ActionBar.LayoutParams.MATCH_PARENT
                        )
                );
                videoView.setVideoURI(video);
                videoView.setMediaController(new MediaController(context));
                videoView.start();
                videoView.requestFocus();
                videoView.setOnCompletionListener(mp -> {
                    Log.d(TAG, "Video play finish.");
                });
                videoView.setOnErrorListener((mp, what, extra) -> {
                    Log.d(TAG, "Video play error!!!");
                    return false;
                });
                // click to return
                videoView.setOnClickListener(v -> {
                    dia.dismiss();
                });
                dia.setContentView(videoView);
                dia.show();

                dia.setCanceledOnTouchOutside(true); // Sets whether this dialog is
                Window w = dia.getWindow();
                WindowManager.LayoutParams lp = w.getAttributes();
                lp.x = 0;
                lp.y = 40;
                dia.onWindowAttributesChanged(lp);
            }
        });

        return iv;
    }

    public static ImageView getVideoPlayIcon(Context context) {
        // add a play icon to the thumbnail
        ImageView playIcon = new ImageView(context);
        LinearLayout.LayoutParams playIconLayout = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        playIconLayout.gravity = Gravity.CENTER;
        playIcon.setLayoutParams(playIconLayout);
        playIcon.setImageResource(R.drawable.ic_play_circle_filled_white);

        return playIcon;
    }
}
