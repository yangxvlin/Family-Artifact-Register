package com.unimelb.family_artifact_register.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocationManager;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.ArtifactMap.SearchLocationMap.MapSearchDisplayFragment;

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
        if (mapLocation != null) {
            easyImage.openGallery(this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] mediaFiles, MediaSource source) {
                Log.d(TAG + "/EasyImage", "Image source: " + source.toString());

                if (source == MediaSource.DOCUMENTS || source == MediaSource.CAMERA_IMAGE || source == MediaSource.GALLERY) {
                    // call back to parent activity
                    for (MediaFile imageFile : mediaFiles) {
                        Log.d(TAG + "/EasyImage", "Image file returned: " + imageFile.getFile().toURI().toString());
                        Uri imageUri = Uri.fromFile(imageFile.getFile());
                        Log.d(TAG, imageUri.toString());
                        mapLocation.addImageUrl(imageUri.toString());
                    }
                }
                Log.i(TAG, "Storing MapLocation to db");
                MapLocationManager.getInstance().storeMapLocation(mapLocation);
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
