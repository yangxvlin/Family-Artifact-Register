package com.unimelb.family_artifact_register.UI.Artifact.ViewArtifact.Util;

import android.content.Context;

import com.unimelb.family_artifact_register.PresentationLayer.Util.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.R;

import tellh.com.stickyheaderview_rv.adapter.DataBean;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;

/**
 * sticky item wrapper
 */
public class StickyArtifactItemItem extends DataBean {
    /**
     * data
     */
    private ArtifactItemWrapper artifactItemWrapper;

    /**
     * context
     */
    private Context context;

    /**
     * is this sticky
     */
    private boolean shouldSticky;

    public StickyArtifactItemItem(ArtifactItemWrapper artifactItemWrapper, Context context) {
        this.artifactItemWrapper = artifactItemWrapper;
        this.context = context;
    }

    @Override
    public int getItemLayoutId(StickyHeaderViewAdapter stickyHeaderViewAdapter) {
        return R.layout.item_my_artifact;
    }

    public ArtifactItemWrapper getArtifactItemWrapper() {
        return this.artifactItemWrapper;
    }

    public Context getContext() {
        return this.context;
    }

    public void setShouldSticky(boolean shouldSticky) {
        this.shouldSticky = shouldSticky;
    }

    @Override
    public boolean shouldSticky() {
        return shouldSticky;
    }

}
