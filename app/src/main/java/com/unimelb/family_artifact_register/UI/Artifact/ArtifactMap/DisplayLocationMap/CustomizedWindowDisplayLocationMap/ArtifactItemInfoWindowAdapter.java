package com.unimelb.family_artifact_register.UI.Artifact.ArtifactMap.DisplayLocationMap.CustomizedWindowDisplayLocationMap;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.unimelb.family_artifact_register.PresentationLayer.Util.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Deprecated because can be used to customize marker's layout bu we don't need it
 * not deleted by the open-close principle
 */
@Deprecated
public class ArtifactItemInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;

    private ArtifactItemWrapper artifactItemWrapper;

    public ArtifactItemInfoWindowAdapter(LayoutInflater inflater,
                                         ArtifactItemWrapper ArtifactItemWrapper) {
        mWindow = inflater.inflate(R.layout.marker_artifact_item, null);
        this.artifactItemWrapper = ArtifactItemWrapper;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        TextView creationTime = mWindow.findViewById(R.id.artifact_item_create_time);
        creationTime.setText(artifactItemWrapper.getUploadDateTime());

        TextView description = mWindow.findViewById(R.id.artifact_item_description);
        description.setText(artifactItemWrapper.getDescription());
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
