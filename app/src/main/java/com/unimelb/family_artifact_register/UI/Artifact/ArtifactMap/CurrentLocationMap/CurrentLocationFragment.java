package com.unimelb.family_artifact_register.UI.Artifact.ArtifactMap.CurrentLocationMap;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.unimelb.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.Artifact.ArtifactMap.Util.BasePlacesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * fragment to detect user's current location
 */
public class CurrentLocationFragment extends BasePlacesFragment {
    /**
     * class tag
     */
    private static final String TAG = CurrentLocationFragment.class.getSimpleName();

    /**
     * Success code for location access permission
     */
    private static final int PERMISSIONS_REQUEST_LOCATION = 1;

    /**
     * Records the permission status
     */
    private boolean mLocationPermissionGranted = false;

    /**
     * Used for selecting the current place.
     */
    private static final int M_MAX_ENTRIES = 5;

    /**
     * Stores location information related to the placed detected by GPS
     */
    private List<Place> mLikelyPlace = new ArrayList<>();

    /**
     * Stores the current user location
     */
    private Place currentPlace;

    /**
     * map view
     */
    private View view;

    /**
     * @return created fragment
     */
    public static CurrentLocationFragment newInstance() {
        return new CurrentLocationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_current_location, container, false);
        // Find the TextView and add listener to it
        TextView mTextView = view.findViewById(R.id.my_location);
        mTextView.setText(R.string.no_location);
        mTextView.setOnClickListener(view1 -> openPlacesDialog());
        // Try set location (if already have permission)
        getLocationListAndUpdateLocation();
        return view;
    }

    /**
     * This method updates the displayed location field by the currently stored place
     */
    private void updateLocationField() {
        TextView mTextView = view.findViewById(R.id.my_location);
        if (currentPlace != null) {
            String location = currentPlace.getName() + "\n" + currentPlace.getAddress();
            mTextView.setText(location);
        } else {
            mTextView.setText(R.string.no_location);
        }
    }

    /**
     * get Location List And Update Location
     */
    private void getLocationListAndUpdateLocation() {
        if (mLocationPermissionGranted) {
            // Use fields to define the data types to return.
            List<Place.Field> placeFields = new ArrayList<>();
            placeFields.add(Place.Field.ID);
            placeFields.add(Place.Field.NAME);
            placeFields.add(Place.Field.ADDRESS);
            placeFields.add(Place.Field.PHOTO_METADATAS);
            placeFields.add(Place.Field.LAT_LNG);
            // Use the builder to create a FindCurrentPlaceRequest.
            FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);
            // Get the likely locations - that is, the businesses and other points of interest that
            // are the best match for the device's current location.
            @SuppressWarnings("MissingPermission") final Task<FindCurrentPlaceResponse> placeResult =
                    mPlacesClient.findCurrentPlace(request);
            // This is an async task that need a listener to execute actions when completed
            placeResult.addOnCompleteListener
                    (task -> {
                        if (task.isSuccessful()){
                            FindCurrentPlaceResponse response = task.getResult();
                            mLikelyPlace.clear();
                            for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                                Log.i(TAG, String.format("Place '%s' has likelihood: %f",
                                        placeLikelihood.getPlace().getName(),
                                        placeLikelihood.getLikelihood()));
                                mLikelyPlace.add(placeLikelihood.getPlace());
                            }
                        } else {
                            Exception exception = task.getException();
                            if (exception instanceof ApiException) {
                                ApiException apiException = (ApiException) exception;
                                Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                            }
                        }
                        if (mLikelyPlace.size() !=  0) {
                            currentPlace = mLikelyPlace.get(0);
                        }
                        updateLocationField();
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
        mLocationPermissionGranted = false;
        switch (requestCode) {
            // If the request is success
            case PERMISSIONS_REQUEST_LOCATION: {
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
            getLocationListAndUpdateLocation();
        }
    }

    /**
     * Prompts the user to select the current place from a list of likely locations with a dialog.
     */
    private void openPlacesDialog() {
        if (mLikelyPlace.size() != 0) {
            // Ask the user to choose the place where they are now.
            DialogInterface.OnClickListener listener = (dialog, which) -> {
                // The "which" argument contains the position of the selected item.
                currentPlace = mLikelyPlace.get(which);
                updateLocationField();
            };
            ;
            // Display the dialog for location selection.
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.pick_place)
                    .setItems(mLikelyPlace.stream()
                                    .map(Place::getName)
                                    .toArray(String[]::new),
                            listener)
                    .show();
        } else {
            getLocationListAndUpdateLocation();
            Toast.makeText(getActivity(), "Fetching Locations, Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @return return the map location of current location
     */
    public MapLocation getLocation() {
        MapLocation location = null;
        if (currentPlace != null) {
            location = MapLocation.newInstance(currentPlace);
        } else {
            location = new MapLocation();
        }
        return location;
    }
}
