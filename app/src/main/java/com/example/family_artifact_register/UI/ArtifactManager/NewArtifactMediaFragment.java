package com.example.family_artifact_register.UI.ArtifactManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

import static com.example.family_artifact_register.UI.Util.ImageProcessHelper.TYPE_IMAGE;

public class NewArtifactMediaFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = NewArtifactMediaFragment.class.getSimpleName();

    private EasyImage easyImage;

    public NewArtifactMediaFragment() {
        // required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_artifact_media, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.new_artifact);

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
//        FloatingActionButton confirm = view.findViewById(R.id.fragment_new_artifact_media_floating_button_confirm);
//        confirm.setOnClickListener(view1 -> {
//            HappenedTimeFragment happenedTime = HappenedTimeFragment.newInstance();
//            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.addToBackStack("next");
//            fragmentTransaction.replace(R.id.activity_new_artifact_main_view, happenedTime);
//            fragmentTransaction.commit();
//        });

//        FloatingActionButton camera = view.findViewById(R.id.fragment_new_artifact_media_camera);
//        camera.setOnClickListener(view1 -> {
//            easyImage.openCameraForImage(this);
//        });

        // choose images form album
        FloatingActionButton album = view.findViewById(R.id.fragment_new_artifact_media_floating_button_album);
        album.setOnClickListener(view1 -> {
            easyImage.openGallery(this);
        });
    }

    public static NewArtifactMediaFragment newInstance() { return new NewArtifactMediaFragment(); }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        easyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] imageFiles, MediaSource source) {
                for (MediaFile imageFile : imageFiles) {
                    Log.d("EasyImage", "Image file returned: " + imageFile.getFile().toURI().toString());
                    Uri image = Uri.fromFile(imageFile.getFile());
                    ((NewArtifactActivity2)getActivity()).addData(image, TYPE_IMAGE);
                }

                if (source == MediaSource.DOCUMENTS) {
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack("next");
                    fragmentTransaction.replace(R.id.activity_new_artifact_main_view, NewArtifactPreviewImagesFragment.newInstance());
                    fragmentTransaction.commit();
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
