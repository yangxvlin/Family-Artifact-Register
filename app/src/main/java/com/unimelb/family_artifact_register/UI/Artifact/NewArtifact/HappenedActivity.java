package com.unimelb.family_artifact_register.UI.Artifact.NewArtifact;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.Util.BaseCancelToolBarActivity;

import java.util.List;

import static com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.UploadingArtifactConstant.ARTIFACT_DESCRIPTION;
import static com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.UploadingArtifactConstant.ARTIFACT_IMAGES;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-14 14:05:49
 * @description activity for the user choose the place where the artifact happened
 */
@Deprecated
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
        setTitle(R.string.artifact_where_happened_title);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_happened;
    }
}
