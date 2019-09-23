package com.example.family_artifact_register.UI.Util;

import android.net.Uri;

import java.util.List;

/**
 * activity with media source and needed to be interacted with the fragment
 */
public interface MediaListener {
    void clearData();

    List<Uri> getData();

    /**
     * take in data and compress it and record it in the activity
     *
     * @param data media data, can be image or video
     * @param type MediaProcessHelper's processing data Type
     */
    void addData(Uri data, int type);
}
