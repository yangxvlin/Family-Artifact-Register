package com.example.family_artifact_register.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocationManager;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.ArtifactManager.NewArtifactMediaFragment;
import com.example.family_artifact_register.UI.ArtifactManager.NewArtifactPreviewImagesFragment;
import com.example.family_artifact_register.UI.ArtifactManager.NewArtifactPreviewVideoFragment;
import com.example.family_artifact_register.UI.MapServiceFragment.MapSearchDisplayFragment;
import com.example.family_artifact_register.UI.Util.MediaListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

public class MapManagerTestActivity extends AppCompatActivity {
    /**
     * class tag
     */
    public static final String TAG = MapManagerTestActivity.class.getSimpleName();

    MapSearchDisplayFragment mapSearchDisplayFragment;
    EasyImage easyImage;

    MapLocation mapLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_manager_test);

        mapSearchDisplayFragment = MapSearchDisplayFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.test_map_search_add,
                mapSearchDisplayFragment).commit();

        easyImage = new EasyImage.Builder(this)
                // Chooser only
                // Will appear as a system chooser title, DEFAULT empty string
                .setChooserTitle("Pick media")
                // Will tell chooser that it should show documents or gallery apps
//                .setChooserType(ChooserType.CAMERA_AND_DOCUMENTS) // you can use this or the one below
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                // Setting to true will cause taken pictures to show up in the device gallery, DEFAULT false
                .setCopyImagesToPublicGalleryFolder(false)
                // Sets the name for images stored if setCopyImagesToPublicGalleryFolder = true
                .setFolderName("EasyImage sample")
                // Allow multiple picking
                .allowMultiple(true)
                .build();
    }


    public void onClickAddToDatabase(View view) {
        mapLocation = mapSearchDisplayFragment.getSelectedLocation();
        // choose images form album
        FloatingActionButton album = findViewById(R.id.test_select_image_add_to_map);
        album.setOnClickListener(view1 -> easyImage.openGallery(this));

        if (mapLocation != null) {
            MapLocationManager.getInstance().storeMapLocation(mapLocation);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] mediaFiles, MediaSource source) {

                if (source == MediaSource.DOCUMENTS || source == MediaSource.CAMERA_IMAGE) {
                    // call back to parent activity
                    for (MediaFile imageFile : mediaFiles) {
                        Log.d(TAG + "/EasyImage", "Image file returned: " + imageFile.getFile().toURI().toString());
                        Uri imageUri = Uri.fromFile(imageFile.getFile());
                        mapLocation.addImageUrl(imageUri.toString());
                    }
                }
            }

            @Override
            public void onImagePickerError(@NonNull Throwable error, @NonNull MediaSource source) {
                //Some error handling
                error.printStackTrace();
            }

            @Override
            public void onCanceled(@NonNull MediaSource source) {
                System.out.println("cancelled !!!");
                //Not necessary to remove any files manually anymore
            }
        });
    }
}
