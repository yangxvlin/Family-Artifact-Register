package com.example.family_artifact_register;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CollectUserInfoActivity extends AppCompatActivity {

    public static final String TAG = CollectUserInfoActivity.class.getSimpleName();

    private String userName = "";

    private MaterialButton finishButton;

    private TextInputEditText userNameInput;

    private CircleImageView avatarImagePreview;

//    private FloatingActionButton fromCameraButton;

//    private FloatingActionButton fromAlbumButton;

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
//        fromCameraButton = findViewById(R.id.activity_collect_user_info_floating_button_camera);
//        fromAlbumButton = findViewById(R.id.activity_collect_user_info_floating_button_album);

//        fromCameraButton.setOnClickListener(view1 -> {
//            easyImage.openCameraForImage(this);
//        });

        avatarImagePreview.setOnClickListener(view1 -> {
            // easyImage.openGallery(this);
            // start picker to get image for cropping and then use the image in cropping activity
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .start(this);
        });

//        fromAlbumButton.setOnClickListener(view1 -> {
//            // easyImage.openGallery(this);
//            // start picker to get image for cropping and then use the image in cropping activity
//            CropImage.activity()
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setCropShape(CropImageView.CropShape.OVAL)
//                    .start(this);
//        });

        finishButton.setOnClickListener(view -> {
            upload();
            toHomeActivity();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                avatarUri = result.getUri();
                updateAvatar();
                setButtonVisibility();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
//            @Override
//            public void onMediaFilesPicked(MediaFile[] mediaFiles, MediaSource source) {
//
//                if (source == MediaSource.DOCUMENTS || source == MediaSource.CAMERA_IMAGE || source == MediaSource.GALLERY) {
//                    // call back to parent activity
//                    for (MediaFile imageFile : mediaFiles) {
//                        Log.d(TAG+"/EasyImage", "Image file returned: " + imageFile.getFile().toURI().toString());
//                        Uri image = Uri.fromFile(imageFile.getFile());
//                        avatarUri = compressUriImage(getContext(), image, false);
//                        updateAvatar();
//                        setButtonVisibility();
//                    }
//
//                } else {
//                    Log.e(TAG, "unknown media source !!!");
//                }
//            }
//
//            @Override
//            public void onImagePickerError(@NonNull Throwable error, @NonNull MediaSource source) {
//                //Some error handling
//                error.printStackTrace();
//            }
//
//            @Override
//            public void onCanceled(@NonNull MediaSource source) {
//                System.out.println("cancelled !!!");
//                //Not necessary to remove any files manually anymore
//            }
//        });
//    }

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
        UserInfo userInfo = UserInfo.newInstance(UserInfoManager.getInstance().getCurrentUid(), userName, avatarUri.toString());
        UserInfoManager.getInstance().storeUserInfo(userInfo);
    }

    private void toHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
