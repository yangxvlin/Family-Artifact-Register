package com.example.family_artifact_register.UI.Util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.example.family_artifact_register.Util.CacheDirectoryHelper;
import com.iceteck.silicompressorr.SiliCompressor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-18 23:06:53
 * @description methods used when process images in android
 */
public class MediaProcessHelper {
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_VIDEO = 2;

    public static Uri compressUriImage(Context context,Uri image, boolean deleteSource) throws IOException {
//        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        // store in app cache directory
        File storageDir = new File(context.getCacheDir().getPath() + "/EasyImage/") ;
        return Uri.parse(SiliCompressor.with(context).compress(image.getPath(), storageDir, deleteSource));
    }

    public static Uri compreUriVideo(Context context,Uri video) throws URISyntaxException {
        String storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
                                        .getPath();
        return Uri.parse(SiliCompressor.with(context).compressVideo(video.getPath(), storageDir));
    }

    public static File copyFileToExternal(Uri uriFrom) {
        File fileFrom = new File(uriFrom.toString());
        File fileTo = new File(CacheDirectoryHelper.getInstance().getCacheDirectory().toString() + fileFrom.getName());
        System.out.println("copy file - from: " + fileFrom.toString() + " to: " + fileTo.toString());
        try {
            Files.copy(fileFrom.toPath(), fileTo.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileTo;
    }

    public static File createVideoFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String videoFileName = "VID_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File videoFile = null;
        try {
            videoFile = File.createTempFile("Compressed_"+videoFileName, ".mp4", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoFile;
    }
    // ***************************** ****************************

    private static int calculateInSampleSize(BitmapFactory.Options options) {
        int height = options.outHeight;
        int width= options.outWidth;
        // default pixel percentage ，change to original's 1/2
        int inSampleSize = 2;
        // original's min length
        int minLen = Math.min(height, width);
        // if original's min length > 100dp Note: unit should be dp not px
        if(minLen > 100) {
            // calculate ratio to be compressed
            float ratio = (float)minLen / 100.0f;
            inSampleSize = (int)ratio;
        }

        return inSampleSize;
    }

    public static BitmapFactory.Options getCompressImageOption(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // only access image's size info, not read whole image into memory so that won't memory leak
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        // after compressed, can to load original
        options.inJustDecodeBounds = false;
        // set the compress ratio just calculated
        options.inSampleSize = calculateInSampleSize(options);

        return options;
    }

    /**
     * compress String path is easy. But compress from InputStream is tricky.
     * https://stackoverflow.com/questions/39316069/bitmapfactory-decodestream-from-assets-returns-null-on-android-7
     *
     * @param context
     * @param imageUri
     * @return
     * @throws FileNotFoundException
     */
    public static BitmapFactory.Options getCompressImageOption(Context context,Uri imageUri) throws FileNotFoundException {
        InputStream inputStream = context.getContentResolver().openInputStream(imageUri);

        BitmapFactory.Options options = new BitmapFactory.Options();
        // only access image's size info, not read whole image into memory so that won't memory leak
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        // after compressed, can to load original
        options.inJustDecodeBounds = false;
        // set the compress ratio just calculated
        options.inSampleSize = calculateInSampleSize(options);

        return options;
    }
}
