package com.example.family_artifact_register.UI.MapServiceFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.family_artifact_register.MapsActivity;
import com.example.family_artifact_register.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.compat.GeoDataClient;
import com.google.android.libraries.places.compat.PlaceDetectionClient;
import com.google.android.libraries.places.compat.PlaceLikelihood;
import com.google.android.libraries.places.compat.PlaceLikelihoodBufferResponse;
import com.google.android.libraries.places.compat.Places;

public class CurrentLocationFragment extends Fragment {

    private static final String TAG = CurrentLocationFragment.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_LOCATION = 1;

    // The entry points to the Places API.
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // Stores the current user location

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted = false;

    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 5;
    private String[] mLikelyPlaceNames = new String[M_MAX_ENTRIES];
    private String[] mLikelyPlaceAddresses = new String[M_MAX_ENTRIES];
    private LatLng[] mLikelyPlaceLatLngs = new LatLng[M_MAX_ENTRIES];

    // Store Current location of user
    LatLng currentLatLng;
    String currentAddress;
    String currentPlaceName;



    public static CurrentLocationFragment newInstance() {
        return new CurrentLocationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_location, container, false);
        // Find the TextView and add listener to it
        Log.i(TAG, "Started onCreateView!");
        TextView mTextView = view.findViewById(R.id.my_location);
        mTextView.setOnClickListener(view1 -> openPlacesDialog());

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(getActivity());

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getActivity());

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        // Get current (possible) locations of user
        getLocationList();

        // set the location if the list is not empty
        if (mLikelyPlaceNames[0] !=  null) {
            currentPlaceName = mLikelyPlaceNames[0];
            currentAddress = mLikelyPlaceAddresses[0];
            currentLatLng = mLikelyPlaceLatLngs[0];
        }
        // Echo the place name
        Log.i(TAG, currentAddress);
        Log.i(TAG, currentPlaceName);

        return view;
    }

    public void autoSetLocation() {
        // Get permission
        // TODO implement

    }

    /**
     * Prompts the user to select the current place from a list of likely places, and shows the
     * current place on the map - provided the user has granted location permission.
     */
    private void getLocationList() {
        if (mLocationPermissionGranted) {
            // Get the likely places - that is, the businesses and other points of interest that
            // are the best match for the device's current location.
            @SuppressWarnings("MissingPermission") final Task<PlaceLikelihoodBufferResponse> placeResult =
                    mPlaceDetectionClient.getCurrentPlace(null);
            placeResult.addOnCompleteListener
                    (task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();

                            // Set the count, handling cases where less than 5 entries are returned.
                            int count;
                            if (likelyPlaces.getCount() < M_MAX_ENTRIES) {
                                count = likelyPlaces.getCount();
                            } else {
                                count = M_MAX_ENTRIES;
                            }

                            int i = 0;
                            mLikelyPlaceNames = new String[count];
                            mLikelyPlaceAddresses = new String[count];
                            mLikelyPlaceLatLngs = new LatLng[count];

                            for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                                // Build a list of likely places to show the user.
                                mLikelyPlaceNames[i] = (String) placeLikelihood.getPlace().getName();
                                mLikelyPlaceAddresses[i] = (String) placeLikelihood.getPlace()
                                        .getAddress();
                                mLikelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();

                                i++;
                                if (i > (count - 1)) {
                                    break;
                                }
                            }

                            // Release the place likelihood buffer, to avoid memory leaks.
                            likelyPlaces.release();
                        } else {
                            Log.e(TAG, "Exception: %s", task.getException());
                        }
                    });
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");
            // Prompt the user for permission.
            getLocationPermission();
        }
    }


    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // TODO Adapt the content here
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }


    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        Log.i(TAG, String.valueOf(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)));
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                //Prompt the user once explanation has been shown
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_LOCATION);
            }
        } else {
            mLocationPermissionGranted = true;
            getLocationList();
        }
    }

    /**
     * Displays a form allowing the user to select a place from a list of likely places.
     */
    private void openPlacesDialog() {
        // Try get a list of locations
        getLocationList();
        if (mLikelyPlaceNames[0] != null) {
            // Ask the user to choose the place where they are now.
            DialogInterface.OnClickListener listener = (dialog, which) -> {
                // The "which" argument contains the position of the selected item.
                // TODO adapt this to change the class attribute instead.
                currentPlaceName = mLikelyPlaceNames[which];
                currentLatLng = mLikelyPlaceLatLngs[which];
                currentAddress = mLikelyPlaceAddresses[which];
            };
            // Display the dialog.
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.pick_place)
                    .setItems(mLikelyPlaceNames, listener)
                    .show();
        }
    }

}
