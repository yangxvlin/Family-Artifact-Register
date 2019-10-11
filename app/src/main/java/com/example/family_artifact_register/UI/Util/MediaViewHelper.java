package com.example.family_artifact_register.UI.Util;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
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
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.Util.FileHelper;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.List;

public class MediaViewHelper {
    public static final String TAG = MediaViewHelper.class.getSimpleName();

    public static void setImagesViewPager(List<Uri> images, Context context, UltraViewPager ultraViewPager, Boolean infiniteLoop) {
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        //initialize ImageUltraPagerAdapter，and add child view to UltraViewPager
        PagerAdapter adapter = new ImageUltraPagerAdapter(images, context);
        ultraViewPager.setAdapter(adapter);

        //initialize built-in indicator
        ultraViewPager.initIndicator();
        //set style of indicators
        ultraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(context.getColor(R.color.primaryColor))
                .setNormalColor(context.getColor(R.color.wechat_grey))
                .setRadius((int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        images.size(),
                        context.getResources().getDisplayMetrics())
                );
        //set the alignment
        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        //construct built-in indicator, and add it to  UltraViewPager
        ultraViewPager.getIndicator().build();

        //set an infinite loop
        ultraViewPager.setInfiniteLoop(infiniteLoop);
        //enable auto-scroll mode
        // ultraViewPager.setAutoScroll(2000);
    }

//    public static SliderLayout getImagesSliderView(int imageWidth, int imageHeight, List<Uri> images, Context context) {
//        SliderLayout imagesSliderLayout = new SliderLayout(context);
//
//        for (Uri image: images) {
//            DefaultSliderView slider = new DefaultSliderView(context);
//            slider.image(image.toString())
//                    .setScaleType(DefaultSliderView.ScaleType.Fit)
//                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
//                        @Override
//                        public void onSliderClick(BaseSliderView slider) {
//                            //
//                        }
//                    });
//            imagesSliderLayout.addSlider(slider);
//        }
//
//        imagesSliderLayout.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageHeight));
//        imagesSliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
//        imagesSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//        imagesSliderLayout.setCustomAnimation(new DescriptionAnimation());
//        imagesSliderLayout.setDuration(4000);
//        imagesSliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                //
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                //
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                //
//            }
//        });
//
//
//        return imagesSliderLayout;
//    }

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

    public static ImageView getVideoThumbnail(Uri video, Context context) {
        Uri videoUri = FileHelper.getInstance().checkAddScheme(video);

        // set up image view layout
        ImageView iv = new ImageView(context);
        iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        // image cropped in center to ge square
        iv.setAdjustViewBounds(true);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // set thumbnail from video to image
        Glide.with(context)
                .load(videoUri) // or URI/path
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
                videoView.setVideoURI(videoUri);
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

    public static ImageView getVideoThumbnail(int thumbnailWidth, int thumbnailHeight, Uri video, Context context) {
        Uri videoUri = FileHelper.getInstance().checkAddScheme(video);

        // set up image view layout
        ImageView iv = new ImageView(context);
        iv.setLayoutParams(new LinearLayout.LayoutParams(thumbnailWidth, thumbnailHeight));
        // image cropped in center to ge square
        iv.setAdjustViewBounds(true);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // set thumbnail from video to image
        Glide.with(context)
                .load(videoUri) // or URI/path
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
                videoView.setVideoURI(videoUri);
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

    public static void setImageOnClickOpenDialogListener(View imageView, Uri image, Context context) {
        imageView.setOnClickListener(view -> {
            // whole screen dialog of image
            Dialog dia = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            ImageView iv = new ImageView(context);
            iv.setLayoutParams(
                    new ActionBar.LayoutParams(
                            ActionBar.LayoutParams.MATCH_PARENT,
                            ActionBar.LayoutParams.MATCH_PARENT
                    )
            );
            iv.setImageURI(image);
            // click to return
            iv.setOnClickListener(v -> {
                dia.dismiss();
            });
            dia.setContentView(iv);
//            dia.setContentView(imageView);
//            imageView.setBackgroundResource(R.mipmap.iv_android);
            dia.show();

            dia.setCanceledOnTouchOutside(true); // Sets whether this dialog is
            Window w = dia.getWindow();
            WindowManager.LayoutParams lp = w.getAttributes();
            lp.x = 0;
            lp.y = 40;
            dia.onWindowAttributesChanged(lp);
        });
    }
}
