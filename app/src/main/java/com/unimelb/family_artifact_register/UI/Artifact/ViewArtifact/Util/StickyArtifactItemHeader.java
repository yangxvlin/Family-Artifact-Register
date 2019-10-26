package com.unimelb.family_artifact_register.UI.Artifact.ViewArtifact.Util;

import android.util.Log;

import com.unimelb.family_artifact_register.R;

import tellh.com.stickyheaderview_rv.adapter.DataBean;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;

/**
 * sticky header wrapper
 */
public class StickyArtifactItemHeader extends DataBean {
    /**
     * class tag
     */
    private final String TAG = getClass().getSimpleName();

    /**
     * simple text
     */
    private String prefix;

    public StickyArtifactItemHeader(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public int getItemLayoutId(StickyHeaderViewAdapter adapter) {
        Log.d(TAG, "layout id for sticky header adapter: " + R.layout.artifact_item_sticky_header);
        return R.layout.artifact_item_sticky_header;
    }

    @Override
    public boolean shouldSticky() {
        return true;
    }
}
