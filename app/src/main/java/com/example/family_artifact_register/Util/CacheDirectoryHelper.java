package com.example.family_artifact_register.Util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
        return cacheDirectory;
    }

    public File createNewFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss_").format(new Date());
        File newFile = getCacheDirectory()
                .toPath()
                .resolve(timeStamp+UUID.randomUUID().toString())
                .toFile();
        while (!newFile.exists()) {
            newFile = getCacheDirectory()
                    .toPath()
                    .resolve(timeStamp+UUID.randomUUID().toString())
                    .toFile();
        }
        return newFile;
    }
}
