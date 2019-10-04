package com.example.family_artifact_register.UI.ArtifactHub;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.Util.BaseActionBarActivity;


/**
 * @author Haichao Song 854035,
 * @time 2019-9-19 11:54:23
 * @description activity for artifact detail view.
 */
public class ArtifactDetailActivity extends BaseActionBarActivity {

    TextView mTitleTv, mDescTv, mUserTv;
    ImageView mImageIv, mAvatarIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitleTv = findViewById(R.id.publisher);
        mDescTv = findViewById(R.id.desc);
        mUserTv = findViewById(R.id.user);
        mImageIv = findViewById(R.id.imageIv);
        mAvatarIv = findViewById(R.id.avatarIv);

        // Use intent to send information to artifact detail activity
        Intent intent = getIntent();

        String mTitle = intent.getStringExtra("iTitle");
        String mDesc = intent.getStringExtra("iDesc");
        String mUser = intent.getStringExtra("iUser");

        byte[] mImageBytes = getIntent().getByteArrayExtra("iImage");
        byte[] mAvatarBytes = getIntent().getByteArrayExtra("iImage");
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(mAvatarBytes, 0, mImageBytes.length);
        Bitmap avatarBitmap = BitmapFactory.decodeByteArray(mAvatarBytes, 0, mAvatarBytes.length);

        // Set action bar title to be artifact title
//        getMyActionBar().setTitle(mTitle);

        // Set artifact information the same as activity hub
        mTitleTv.setText(mTitle);
        mDescTv.setText(mDesc);
        mUserTv.setText(mUser);
        mImageIv.setImageBitmap(imageBitmap);
        mAvatarIv.setImageBitmap(avatarBitmap);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mTitle);
        actionBar.setBackgroundDrawable(this.getDrawable(R.drawable.gradient_background));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_artifact_detail;
    }
}
