package com.example.family_artifact_register.Util;

import android.os.Environment;

import java.io.File;

public class CacheDirectoryHelper {
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
//        return cacheDirectory;
        return new File(Environment.getExternalStorageDirectory().toString() ); //+ "/family_artifact_register_");
    }
}
