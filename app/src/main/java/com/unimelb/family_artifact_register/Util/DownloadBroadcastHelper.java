package com.unimelb.family_artifact_register.Util;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.Log;

import com.unimelb.family_artifact_register.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the event when download is finished by polymorphism
 */
public class DownloadBroadcastHelper extends BroadcastReceiver {
    /**
     * Add tag for logging
     */
    private static final String TAG = DownloadBroadcastHelper.class.getSimpleName();

    /**
     * singleton object
     */
    private static DownloadBroadcastHelper ourInstance = null;
    /**
     * list of callback task
     */
    List<DownloadCallback> callbackList;
    /**
     * down load manager
     */
    DownloadManager downloadManager;

    /**
     * create object
     */
    private DownloadBroadcastHelper() {
        callbackList = new ArrayList<>();
        downloadManager = (DownloadManager) MyApplication
                .getContext()
                .getSystemService(Context.DOWNLOAD_SERVICE);
        ourInstance = this;
        MyApplication.getContext().registerReceiver(this,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    /**
     * @return singleton object
     */
    public static DownloadBroadcastHelper getInstance() {
        if (ourInstance == null) {
            ourInstance = new DownloadBroadcastHelper();
        }
        return ourInstance;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Log.d(TAG, "Download id: " + id);
            // Invoke all callbacks
            for (DownloadCallback downloadCallback : callbackList) {
                downloadCallback.callback(id);
            }
        }
    }

    /**
     * register callback task
     *
     * @param callback callback task
     */
    public void addCallback(DownloadCallback callback) {
        callbackList.add(callback);
    }

    /**
     * cancel callback task
     *
     * @param callback callback task
     */
    public void removeCallback(DownloadCallback callback) {
        callbackList.remove(callback);
    }

    /**
     * task to download by polymorphism to have variation protection
     */
    public static abstract class DownloadCallback implements Callback<Uri> {
        /**
         * @param downloadId FirebaseDatabase data id to be downloaded
         */
        public abstract void callback(long downloadId);

        @Override
        public void callback(int requestCode, int resultCode, Uri data) {
            callback(resultCode);
        }
    }
}