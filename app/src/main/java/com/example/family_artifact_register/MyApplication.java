package com.example.family_artifact_register;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.family_artifact_register.FoundationLayer.Util.FirebaseAuthHelper;
import com.example.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;
import com.example.family_artifact_register.Util.CacheDirectoryHelper;
import com.example.family_artifact_register.Util.DownloadBroadcastHelper;
import com.example.family_artifact_register.Util.FileHelper;

public class MyApplication extends Application {
    private static Application sApplication;

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        // set whole app cache directory
        CacheDirectoryHelper.getInstance().setCacheDirectory(this.getExternalCacheDir());

        // initialise all helper singleton
        FirebaseAuthHelper.getInstance();
        FirebaseStorageHelper.getInstance();
        DownloadBroadcastHelper.getInstance();
        FileHelper.getInstance();
        CacheDirectoryHelper.getInstance();

        // register to be informed of activities starting up
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity,
                                          Bundle savedInstanceState) {

                // new activity created; force its orientation to portrait
                activity.setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
            }
        });

    }
}
