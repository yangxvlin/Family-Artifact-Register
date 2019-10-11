package com.example.family_artifact_register;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

import static com.example.family_artifact_register.MyApplication.getContext;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.compressUriImage;

public class CollectUserInfoActivity extends AppCompatActivity {

    public static final String TAG = CollectUserInfoActivity.class.getSimpleName();

    private String userName = "";

    private MaterialButton finishButton;

    private TextInputEditText userNameInput;

    private CircleImageView avatarImagePreview;

    private FloatingActionButton fromCameraButton;

    private FloatingActionButton fromAlbumButton;

    private EasyImage easyImage;

    private Uri avatarUri = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_user_info);

        finishButton = findViewById(R.id.activity_collect_user_info_finish);
        userNameInput = findViewById(R.id.activity_collect_user_info_username_input);
        setButtonVisibility();
        setUserNameInputListener();

        avatarImagePreview = findViewById(R.id.activity_collect_user_info_avatar);
        fromCameraButton = findViewById(R.id.activity_collect_user_info_floating_button_camera);
        fromAlbumButton = findViewById(R.id.activity_collect_user_info_floating_button_album);

        easyImage = new EasyImage.Builder(getContext())
                // Chooser only
                // Will appear as a system chooser title, DEFAULT empty string
                .setChooserTitle(getString(R.string.choose_avatar))
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

        fromCameraButton.setOnClickListener(view1 -> {
            easyImage.openCameraForImage(this);
        });

        fromAlbumButton.setOnClickListener(view1 -> {
            easyImage.openGallery(this);
        });

        finishButton.setOnClickListener(view -> {
            upload();
            toHomeActivity();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] mediaFiles, MediaSource source) {

                if (source == MediaSource.DOCUMENTS || source == MediaSource.CAMERA_IMAGE || source == MediaSource.GALLERY) {
                    // call back to parent activity
                    for (MediaFile imageFile : mediaFiles) {
                        Log.d(TAG+"/EasyImage", "Image file returned: " + imageFile.getFile().toURI().toString());
                        Uri image = Uri.fromFile(imageFile.getFile());
                        avatarUri = compressUriImage(getContext(), image, false);
                        updateAvatar();
                        setButtonVisibility();
                    }

                } else {
                    Log.e(TAG, "unknown media source !!!");
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

    private void setButtonVisibility() {
        // require username
        if (!userName.isEmpty() && (avatarUri != null)) {
            finishButton.setVisibility(View.VISIBLE);
        } else {
            finishButton.setVisibility(View.GONE);
        }
    }

    private void setUserNameInputListener() {
        userNameInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                userName = userNameInput.getText().toString();
                setButtonVisibility();
            }
        });
    }

    private void updateAvatar() {
        avatarImagePreview.setImageURI(avatarUri);
        avatarImagePreview.setVisibility(View.VISIBLE);
    }

    private void upload() {
        UserInfo userInfo = UserInfo.newInstance(null, userName);
        UserInfoManager.getInstance().storeUserInfo(userInfo);
    }

    private void toHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
