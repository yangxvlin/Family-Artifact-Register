package com.unimelb.family_artifact_register.UI.Util;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.unimelb.family_artifact_register.R;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-16 20:04:45
 * @description base activity with an action bar which can 1. sign out from menu 2. set centered
 * title text 3. disable navigation icon
 */
public abstract class BaseSignOutActionBarActivity extends BaseActionBarActivity {
    TextView textView;

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

    public void setCenterTitleText(int id) {
        ViewGroup parent = findViewById(getLayoutResource());
        View viewActionBar = getLayoutInflater().inflate(R.layout.actionbar_center_title_layout, parent);
        //Center the text view in the ActionBar !
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textViewTitle = viewActionBar.findViewById(R.id.action_bar_title);
        textViewTitle.setText(id);
        getMyActionBar().setCustomView(viewActionBar, params);
        getMyActionBar().setDisplayShowCustomEnabled(true);
        setDisplayShowTitle(false);
        setDisplayHomeEnabled(false);
    }

    /**
     * abstract method to end the activity
     */
    public abstract void baseFinish();
}
