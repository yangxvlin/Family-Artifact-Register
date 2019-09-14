package com.example.family_artifact_register.PresentationLayer.ArtifactManager;

import android.os.Bundle;
import android.util.Log;

import com.example.family_artifact_register.R;

import static com.example.family_artifact_register.PresentationLayer.util.ActivityNavigator.navigateFromToEmpty;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-14 14:05:49
 * @description activity for the user upload artifact contents
 */
public class NewArtifactActivity extends BaseCancelToolBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // enable the top-left cancel button
        setCancelButton();
        setTitle(R.string.new_artifact);
    }


    @Override
    public void baseFinish() {
        navigateFromToEmpty(this, ArtifactManageActivity.class);
    }

    protected int getLayoutResource() {
        return R.layout.activity_new_artifact;
    }

    protected int getToolBarId(){
        return R.id.new_artifact_cancel_toolbar;
    }
}
