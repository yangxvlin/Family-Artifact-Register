package com.example.family_artifact_register.UI.Util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-18 23:06:53
 * @description methods used when process images in android
 */
public class ImageProcessHelper {

    private static int calculateInSampleSize(BitmapFactory.Options options) {
        int height = options.outHeight;
        int width= options.outWidth;
        int inSampleSize = 2; // 默认像素压缩比例，压缩为原图的1/2
        int minLen = Math.min(height, width); // 原图的最小边长
        if(minLen > 100) { // 如果原始图像的最小边长大于100dp（此处单位我认为是dp，而非px）
            float ratio = (float)minLen / 100.0f; // 计算像素压缩比例
            inSampleSize = (int)ratio;
        }

        return inSampleSize;
    }

    public static BitmapFactory.Options getCompressImageOption(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
        options.inSampleSize = calculateInSampleSize(options); // 设置为刚才计算的压缩比例

        return options;
    }

    public static BitmapFactory.Options getCompressImageOption(Context context,Uri imageUri) throws FileNotFoundException {
        InputStream inputStream = context.getContentResolver().openInputStream(imageUri);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        BitmapFactory.decodeStream(inputStream, null, options);
        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
        options.inSampleSize = calculateInSampleSize(options); // 设置为刚才计算的压缩比例

        return options;
    }
}
