package com.unimelb.family_artifact_register;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.unimelb.family_artifact_register.FoundationLayer.UserModel.UserInfoManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @description activity to collect new user's username and avatar
 */
public class CollectUserInfoActivity extends AppCompatActivity {

    /**
     * class tag
     */
    public static final String TAG = CollectUserInfoActivity.class.getSimpleName();

    /**
     * collected username
     */
    private String userName = "";

    /**
     * button to finish collect user info
     */
    private MaterialButton finishButton;

    /**
     * user name input text box
     */
    private TextInputEditText userNameInput;

    /**
     * user avatar
     */
    private CircleImageView avatarImagePreview;

    /**
     * user avatar uri
     */
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

        avatarImagePreview.setOnClickListener(view1 -> {
            // start picker to get image for cropping and then use the image in cropping activity
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .start(this);
        });

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
        UserInfoManager.getInstance().setDisplayName(userName);
        UserInfoManager.getInstance().setPhoto(avatarUri);
    }

    private void toHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
