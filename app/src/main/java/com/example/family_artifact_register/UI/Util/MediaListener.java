package com.example.family_artifact_register.UI.Util;

import android.net.Uri;

import java.util.List;

/**
 * activity with media source and needed to be interacted with the fragment
 */
public interface MediaListener {
    void clearData();

    List<Uri> getData();
}
