package com.example.family_artifact_register.UI.ArtifactManager;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.family_artifact_register.R;

import java.util.List;

import static com.example.family_artifact_register.UI.ArtifactManager.UploadingArtifact.ARTIFACT_DESCRIPTION;
import static com.example.family_artifact_register.UI.ArtifactManager.UploadingArtifact.ARTIFACT_IMAGES;
import static com.example.family_artifact_register.UI.Util.ActivityNavigator.navigateFromToEmpty;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-14 14:05:49
 * @description activity for the user choose the place where the artifact happened
 */
public class HappenedActivity extends BaseCancelToolBarActivity {

    /**
     * artifact's description from previous activity
     */
    private String artifactDescription;

    /**
     * artifact's images from previous activity
     */
    private List<Bitmap> artifactImages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        artifactDescription = getIntent().getStringExtra(ARTIFACT_DESCRIPTION);
        artifactImages = (List<Bitmap>) getIntent().getSerializableExtra(ARTIFACT_IMAGES);

        // enable the top-left cancel button
        setCancelButton();
        setTitle(R.string.artifact_happened_title);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_happened;
    }

    @Override
    public void baseFinish() {
        navigateFromToEmpty(this, ArtifactManageActivity.class);
    }

    @Override
    protected int getToolBarId() {
        return R.id.happened_cancel_toolbar;
    }
}
