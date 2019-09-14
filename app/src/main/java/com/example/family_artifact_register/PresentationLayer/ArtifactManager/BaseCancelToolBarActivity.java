package com.example.family_artifact_register.PresentationLayer.ArtifactManager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.family_artifact_register.R;


/**
 * @author XuLin Yang 904904,
 * @time 2019-9-14 14:05:49
 * @description activity with predefined app bar with one cancel label at top left corner of the
 * page and direct back to ArtifactManageActivity
 */
public abstract class BaseCancelToolBarActivity extends AppCompatActivity {

    private Toolbar toolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        toolBar = findViewById(getToolBarId());

        if (toolBar != null) {
            setSupportActionBar(toolBar);
            if (getSupportActionBar() != null) {
                setDisplayHomeEnabled(true);
            }
        }
    }

    protected abstract int getLayoutResource();

    public void setDisplayHomeEnabled(boolean b) {
        if (getSupportActionBar() != null) {
            // enable the display
            getSupportActionBar().setDisplayHomeAsUpEnabled(b);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        // https://stackoverflow.com/questions/26486730/in-android-app-toolbar-settitle-method-has-no-effect-application-name-is-shown
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        // https://stackoverflow.com/questions/26486730/in-android-app-toolbar-settitle-method-has-no-effect-application-name-is-shown
        getSupportActionBar().setTitle(titleId);
    }

    public Toolbar getToolBar() {
        return toolBar;
    }

    /**
     * set left top cancel button and its listener
     */
    public void setCancelButton() {
        // set the button for the action bar
        toolBar.setNavigationIcon(R.drawable.common_close_button);
        toolBar.setNavigationOnClickListener(view -> baseFinish());
    }

    /**
     * abstract method to end the activity
     */
    public abstract void baseFinish();

    protected abstract int getToolBarId();
}
