package com.unimelb.family_artifact_register.UI.Util;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * polymorphism to provide a base activity with an action bar with cancel icon
 */
public abstract class BaseActionBarActivity extends AppCompatActivity {
    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        actionBar = getSupportActionBar();
    }


    public ActionBar getMyActionBar() {
        return actionBar;
    }

    public void setDisplayHomeEnabled(boolean b) {
        if (getSupportActionBar() != null) {
            // enable the display
            getSupportActionBar().setDisplayHomeAsUpEnabled(b);
        }
    }

    public void setDisplayShowTitle(boolean b) {
        if (getSupportActionBar() != null) {
            // enable the display
            getSupportActionBar().setDisplayShowTitleEnabled(b);
        }
    }

    /**
     * @return the concrete activity's layout resource file
     */
    protected abstract int getLayoutResource();
}
