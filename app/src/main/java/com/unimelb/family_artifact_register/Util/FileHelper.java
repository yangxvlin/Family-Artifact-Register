package com.unimelb.family_artifact_register.Util;

import android.net.Uri;
import android.util.Log;

import java.io.File;

public class FileHelper {
    private static final String TAG = FileHelper.class.getSimpleName();

    private static final FileHelper ourInstance = new FileHelper();

    private FileHelper() {
    }

    public static FileHelper getInstance() {
        return ourInstance;
    }

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

    public String checkAddScheme(String stringUri) {
        return checkAddScheme(Uri.parse(stringUri)).toString();
    }

    public File checkAddScheme(File file) {
        Uri uri = Uri.parse(file.toString());
        return new File(uri.toString());
    }

    /**
     * Wrapper for make dir (hide away the check exist)
     *
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
