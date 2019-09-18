package com.example.family_artifact_register.UI.ArtifactHub;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.Util.BaseActionBarActivity;

public class ArtifactDetailActivity extends BaseActionBarActivity {

    TextView mTitleTv, mDescTv, mUserTv;
    ImageView mImageIv, mAvatarIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_artifact_detail);

//        ActionBar actionBar = getSupportActionBar();

        mTitleTv = findViewById(R.id.title);
        mDescTv = findViewById(R.id.desc);
        mUserTv = findViewById(R.id.user);
        mImageIv = findViewById(R.id.imageIv);
        mAvatarIv = findViewById(R.id.avatarIv);

        Intent intent = getIntent();

        String mTitle = intent.getStringExtra("iTitle");
        String mDesc = intent.getStringExtra("iDesc");
        String mUser = intent.getStringExtra("iUser");

        byte[] mBytes = getIntent().getByteArrayExtra("iImage");
        Bitmap bitmap = BitmapFactory.decodeByteArray(mBytes, 0, mBytes.length);

        getMyActionBar().setTitle(mTitle);

        mTitleTv.setText(mTitle);
        mDescTv.setText(mDesc);
        mUserTv.setText(mUser);
        mImageIv.setImageBitmap(bitmap);
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_artifact_detail;
    }
}
