package com.example.family_artifact_register.Util;

import android.net.Uri;
import android.util.Log;

import com.example.family_artifact_register.UI.Util.TimeToString;

import android.os.Environment;
import java.io.File;
import java.util.UUID;

public class CacheDirectoryHelper {
    private static final String TAG = CacheDirectoryHelper.class.getSimpleName();

    private File cacheDirectory = null;

    private static final CacheDirectoryHelper ourInstance = new CacheDirectoryHelper();

    public static CacheDirectoryHelper getInstance() {
        return ourInstance;
    }

    private CacheDirectoryHelper() {
    }

    public void setCacheDirectory(File cacheDirectory) {
        this.cacheDirectory = cacheDirectory;
    }

    public File getCacheDirectory() {
        return cacheDirectory;
//        return new File(Environment.getExternalStorageDirectory().toString() ); //+ "/family_artifact_register_");
    }

    public File createNewFile(String postFix) {
        String timeStamp = TimeToString.getCurrentTimeFormattedString();
        File newFile = getCacheDirectory()
                .toPath()
                .resolve(timeStamp+UUID.randomUUID().toString()+postFix)
                .toFile();
        Log.d(TAG, "Creating newFile Uri: " + newFile.toString());
        while (newFile.exists()) {
            newFile = getCacheDirectory()
                    .toPath()
                    .resolve(timeStamp+UUID.randomUUID().toString())
                    .toFile();
        }
        newFile = new File(UriHelper.getInstance()
                .checkAddScheme(
                        newFile.toString())
                .toString());
        Log.d(TAG, "After adding scheme newFile Uri: " + newFile.toString());
        return newFile;
    }
}
