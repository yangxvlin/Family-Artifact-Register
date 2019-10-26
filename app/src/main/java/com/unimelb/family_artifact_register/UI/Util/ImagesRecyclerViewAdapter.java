package com.unimelb.family_artifact_register.UI.Util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.R;

import java.util.ArrayList;
import java.util.List;

import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.setImageOnClickOpenDialogListener;

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
        View view = LayoutInflater.from(parent.getContext())
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
        setImageOnClickOpenDialogListener(holder.imageView, images.get(position), context);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void addData(Uri image) {
        this.images.add(image);
        notifyDataSetChanged();
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }
}
