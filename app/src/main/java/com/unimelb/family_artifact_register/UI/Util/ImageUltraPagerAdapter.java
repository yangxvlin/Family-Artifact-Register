package com.unimelb.family_artifact_register.UI.Util;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.unimelb.family_artifact_register.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.setImageOnClickOpenDialogSliderListener;

/**
 * required adapter for pager adapter
 */
public class ImageUltraPagerAdapter extends PagerAdapter {
    private List<Uri> images;
    private Context context;

    public ImageUltraPagerAdapter(List<Uri> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NotNull View view, @NotNull Object object) {
        return view == object;
    }

    @NotNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.item_my_artifact_image, null);
        //new LinearLayout(container.getContext());
        ImageView imageView = linearLayout.findViewById(R.id.item_image);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setImageURI(images.get(position));
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        setImageOnClickOpenDialogSliderListener(imageView, images, context, position);
        container.addView(linearLayout);
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout view = (LinearLayout) object;
        container.removeView(view);
    }
}
