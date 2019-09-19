package com.example.family_artifact_register.UI.ArtifactHub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<Model> models;

    public MyAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }

    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_row, null);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull final MyHolder myHolder, int i) {
        myHolder.mTitle.setText(models.get(i).getTitle());
        myHolder.mDes.setText(models.get(i).getDescription());
        myHolder.mImeaView.setImageResource(models.get(i).getImg());
        myHolder.mAvatar.setImageResource(models.get(i).getAvatar());
        myHolder.mUsername.setText(models.get(i).getUsername());

        // The ClickListener checks which model is clicked and create intent
        myHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnItemClickListener(View v, int Position) {
                String gTitle = models.get(Position).getTitle();
                String gDesc = models.get(Position).getDescription();
                String gUser = models.get(Position).getUsername();
                BitmapDrawable bitmapDrawable = (BitmapDrawable)myHolder.mImeaView.getDrawable();

                Bitmap bitmap = bitmapDrawable.getBitmap();

                // Compress the bitmap as stream convert to array of bytes
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
                byte[] bytes = stream.toByteArray();

                //Creat intent and put information into it
                Intent intent = new Intent(c, ArtifactDetailActivity.class);
                intent.putExtra("iTitle", gTitle);
                intent.putExtra("iDesc", gDesc);
                intent.putExtra("iUser", gUser);
                intent.putExtra("iImage", bytes);
                c.startActivity(intent);
            }
        });

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
        return models.size();
    }

}
