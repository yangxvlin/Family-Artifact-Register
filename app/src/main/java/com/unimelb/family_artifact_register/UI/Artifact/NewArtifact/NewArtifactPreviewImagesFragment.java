package com.unimelb.family_artifact_register.UI.Artifact.NewArtifact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener.MediaListener;
import com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.NewArtifactPreviewImageGridViewAdapter;
import com.unimelb.family_artifact_register.UI.Util.OnBackPressedListener;
import com.unimelb.family_artifact_register.Util.IFragment;

import es.dmoral.toasty.Toasty;
import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;

/**
 * fragment for user to preview selected image and add more images
 */
public class NewArtifactPreviewImagesFragment extends Fragment implements IFragment, OnBackPressedListener {
    /**
     * class tag
     */
    public static final String TAG = NewArtifactPreviewImagesFragment.class.getSimpleName();

    private NewArtifactPreviewImageGridViewAdapter imageAdapter;

    /**
     * easy image library
     */
    private EasyImage easyImage;

    /**
     * required empty constructor
     */
    public NewArtifactPreviewImagesFragment() {
    }

    public static NewArtifactPreviewImagesFragment newInstance() {
        return new NewArtifactPreviewImagesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_artifact_preview_images, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.preview_image);

        easyImage = new EasyImage.Builder(getContext())
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

        // to next fragment
        FloatingActionButton confirm = view.findViewById(R.id.fragment_new_artifact_preview_images_floating_button_confirm);
        confirm.setOnClickListener(view1 -> {
            if (imageAdapter.getCount() > 0) {
                NewArtifactHappenedTimeFragment happenedTime = NewArtifactHappenedTimeFragment.newInstance();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("next");
                fragmentTransaction.replace(R.id.activity_new_artifact_main_view, happenedTime);
                fragmentTransaction.commit();
            } else {
                Toasty.error(getContext(), R.string.not_enough_image_warning, Toast.LENGTH_SHORT, true)
                        .show();
            }
        });

        // choose images from camera
        FloatingActionButton camera = view.findViewById(R.id.fragment_new_artifact_preview_images_floating_button_camera);
        camera.setOnClickListener(view1 -> {
            easyImage.openCameraForImage(this);
        });

        // choose images form album
        FloatingActionButton album = view.findViewById(R.id.fragment_new_artifact_preview_images_floating_button_album);
        album.setOnClickListener(view1 -> {
            easyImage.openGallery(this);
        });

        GridView gridView = view.findViewById(R.id.fragment_new_artifact_preview_images_grid_view);
        imageAdapter = new NewArtifactPreviewImageGridViewAdapter(getContext(), ((MediaListener) getActivity()).getData());
        gridView.setAdapter(imageAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        easyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] mediaFiles, MediaSource source) {
                if (source == MediaSource.DOCUMENTS || source == MediaSource.CAMERA_IMAGE) {
                    // call back to parent activity
                    for (MediaFile imageFile : mediaFiles) {
                        Log.d(TAG + "/EasyImage", "Image file returned: " + imageFile.getFile().toURI().toString());
                        Uri image = Uri.fromFile(imageFile.getFile());
                        ((MediaListener) getActivity()).addData(image, TYPE_IMAGE);
                        imageAdapter.notifyDataSetChanged();
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

    // ************************************ implement interface ***********************************
    @Override
    public void onBackPressed() {
        ((MediaListener) getActivity()).clearData();
    }
}
