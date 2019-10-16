package com.example.family_artifact_register.UI.ArtifactManager.NewArtifactItem;

import android.content.Context;

import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.R;

import tellh.com.stickyheaderview_rv.adapter.DataBean;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;

public class StickyArtifactItemItem extends DataBean {
    private ArtifactItemWrapper artifactItemWrapper;

    private Context context;

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
