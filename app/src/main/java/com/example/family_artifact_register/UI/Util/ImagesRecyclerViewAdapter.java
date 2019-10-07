package com.example.family_artifact_register.UI.Util;

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

import com.example.family_artifact_register.R;

import java.io.File;
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
//            is = context.getContentResolver().openInputStream(imageUri);
//            System.out.println("input stream finished");
////            assert is != null;
////            is.reset();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Bitmap bitmap = BitmapFactory.decodeFile((new File(imageUri.toString())).getPath());
//        Bitmap bitmap = null;
//        try {
//            bitmap = BitmapFactory.decodeStream(
//                    is,
//                    null,
//                    getCompressImageOption(context, imageUri));
//            System.out.println("bitmap finished");
////            assert is != null;
////            is.reset();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), images.get(position));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        holder.imageView.setImageBitmap(bitmap);
        System.out.println("set bitmap finished");
        holder.imageView.setLayoutParams(layoutParams);

//        String stringUri = images.get(position).getPath(); //works
//        holder.imageView.setImageURI(Uri.parse(stringUri));
//        holder.imageView.setImageURI(images.get(position));
//        Picasso.with(context).load(images.get(position)).into(holder.imageView);
        holder.imageView.setOnClickListener(view -> {
            System.out.println("#"+position+" clicked!!!!");
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
