package com.example.family_artifact_register.UI.ArtifactManager;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.ArtifactManager.NewArtifact.NewArtifactActivity;

import static com.example.family_artifact_register.UI.Util.ActivityNavigator.navigateFromTo;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-13 21:33:40
 * @description activity for the user to manage artifacts
 */
public class ArtifactManageActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artifact_manage);
    }

    /* ********************************** view controller *************************************** */
    public void newArtifact(View view) {
        navigateFromTo(this, NewArtifactActivity.class);
    }
}
