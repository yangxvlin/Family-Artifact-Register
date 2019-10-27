package com.unimelb.family_artifact_register.Util;

import android.util.Log;

import java.io.File;
import java.util.UUID;

/**
 * pure fabricate logic of getting phone's cache directory singleton to provide global access
 */
public class CacheDirectoryHelper {
    /**
     * class tag
     */
    private static final String TAG = CacheDirectoryHelper.class.getSimpleName();
    /**
     * singleton object
     */
    private static final CacheDirectoryHelper ourInstance = new CacheDirectoryHelper();
    /**
     * phone's internal cache directory
     */
    private File cacheDirectory = null;

    /**
     * empty constructor
     */
    private CacheDirectoryHelper() {
    }

    /**
     * @return singleton instance
     */
    public static CacheDirectoryHelper getInstance() {
        return ourInstance;
    }

    /**
     * @return the app internal cache directory
     */
    public File getCacheDirectory() {
        return cacheDirectory;
    }

    /**
     * @param cacheDirectory the app internal cache directory
     */
    public void setCacheDirectory(File cacheDirectory) {
        this.cacheDirectory = cacheDirectory;
    }

    /**
     * @param postFix file suffix
     * @return created new file or existing file
     */
    public File createNewFile(String postFix) {
        String timeStamp = TimeToString.getCurrentTimeFormattedStringForFileName();
        File newFile = getCacheDirectory()
                .toPath()
                .resolve(timeStamp + UUID.randomUUID().toString() + postFix)
                .toFile();
        Log.d(TAG, "Creating newFile Uri: " + newFile.toString());
        while (newFile.exists()) {
            newFile = getCacheDirectory()
                    .toPath()
                    .resolve(timeStamp + UUID.randomUUID().toString())
                    .toFile();
        }
        return newFile;
    }
}
