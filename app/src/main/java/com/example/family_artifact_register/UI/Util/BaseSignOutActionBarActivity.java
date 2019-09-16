package com.example.family_artifact_register.UI.Util;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.family_artifact_register.R;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-16 20:04:45
 * @description base activity with an action bar which can
 * 1. sign out from menu
 * 2. set centered title text
 * 3. disable navigation icon
 */
public abstract class BaseSignOutActionBarActivity extends AppCompatActivity {
    private Toolbar toolBar;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        textView = (TextView) findViewById(R.id.main_activity_toolbar_title);
        toolBar = findViewById(getToolBarId());

        if (toolBar != null) {
            setSupportActionBar(toolBar);
        }
    }

    protected abstract int getLayoutResource();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sign_out:
                Toast.makeText(this, R.string.menu_sign_out, Toast.LENGTH_SHORT).show();
                baseFinish();
                break;
        }
        return true;
    }

    public void setDisplayHomeEnabled(boolean b) {
        if (getSupportActionBar() != null) {
            // enable the display
            getSupportActionBar().setDisplayHomeAsUpEnabled(b);
        }
    }

    public void disableNavigationIcon() {
        if (toolBar != null) {
            // enable the display
            toolBar.setNavigationIcon(null);
        }
    }

    public void setDisplayShowTitle(boolean b) {
        if (getSupportActionBar() != null) {
            // enable the display
            getSupportActionBar().setDisplayShowTitleEnabled(b);
        }
    }

    public void setTitleText(int id) {
        textView.setText(id);
    }

    public Toolbar getToolBar() {
        return toolBar;
    }

    /**
     * abstract method to end the activity
     */
    public abstract void baseFinish();

    protected abstract int getToolBarId();

}
