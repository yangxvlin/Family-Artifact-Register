package com.example.family_artifact_register.UI.ArtifactManager;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.family_artifact_register.R;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-21 13:33:24
 * @description activity let user to upload new artifact
 */
public class NewArtifactActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_artifact_2);

        // set cancel icon
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.common_close_button);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // https://stackoverflow.com/a/30059647
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
