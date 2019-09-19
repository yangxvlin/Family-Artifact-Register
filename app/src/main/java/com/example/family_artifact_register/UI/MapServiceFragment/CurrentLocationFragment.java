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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.family_artifact_register.R;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.compat.Place;
import com.google.android.libraries.places.compat.PlaceDetectionClient;
import com.google.android.libraries.places.compat.PlaceLikelihood;
import com.google.android.libraries.places.compat.PlaceLikelihoodBufferResponse;
import com.google.android.libraries.places.compat.Places;

public class CurrentLocationFragment extends Fragment {

    private static final String TAG = CurrentLocationFragment.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_LOCATION = 1;

    // The entry points to the Places API.
    private PlaceDetectionClient mPlaceDetectionClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted = false;

    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 5;
    private Place[] mLikelyPlace = new Place[M_MAX_ENTRIES];
    private String[] mLikelyPlaceNames = new String[M_MAX_ENTRIES];

    // Stores the current user location
    private Place currentPlace;
    private View view;

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
        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getActivity());
        // Try set location (if already have permission)
        getLocationListAndUpdateLocation();
        return view;
    }

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
     * Prompts the user to select the current place from a list of likely places, and shows the
     * current place on the map - provided the user has granted location permission.
     */
    private void getLocationListAndUpdateLocation() {
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

                            mLikelyPlace = new Place[M_MAX_ENTRIES];

                            for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                                // Build a list of likely places to show the user if user want to
                                // open the dialog
                                mLikelyPlace[i] = placeLikelihood.getPlace();
                                mLikelyPlaceNames[i] = (String) mLikelyPlace[i].getName();
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
                        if (mLikelyPlace[0] !=  null) {
                            currentPlace = mLikelyPlace[0];
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
            getLocationListAndUpdateLocation();
        }
    }

    /**
     * Displays a form allowing the user to select a place from a list of likely places.
     */
    private void openPlacesDialog() {
        if (mLikelyPlace[0] != null) {
            // Ask the user to choose the place where they are now.
            DialogInterface.OnClickListener listener = (dialog, which) -> {
                // The "which" argument contains the position of the selected item.
                currentPlace = mLikelyPlace[which];
                updateLocationField();
            };

            // Display the dialog.
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.pick_place)
                    .setItems(mLikelyPlaceNames, listener)
                    .show();
        } else {
            getLocationListAndUpdateLocation();
            Toast.makeText(getActivity(), "Fetching Locations, Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public Place getPlace() {
        return currentPlace;
    }
}
