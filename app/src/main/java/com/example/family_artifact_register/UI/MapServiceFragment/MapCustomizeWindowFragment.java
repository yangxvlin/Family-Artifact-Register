package com.example.family_artifact_register.UI.MapServiceFragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.appolica.interactiveinfowindow.InfoWindowManager;
import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.PresentationLayer.Util.Pair;
import com.example.family_artifact_register.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.cropCenter;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.getResizedBitmap;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getVideoThumbNail;

public class MapCustomizeWindowFragment extends MapDisplayFragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = MapCustomizeWindowFragment.class.getSimpleName();

    private InfoWindowManager infoWindowManager;

    /**
     * Required empty public constructor
     */
    public MapCustomizeWindowFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_map_customize_window, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void setDisplayArtifactItems(List<Pair<ArtifactItemWrapper, MapLocation>> artifactItems) {
        // Only display with marker if map is not null and there are locations stored
        if (mMap != null && artifactItems.size() != 0) {
            mMap.clear();
            List<Marker> markers = new ArrayList<>();
            for (Pair<ArtifactItemWrapper, MapLocation> pair : artifactItems) {
                ArtifactItemWrapper artifactItemWrapper = pair.getFst();
                MapLocation storeLocation = pair.getSnd();

                // Log.d(getFragmentTag(), "store location = " + storeLocation.toString());

                MarkerOptions opt = new MarkerOptions()
                        .position(new LatLng(storeLocation.getLatitude(),
                                storeLocation.getLongitude()))
                        .title(getContext().getString(R.string.create_at) + artifactItemWrapper.getUploadDateTime())
                        .snippet(getSnippet(pair));

                if (artifactItemWrapper.getMediaType() == TYPE_IMAGE) {
                    try {
                        Uri uri = Uri.parse(artifactItemWrapper.getLocalMediaDataUrls().get(0));

                        if (uri.getScheme() == null) {
                            uri = Uri.parse("file://" + uri.toString());
                            Log.d(getFragmentTag(), "uri = " + uri);
                        }

                        Bitmap mBitmap = MediaStore.Images.Media.getBitmap(
                                getContext().getContentResolver(),
                                uri
                        );
                        mBitmap = getResizedBitmap(cropCenter(mBitmap), IMAGE_DEFAULT_WIDTH, IMAGE_DEFAULT_HEIGHT);
                        opt.icon(BitmapDescriptorFactory.fromBitmap(mBitmap));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (artifactItemWrapper.getMediaType() == TYPE_VIDEO) {
                    Bitmap mBitmap = getVideoThumbNail(artifactItemWrapper.getLocalMediaDataUrls()
                            .get(0));
                    mBitmap = getResizedBitmap(cropCenter(mBitmap), IMAGE_DEFAULT_WIDTH, IMAGE_DEFAULT_HEIGHT);
                    opt.icon(BitmapDescriptorFactory.fromBitmap(mBitmap));
                } else {
                    Log.e(getFragmentTag(), "unknown media Type !!!");
                }

                markers.add(mMap.addMarker(opt));
            }
            CameraUpdate cu = MarkerZoomStrategyFactory
                    .getMarkerZoomStrategyFactory()
                    .getMarkerZoomStrategy(markers.size())
                    .makeCameraUpdate(markers);
            mMap.animateCamera(cu);
        }
    }

    /**
     *
     */
    public static MapCustomizeWindowFragment newInstance() {
        return new MapCustomizeWindowFragment();
    }
}
