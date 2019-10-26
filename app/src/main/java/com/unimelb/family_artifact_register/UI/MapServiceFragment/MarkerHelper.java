package com.unimelb.family_artifact_register.UI.MapServiceFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.Util.Pair;
import com.unimelb.family_artifact_register.R;

import java.io.IOException;

import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;
import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.cropCenter;
import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.getResizedBitmap;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getVideoThumbNail;

public class MarkerHelper {
    public static final String TAG = MarkerHelper.class.getSimpleName();

    public static MarkerOptions setUpMarker(Pair<ArtifactItemWrapper, MapLocation> pair, String title, String snippet) {
        MapLocation storeLocation = pair.getSnd();

        // Log.d(getFragmentTag(), "store location = " + storeLocation.toString());

        MarkerOptions opt = new MarkerOptions()
                .position(new LatLng(storeLocation.getLatitude(),
                        storeLocation.getLongitude()))
                .title(title)
                .snippet(snippet);
        return opt;
    }

    public static MarkerOptions setUpMarker(Pair<ArtifactItemWrapper, MapLocation> pair, Context context, int width, int height, String title, String snippet) {
        ArtifactItemWrapper artifactItemWrapper = pair.getFst();
        MapLocation storeLocation = pair.getSnd();

        // Log.d(getFragmentTag(), "store location = " + storeLocation.toString());

        MarkerOptions opt = new MarkerOptions()
                .position(new LatLng(storeLocation.getLatitude(),
                        storeLocation.getLongitude()))
                .title(title)
                .snippet(snippet);

        if (artifactItemWrapper.getMediaType() == TYPE_IMAGE) {
            try {
                Uri uri = Uri.parse(artifactItemWrapper.getLocalMediaDataUrls().get(0));

                if (uri.getScheme() == null) {
                    uri = Uri.parse("file://" + uri.toString());
                    Log.d(TAG, "uri = " + uri);
                }

                Bitmap mBitmap = MediaStore.Images.Media.getBitmap(
                        context.getContentResolver(),
                        uri
                );
                mBitmap = getResizedBitmap(cropCenter(mBitmap), width, height);
                opt.icon(BitmapDescriptorFactory.fromBitmap(mBitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (artifactItemWrapper.getMediaType() == TYPE_VIDEO) {
            Bitmap mBitmap = getVideoThumbNail(artifactItemWrapper.getLocalMediaDataUrls()
                    .get(0));
            mBitmap = getResizedBitmap(cropCenter(mBitmap), width, height);
            opt.icon(BitmapDescriptorFactory.fromBitmap(mBitmap));
        } else {
            Log.e(TAG, "unknown media Type !!!");
        }

        return opt;
    }

    public static String getSnippet(Pair<ArtifactItemWrapper, MapLocation> pair, Context context) {

        return String.format("%s%s\n%s%s\n%s\n%s\n%s\n",
                context.getString(R.string.description), pair.getFst().getDescription(),
                context.getString(R.string.happen_at), pair.getFst().getHappenedDateTime(),
                getAddress(pair, context, context.getString(R.string.locate_at)),
                pair.getFst().getPostId(),
                pair.getFst().getArtifactTimelineId()
        );
    }

    public static String getCreateAt(Pair<ArtifactItemWrapper, MapLocation> pair, Context context) {
        return context.getString(R.string.create_at) + pair.getFst().getUploadDateTime();
    }

    public static String getAddress(Pair<ArtifactItemWrapper, MapLocation> pair, Context context, String hint) {
        String snippet = hint;

        if (pair.getSnd().getAddress() != null) {
            snippet += pair.getSnd().getAddress();
        } else {
            snippet += "(" + pair.getSnd().getLatitude() + ", " + pair.getSnd().getLongitude() + ")";
        }

        return snippet;
    }


}
