package com.example.family_artifact_register.Util;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.SparseArray;
import android.widget.Toast;

import com.example.family_artifact_register.MainActivity;
import com.example.family_artifact_register.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class handles the event when download is finished
 */
public class DownloadBroadcastHelper extends BroadcastReceiver {
    private static DownloadBroadcastHelper ourInstance = null;

    public static DownloadBroadcastHelper getInstance() {
        if (ourInstance == null) {
            ourInstance = new DownloadBroadcastHelper();
        }
        return ourInstance;
    }

    List<DownloadCallback> callbackList;
    DownloadManager downloadManager;

    private DownloadBroadcastHelper() {
        callbackList = new ArrayList<>();
        downloadManager = (DownloadManager) MyApplication
                .getContext()
                .getSystemService(Context.DOWNLOAD_SERVICE);
        ourInstance = this;
        MyApplication.getContext().registerReceiver(this,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            // Invoke all callbacks
            for (DownloadCallback downloadCallback: callbackList) {
                downloadCallback.callback(id, downloadManager.getUriForDownloadedFile(id));
            }
        }
    }

    public void addCallback(DownloadCallback callback) {
        callbackList.add(callback);
    }

    public void removeCallback(DownloadCallback callback) {
        callbackList.remove(callback);
    }

    public static abstract class DownloadCallback implements Callback<Uri> {
        public abstract void callback(long downloadId, Uri data);

        @Override
        public void callback(int requestCode, int resultCode, Uri data) {
            callback(resultCode, data);
        }
    }
}