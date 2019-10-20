package com.unimelb.family_artifact_register.UI.ArtifactManager.NewArtifact;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.UI.ArtifactManager.BaseCancelToolBarActivity;
import com.unimelb.family_artifact_register.UI.ArtifactManager.HappenedActivity;
import com.unimelb.family_artifact_register.UI.Util.UploadArtifactAdapter;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.MapServiceFragment.CurrentLocationFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;
import static com.unimelb.family_artifact_register.UI.ArtifactManager.UploadingArtifact.ARTIFACT_DESCRIPTION;
import static com.unimelb.family_artifact_register.UI.ArtifactManager.UploadingArtifact.ARTIFACT_IMAGES;
import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.getCompressImageOption;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-14 14:05:49
 * @description activity for the user upload artifact contents
 * modify from https://www.jianshu.com/p/fcc7f8507211
 */
public class NewArtifactActivity extends BaseCancelToolBarActivity {

    private final String TAG = getClass().getSimpleName();
    private static final String PERMISSION_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int REQUEST_PERMISSION_CODE = 267;
    private static final int TAKE_PHOTO = 189;
    private static final int CHOOSE_PHOTO = 385;
    private static final String FILE_PROVIDER_AUTHORITY = "com.mydomain.fileprovider";
    private Uri mImageUri, mImageUriFromFile;

    /**
     * used to store token photos
     */
    private File imageFile;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private UploadArtifactAdapter uploadArtifactAdapter;
    private DividerItemDecoration dividerItemDecoration;
    private EditText editText;

    private FrameLayout uploadLocationFrame;

    private Fragment uploadLocationFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // enable the top-left cancel button
        setCancelButton();
        setTitle(R.string.new_artifact);

        uploadLocationFrame = findViewById(R.id.new_artifact_upload_location);
        uploadLocationFragment = CurrentLocationFragment.newInstance();
        FragmentManager fragmentManager =  getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.new_artifact_upload_location, uploadLocationFragment)
                .commit();

        mRecyclerView = findViewById(R.id.recycler_image_preview);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        uploadArtifactAdapter = new UploadArtifactAdapter();
        mRecyclerView.setAdapter(uploadArtifactAdapter);
        dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        editText = findViewById(R.id.add_artifact_description_input);

        // request write permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(PERMISSION_WRITE_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{PERMISSION_WRITE_STORAGE}, REQUEST_PERMISSION_CODE);
            }
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_new_artifact;
    }

    /**
     * start taking photo by using system camera
     *
     * @param view view
     */
    // ********************************* view controller *****************************************
    public void takePhoto(View view) {
        // open camera intent
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // if the system has no camera app, then the app will inform user.
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
            imageFile = createImageFile();
            mImageUriFromFile = Uri.fromFile(imageFile);
            Log.i(TAG, "takePhoto: uriFromFile " + mImageUriFromFile);
            if (imageFile != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // 7.0 or above api, FileProvider will turn File into Uri
                    mImageUri = FileProvider.getUriForFile(this, FILE_PROVIDER_AUTHORITY, imageFile);
                } else {
                    // below 7.0 api just directly use Uri.fromFile method to turn File into Uri
                    mImageUri = Uri.fromFile(imageFile);
                }
                // pass output file's uri to camera
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                // open camera
                startActivityForResult(takePhotoIntent, TAKE_PHOTO);
            }
        } else {
            Toast.makeText(this,
                     "There was a problem accessing your device's external storage.",
                           Toast.LENGTH_LONG).show();
        }
    }

    /**
     * user open the album to choose images to be chosen
     *
     * @param view view
     */
    public void openAlbum(View view) {
        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image/*");

        // open the album
        startActivityForResult(openAlbumIntent, CHOOSE_PHOTO);
    }

    /**
     * user confirm the information required for this page, and send description, photos, {TODO videos}, {TODO location place}
     * to next activity
     *
     * @param view view
     */
    public void confirm(View view) {
        // has item and can proceed to next activity
        if (uploadArtifactAdapter.getItemCount() > 0) {
            Intent activityChangeIntent = new Intent(this, HappenedActivity.class);
            activityChangeIntent.putExtra(ARTIFACT_DESCRIPTION, editText.getText());
            activityChangeIntent.putExtra(ARTIFACT_IMAGES, (Serializable) uploadArtifactAdapter.getImages());
            // set navigation stack to empty
            activityChangeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activityChangeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(activityChangeIntent);
        // require users to input images
        } else {
            Toast.makeText(NewArtifactActivity.this, R.string.new_artifact_activity_proceed_next_hint, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    // ********************************* take photo logic *****************************************
    /**
     * used to create file for the image.
     * Note: use time as image name in order to avoid name collision.
     *
     * @return created image file
     */
    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = null;
        try {
            imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    // ************************************ input call back **************************************
    /**
     * permission request callback
     * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "onRequestPermissionsResult: permission granted");
        } else {
            Log.i(TAG, "onRequestPermissionsResult: permission denied");
            Toast.makeText(this, "You Denied Permission", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * activity's callback to the returned taken photo or chosen album image.
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // if camera successful, use BitmapFactory.decodeStream to turn image's Uri
                        // into Bitmap
                        InputStream is = getContentResolver().openInputStream(mImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                                    is,
                                                    null,
                                                    getCompressImageOption(this, mImageUri));

                        bitmap = rotateImage(Objects.requireNonNull(bitmap), 90);
                        Log.i(TAG, "onActivityResult: imageUri " + mImageUri);
                        galleryAddPic(mImageUriFromFile);

                        uploadArtifactAdapter.addData(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                // if no image chosen, return directly
                if (data == null) {
                    return;
                }
                Log.i(TAG, "onActivityResult: ImageUriFromAlbum: " + data.getData());
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        // 4.4 later image loading
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4 before image loading
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 4.4 below method to the returned image
     * Note: directly display image by using the Uri
     * @param data Uri returned after calling system album
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    /**
     * 4.4 above method to the returned image
     * Note: need to process Uri to get the true path
     * @param data Uri returned after calling system album
     */
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // if Uri is document type, then give document id to process
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                // 1: digital to get id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // if content is Uri type, by normal processing
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // if file type is Uri, directly get the path
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    /**
     * add image to the view
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, getCompressImageOption(imagePath));
            bitmap = rotateImage(Objects.requireNonNull(bitmap), 90);
            uploadArtifactAdapter.addData(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * turn Uri to path
     * @param uri Uri going to be turned
     * @param selection 4.4 above requires to process it, this param is required
     * @return path after transformed
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * add photo to system album
     *
     * @param uri photo's Uri
     */
    private void galleryAddPic(Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        sendBroadcast(mediaScanIntent);
    }
}
