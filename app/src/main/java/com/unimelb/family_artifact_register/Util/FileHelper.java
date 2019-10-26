package com.unimelb.family_artifact_register.Util;

import android.net.Uri;
import android.util.Log;

import java.io.File;

/**
 * pure fabricate logic of manipulating files in android
 * singleton to provide global access
 */
public class FileHelper {
    /**
     * class tag
     */
    private static final String TAG = FileHelper.class.getSimpleName();

    /**
     * singleton instance
     */
    private static final FileHelper ourInstance = new FileHelper();

    /**
     * @return singleton instance
     */
    public static FileHelper getInstance() {
        return ourInstance;
    }

    /**
     * empty class constructor
     */
    private FileHelper() {
    }

    /**
     * @param uri file uri
     * @return uri with scheme added
     */
    public Uri checkAddScheme(Uri uri) {
        Log.d(TAG, "before check scheme:" + uri.toString() +
                "\nscheme: :" + uri.getScheme());
        if (uri.getScheme() == null) {
            uri = uri.buildUpon().scheme("file").build();
        }
        Log.d(TAG, "after check scheme:" + uri.toString()
                + "\nscheme: " + uri.getScheme()
                + "\npath: " + uri.getPath());
        return uri;
    }

    /**
     * @param stringUri file uri string
     * @return uri string with scheme added
     */
    public String checkAddScheme(String stringUri) {
        return checkAddScheme(Uri.parse(stringUri)).toString();
    }

    /**
     * @param file file
     * @return file with scheme added
     */
    public File checkAddScheme(File file) {
        Uri uri = Uri.parse(file.toString());
        return new File(uri.toString());
    }

    /**
     * Wrapper for make dir (hide away the check exist)
     * @param file The directory to make
     * @return if the mkdirs is successful
     */
    public boolean mkdirs(File file) {
        if (file.isDirectory() && file.exists()) {
            return true;
        } else {
            return file.mkdirs();
        }
    }
}
