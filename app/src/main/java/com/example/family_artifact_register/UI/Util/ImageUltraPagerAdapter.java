package com.example.family_artifact_register.UI.Util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.example.family_artifact_register.R;
import com.tmall.ultraviewpager.UltraViewPager;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getImagesViewPager;

public class ImageUltraPagerAdapter extends PagerAdapter {
    private List<Uri> images;
//    private int imageWidth;
//    private int imageHeight;
    private Context context;

    public ImageUltraPagerAdapter(List<Uri> images, Context context) {
        this.images = images;
        this.context = context;
//        this.imageWidth = imageWidth;
//        this.imageHeight = imageHeight;
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
        imageView.setOnClickListener(view -> {
            // whole screen dialog of image
            Dialog dia = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dia.setContentView(R.layout.whole_screen_image_view_pager);
            UltraViewPager diaUltraViewPager = dia.findViewById(R.id.whole_screen_ultra_viewpager);

            getImagesViewPager(images, context, diaUltraViewPager, false);
            diaUltraViewPager.setCurrentItem(position);

            // click to return
            dia.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    dia.dismiss();
                    return false;
                }
            });
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
        container.addView(linearLayout);
//        linearLayout.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, container.getContext().getResources().getDisplayMetrics());
//        linearLayout.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, container.getContext().getResources().getDisplayMetrics());
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout view = (LinearLayout) object;
        container.removeView(view);
    }
}
