package com.example.family_artifact_register.UI.Util;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

import java.util.ArrayList;
import java.util.List;

/**
 * a recycler view adapter for displaying multiple images with customized image size;
 */
public class ImagesRecyclerViewAdapter extends RecyclerView.Adapter<ImagesRecyclerViewHolder> {

    private int imageWidth;

    private int imageHeight;

    private List<Uri> images;

    private Context context;

    public ImagesRecyclerViewAdapter(int height, int width, Context context) {
        images = new ArrayList<>();
        this.imageHeight = height;
        this.imageWidth = width;
        this.context = context;
    }

    @NonNull
    @Override
    public ImagesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext())
                                            .inflate(R.layout.item_my_artifact_image,
                                                    parent,
                                                    false);
        return new ImagesRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesRecyclerViewHolder holder, int position) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                getImageWidth(),
                getImageHeight()
        );
        System.out.println("image uri string: " + images.get(position).toString());
        System.out.println("image uri path:   " + images.get(position).getPath());

        Uri imageUri = images.get(position);

//        InputStream is = null;
//        try {
//
//            is = context.getContentResolver().openInputStream(imageUri);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Bitmap bitmap = null;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        // only access image's size info, not read whole image into memory so that won't memory leak
//        options.inJustDecodeBounds = false;
//        bitmap = BitmapFactory.decodeStream(
//                is,
//                null,
//                options);

        // System.out.println(" start decode ");
        Bitmap bitmap = BitmapFactory.decodeFile(imageUri.getPath());
        // System.out.println("bitmap: " + bitmap.toString());
        // System.out.println(" finish decode ");
        holder.imageView.setImageBitmap(bitmap);
        // System.out.println("set bitmap finished");
        holder.imageView.setLayoutParams(layoutParams);
        holder.imageView.setOnClickListener(view -> {
            // whole screen dialog of image
            Dialog dia = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//            dia.setContentView(R.layout.start_image_dialog);

            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(
                new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT
                )
            );
            imageView.setImageBitmap(bitmap);
            // click to return
            imageView.setOnClickListener(v -> {
                dia.dismiss();
            });
            dia.setContentView(imageView);
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

    @Override
    public int getItemCount() { return images.size(); }

    public void addData(Uri image) {
        this.images.add(image);
        notifyDataSetChanged();
    }

    public int getImageWidth() { return imageWidth; }

    public int getImageHeight() { return imageHeight; }
}
