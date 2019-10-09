package com.example.family_artifact_register.Util;

import android.net.Uri;
import android.util.Log;

public class UriHelper {
    private static final String TAG = UriHelper.class.getSimpleName();

    private static final UriHelper ourInstance = new UriHelper();

    public static UriHelper getInstance() {
        return ourInstance;
    }

    private UriHelper() {
    }

    public Uri checkAddScheme(Uri uri) {
        Log.d(TAG, "before:" + uri.toString() + ", scheme: :" + uri.getScheme());
        if (uri.getScheme() != null) {
            uri = Uri.parse("file://"+uri.toString());
        }
        Log.d(TAG, "after:" + uri.toString());
        return uri;
    }

    public Uri checkAddScheme(String stringUri) {
        Log.d(TAG, "before:" + stringUri);
        if (!stringUri.contains("://")) {
            stringUri = "file://"+stringUri;
        }
        Log.d(TAG, "after:" + stringUri);
        return Uri.parse(stringUri);
    }
}
