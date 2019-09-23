package com.example.family_artifact_register.UI.ArtifactManager;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.Util.MediaListener;
import com.example.family_artifact_register.UI.Util.MediaProcessHelper;
import com.example.family_artifact_register.UI.Util.OnBackPressedListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-21 13:33:24
 * @description activity let user to upload new artifact
 */
public class NewArtifactActivity2 extends AppCompatActivity implements MediaListener {
    private static final String TAG = NewArtifactActivity2.class.getSimpleName();

    FragmentManager fm;

    Fragment mediaFragment = NewArtifactMediaFragment.newInstance();

    List<Uri> mediaData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_artifact_2);
        mediaData = new ArrayList<>();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        // initialize first fragment
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.activity_new_artifact_main_view, mediaFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // https://stackoverflow.com/a/30059647
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.activity_new_artifact_main_view);
            if (f instanceof OnBackPressedListener) {
                ((OnBackPressedListener) f).onBackPressed();
            }

            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    // ************************************ implement interface ***********************************
    /**
     * take in data and compress it and record it in the activity
     *
     * @param data media data, can be image or video
     * @param type MediaProcessHelper's processing data Type
     */
    @Override
    public void addData(Uri data, int type) {
        switch (type) {
            case MediaProcessHelper.TYPE_IMAGE:
                try {
                    mediaData.add(MediaProcessHelper.compressUriImage(this, data, false));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case MediaProcessHelper.TYPE_VIDEO:
//                try {
                    mediaData.add(data);
//                    mediaData.add(MediaProcessHelper.compreUriVideo(this, data));
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
                break;
        }
        Log.i(TAG, "added data: "+data.getPath() + " with cur size = " + mediaData.size());
    }

    @Override
    public List<Uri> getData() { return mediaData; }

    @Override
    public void clearData() { mediaData.clear(); }
}
