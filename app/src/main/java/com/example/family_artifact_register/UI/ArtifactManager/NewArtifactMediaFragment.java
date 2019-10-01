package com.example.family_artifact_register.UI.ArtifactManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.MapServiceFragment.CurrentLocationFragment;
import com.example.family_artifact_register.UI.Util.DescriptionListener;
import com.example.family_artifact_register.UI.Util.MediaListener;
import com.example.family_artifact_register.UI.Util.OnBackPressedListener;
import com.example.family_artifact_register.UI.Util.UploadLocationListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;

public class NewArtifactMediaFragment extends Fragment implements IFragment, OnBackPressedListener {
    /**
     * class tag
     */
    public static final String TAG = NewArtifactMediaFragment.class.getSimpleName();

    private EasyImage easyImage;

    private CurrentLocationFragment uploadLocationFragment;

    EditText description;

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

        // upload location fragment location
        uploadLocationFragment = CurrentLocationFragment.newInstance();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_new_artifact_media_upload_location, uploadLocationFragment)
                .commit();

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

        // take image from camera
        FloatingActionButton camera = view.findViewById(R.id.fragment_new_artifact_media_floating_button_camera);
        camera.setOnClickListener(view1 -> {
            easyImage.openCameraForImage(this);
        });

        // choose images form album
        FloatingActionButton album = view.findViewById(R.id.fragment_new_artifact_media_floating_button_album);
        album.setOnClickListener(view1 -> {
            easyImage.openGallery(this);
        });

        // take video from camera
        FloatingActionButton video = view.findViewById(R.id.fragment_new_artifact_media_floating_button_video);
        video.setOnClickListener(view1 -> {
            easyImage.openCameraForVideo(this);
        });

        // set description if any
        description = view.findViewById(R.id.fragment_new_artifact_media_description_input);
        description.setText(((DescriptionListener)getActivity()).getDescription(), TextView.BufferType.EDITABLE);
    }

    public static NewArtifactMediaFragment newInstance() { return new NewArtifactMediaFragment(); }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        easyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] mediaFiles, MediaSource source) {

                if (source == MediaSource.DOCUMENTS || source == MediaSource.CAMERA_IMAGE || source == MediaSource.GALLERY) {
                    // call back to parent activity
                    for (MediaFile imageFile : mediaFiles) {
                        Log.d(TAG+"/EasyImage", "Image file returned: " + imageFile.getFile().toURI().toString());
                        Uri image = Uri.fromFile(imageFile.getFile());
                        ((MediaListener)getActivity()).addData(image, TYPE_IMAGE);
                    }
                    ((MediaListener)getActivity()).setMediaType(TYPE_IMAGE);
                    ((UploadLocationListener)getActivity()).setUploadLocation(uploadLocationFragment.getLocation());
                    ((DescriptionListener)getActivity()).setDescription(description.getText().toString());
                    // next images fragment
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack("next");
                    fragmentTransaction.replace(R.id.activity_new_artifact_main_view, NewArtifactPreviewImagesFragment.newInstance());
                    fragmentTransaction.commit();
                } else if (source == MediaSource.CAMERA_VIDEO) {
                    // call back to parent activity
                    for (MediaFile videoFile : mediaFiles) {
                        Log.d(TAG+"/EasyImage", "Video file returned: " + videoFile.getFile().toURI().toString());
                        Uri image = Uri.fromFile(videoFile.getFile());
                        ((MediaListener)getActivity()).addData(image, TYPE_VIDEO);
                    }
                    ((MediaListener)getActivity()).setMediaType(TYPE_VIDEO);
                    ((UploadLocationListener)getActivity()).setUploadLocation(uploadLocationFragment.getLocation());
                    ((DescriptionListener)getActivity()).setDescription(description.getText().toString());
                    // next fragment
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack("next");
                    fragmentTransaction.replace(R.id.activity_new_artifact_main_view, NewArtifactPreviewVideoFragment.newInstance());
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

    // ************************************ implement interface ***********************************
    @Override
    public void onBackPressed() {
        ((MediaListener)getActivity()).clearData();
    }
}
