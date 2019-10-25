package com.unimelb.family_artifact_register.UI.Artifact.ArtifactMap.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.unimelb.family_artifact_register.PresentationLayer.Util.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.Util.Pair;
import com.unimelb.family_artifact_register.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;
import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.cropCenter;
import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.getResizedBitmap;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getVideoThumbNail;

/**
 * pure fabricate logic to set up marker to reduce duplicate code
 */
public class MarkerHelper {
    /**
     * class tag
     */
    public static final String TAG = MarkerHelper.class.getSimpleName();

    /**
     * @param pair pair of ArtifactItemWrapper and MapLocation
     * @param title title of marker
     * @param snippet snippet of marker
     * @return marker to be displayed
     */
    public static MarkerOptions setUpMarker(Pair<ArtifactItemWrapper, MapLocation> pair, String title, String snippet) {
        MapLocation storeLocation = pair.getSnd();

        MarkerOptions opt = new MarkerOptions()
                .position(new LatLng(storeLocation.getLatitude(),
                        storeLocation.getLongitude()))
                .title(title)
                .snippet(snippet);
        return opt;
    }

    /**
     * @param pair pair of ArtifactItemWrapper and MapLocation
     * @param context context
     * @param width width of icon
     * @param height height of icon
     * @param title title of marker
     * @param snippet snippet of marker
     * @return marker to be displayed
     */
    public static MarkerOptions setUpMarker(Pair<ArtifactItemWrapper, MapLocation> pair, Context context, int width, int height, String title, String snippet) {
        ArtifactItemWrapper artifactItemWrapper = pair.getFst();
        MapLocation storeLocation = pair.getSnd();

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

    /**
     * @param pair pair of ArtifactItemWrapper and MapLocation
     * @param context context
     * @return turn required data to a single string separated by new line
     */
    public static String getSnippet(Pair<ArtifactItemWrapper, MapLocation> pair, Context context) {
        return String.format("%s%s\n%s%s\n%s\n%s\n%s\n",
                context.getString(R.string.description), pair.getFst().getDescription(),
                context.getString(R.string.happen_at), pair.getFst().getHappenedDateTime(),
                getAddress(pair, context, context.getString(R.string.locate_at)),
                pair.getFst().getPostId(),
                pair.getFst().getArtifactTimelineId()
        );
    }

    /**
     * @param pair pair of ArtifactItemWrapper and MapLocation
     * @param context context
     * @return formatted string of creation time
     */
    public static String getCreateAt(Pair<ArtifactItemWrapper, MapLocation> pair, Context context) {
        return context.getString(R.string.create_at) + pair.getFst().getUploadDateTime();
    }

    /**
     * @param pair pair of ArtifactItemWrapper and MapLocation
     * @param context context
     * @param hint string about the output
     * @return formatted string of address if no specific address use lat and lon as address
     */
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
