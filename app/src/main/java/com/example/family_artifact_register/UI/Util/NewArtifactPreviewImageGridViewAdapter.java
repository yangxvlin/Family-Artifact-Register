package com.example.family_artifact_register.UI.Util;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.family_artifact_register.R;

import java.util.List;

public class NewArtifactPreviewImageGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Uri> images;

    public NewArtifactPreviewImageGridViewAdapter(Context context, List<Uri> data) {
        mContext = context;
        images = data;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return images.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_grid_view_new_artifact_preview_image, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = view.findViewById(R.id.item_new_artifact_preview_image_grid_view_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Uri image = images.get(i);
        viewHolder.imageView.setImageURI(image);

        return view;
    }

    public void addImage(Uri uri) {
        notifyDataSetChanged();
    }

    private class ViewHolder {
        ImageView imageView;
    }
}
