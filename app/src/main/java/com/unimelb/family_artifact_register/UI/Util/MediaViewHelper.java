package com.unimelb.family_artifact_register.UI.Util;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
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
import com.kodmap.app.library.PopopDialogBuilder;
import com.tmall.ultraviewpager.UltraViewPager;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.Util.FileHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @description pure fabricate media view creation or set logic
 */
public class MediaViewHelper {
    /**
     * class tag
     */
    public static final String TAG = MediaViewHelper.class.getSimpleName();

    /**
     * @param images         list of images
     * @param context        context
     * @param ultraViewPager ultraViewPager
     * @param infiniteLoop   can go back to begin if at the end
     */
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
                        TypedValue.COMPLEX_RADIX_0p23,
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

    /**
     * @param imageWidth  image item width
     * @param imageHeight image item height
     * @param images      list of images
     * @param context     context
     * @return dynamically created recycler view
     */
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
        for (Uri image : images) {
            imagesRecyclerViewAdapter.addData(image);
        }
        imageRecyclerView.setAdapter(imagesRecyclerViewAdapter);

        return imageRecyclerView;
    }

    /**
     * @param video video address
     * @return thumbnail of video
     */
    public static Bitmap getVideoThumbNail(String video) {
        Bitmap thumb;
        //MINI_KIND, size:  512 x 384 thumbnail
        thumb = ThumbnailUtils.createVideoThumbnail(video, MediaStore.Video.Thumbnails.MICRO_KIND);
        return thumb;
    }

    /**
     * @param video   video address
     * @param context context
     * @return dynamically created thumbnail image view of video
     */
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

    /**
     * @param thumbnailWidth  image width
     * @param thumbnailHeight image height
     * @param video           video address
     * @param context         context context
     * @return dynamically created thumbnail image view of video
     */
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

    /**
     * @param context context
     * @return dynamically created play icon for video thumbnail
     */
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

    /**
     * click image and open a dialog to see whole image
     *
     * @param imageView image view
     * @param image     image address
     * @param context   context
     */
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
            dia.show();

            dia.setCanceledOnTouchOutside(true); // Sets whether this dialog is
            Window w = dia.getWindow();
            WindowManager.LayoutParams lp = w.getAttributes();
            lp.x = 0;
            lp.y = 40;
            dia.onWindowAttributesChanged(lp);
        });
    }

    /**
     * when image clicked start a dialog with images of view pager and start at selected index
     *
     * @param imageView     image view
     * @param images        list of images
     * @param context       context
     * @param selectedIndex index of selected image
     */
    public static void setImageOnClickOpenDialogSliderListener(View imageView, List<Uri> images, Context context, int selectedIndex) {
        List<String> imagesUriString = new ArrayList<>();
        for (Uri image : images) {
            imagesUriString.add("file:/" + image.toString());
        }

        imageView.setOnClickListener(view -> {
            Dialog dialog = new PopopDialogBuilder(context)
                    // Set list like as option1 or option2 or option3
                    .setList(imagesUriString, selectedIndex)
                    // or setList with initial position that like .setList(list,position)
                    // Set dialog header color
                    .setHeaderBackgroundColor(R.color.color_dialog_bg)
//                    .setHeaderBackgroundColor(R.color.primaryColor)
                    // Set dialog background color
                    .setDialogBackgroundColor(R.color.color_dialog_bg)
                    // Set close icon drawable
                    .setCloseDrawable(R.drawable.ic_close_white_24dp)
                    // Set loading view for pager image and preview image
                    .setLoadingView(R.layout.loading_view)
                    // Set dialog style
                    .setDialogStyle(R.style.DialogStyle)
                    // Choose selector type, indicator or thumbnail
                    .showThumbSlider(true)
                    // Set image scale type for slider image
                    .setSliderImageScaleType(ImageView.ScaleType.FIT_CENTER)
                    // Set indicator drawable
                    .setSelectorIndicator(R.drawable.sample_indicator_selector)
                    // Enable or disable zoomable
                    .setIsZoomable(true)
                    // Build Km Slider Popup Dialog
                    .build();

            dialog.show();
        });
    }
}
