package com.example.family_artifact_register.UI.ArtifactHub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * @author Haichao Song 854035,
 * @time 2019-9-18 14:28:43
 * @description Adapter for models recycler view
 */
public class HubModelAdapter extends RecyclerView.Adapter {

//    public final static String TAG = HubModelAdapter.class.getSimpleName();

    private ArrayList<String> dataSet;

//    Context c;
//    ArrayList<Model> models;

//    public HubModelAdapter(Context c, ArrayList<Model> models) {
//        this.c = c;
//        this.models = models;
//    }

    @NotNull
    @Override
    public HubModelHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item, null);

        return new HubModelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,int i) {
        ((HubModelViewHolder) holder).textView.setText(dataSet.get(i));

//        myHolder.mDes.setText(models.get(i).getDescription());
//        myHolder.mImeaView.setImageResource(models.get(i).getPostimage());
//        myHolder.mAvatar.setImageResource(models.get(i).getAvatar());
//        myHolder.mUsername.setText(models.get(i).getUsername());
//        myHolder.mPublisher.setText(models.get(i).getPublisher());
//
//        // The ClickListener checks which model is clicked and create intent
//        myHolder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void OnItemClickListener(View v, int Position) {
//                String gDesc = models.get(Position).getDescription();
//                String gUser = models.get(Position).getUsername();
//                String gPublisher = models.get(Position).getPublisher();
//                BitmapDrawable bitmapDrawableImage = (BitmapDrawable)myHolder.mImeaView.getDrawable();
//                BitmapDrawable bitmapDrawableAvatar = (BitmapDrawable)myHolder.mAvatar.getDrawable();
//
//
//                Bitmap bitmapImage = bitmapDrawableImage.getBitmap();
//                Bitmap bitmapAvatar = bitmapDrawableAvatar.getBitmap();
//
//                // Compress the bitmap as stream convert to array of bytes
//                ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
//                ByteArrayOutputStream avatarStream = new ByteArrayOutputStream();
//                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100,imageStream);
//                bitmapAvatar.compress(Bitmap.CompressFormat.PNG, 100,avatarStream);
//
//                byte[] imageBytes = imageStream.toByteArray();
//                byte[] avatarBytes = avatarStream.toByteArray();
//
//                //Creat intent and put information into it
//                Intent intent = new Intent(c, ArtifactDetailActivity.class);
//                intent.putExtra("iTitle", gPublisher);
//                intent.putExtra("iDesc", gDesc);
//                intent.putExtra("iUser", gUser);
//                intent.putExtra("iImage", imageBytes);
//                intent.putExtra("iAvatar", avatarBytes);
//                c.startActivity(intent);
//            }
//        });
//
//        myHolder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void OnItemClickListener(View v, int Position) {
//                if (models.get(Position).getTitle().equals("This is Art1")){
//
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
