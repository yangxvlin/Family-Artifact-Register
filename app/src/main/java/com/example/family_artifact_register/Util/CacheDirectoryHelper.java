package com.example.family_artifact_register.Util;

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

    public String getCacheDirectory() {
        return cacheDirectory.getPath();
    }
}
