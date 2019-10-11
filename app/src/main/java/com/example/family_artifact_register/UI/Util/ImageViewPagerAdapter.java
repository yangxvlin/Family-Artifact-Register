package com.example.family_artifact_register.UI.Util;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.example.family_artifact_register.R;

import java.util.List;

public class ImageViewPagerAdapter extends LoopingPagerAdapter<Uri> {

    private static final int VIEW_TYPE_NORMAL = 100;
    private static final int VIEW_TYPE_SPECIAL = 101;

    private List<Uri> itemList;
    private int imageWidth;
    private int imageHeight;

    public ImageViewPagerAdapter(Context context, List<Uri> itemList, boolean isInfinite, int imageWidth, int imageHeight) {
        super(context, itemList, isInfinite);
        this.itemList = itemList;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    @Override
    protected int getItemViewType(int listPosition) {
        if (itemList.get(listPosition) == null) return VIEW_TYPE_SPECIAL;
        return VIEW_TYPE_NORMAL;
    }

    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {
        // if (viewType == VIEW_TYPE_SPECIAL) return LayoutInflater.from(context).inflate(R.layout.item_my_artifact_image, container, false);
        return LayoutInflater.from(context).inflate(R.layout.item_my_artifact_image, container, false);
    }

    @Override
    protected void bindView(View convertView, int listPosition, int viewType) {
        ImageView image = convertView.findViewById(R.id.item_image);
        image.setImageURI(itemList.get(listPosition));
        image.setMaxWidth(imageWidth);
        image.setMaxHeight(imageHeight);
        image.setAdjustViewBounds(true);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
}
