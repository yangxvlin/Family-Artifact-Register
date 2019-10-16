package com.example.family_artifact_register.UI.ArtifactManager.NewArtifactItem;

import com.example.family_artifact_register.R;

import tellh.com.stickyheaderview_rv.adapter.DataBean;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;

public class StickyArtifactItemHeader extends DataBean {

    private String prefix;

    public String getPrefix() {
        return prefix;
    }

    public StickyArtifactItemHeader(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public int getItemLayoutId(StickyHeaderViewAdapter adapter) {
        return R.layout.artifact_item_sticky_header;
    }

    @Override
    public boolean shouldSticky() {
        return true;
    }
}
