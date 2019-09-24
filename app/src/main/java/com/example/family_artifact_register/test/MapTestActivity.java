package com.example.family_artifact_register.test;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.MapServiceFragment.CurrentLocationFragment;
import com.example.family_artifact_register.UI.MapServiceFragment.MapDisplayFragment;
import com.example.family_artifact_register.UI.MapServiceFragment.MapSearchDisplayFragment;
import com.example.family_artifact_register.UI.MapServiceFragment.MyLocation;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e. status bar and
 * navigation/system bar) with user interaction.
 */
public class MapTestActivity extends AppCompatActivity {
    private static final String TAG = MapTestActivity.class.getSimpleName();

    // Create a new Places client instance
    protected PlacesClient mPlacesClient = null;

    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map_test);

        // Initialize the SDK
        Places.initialize(this, getString(R.string.google_api_key));

        // Get bottom bar
        CurrentLocationFragment currentLocationFragment = CurrentLocationFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.current_location_test, currentLocationFragment)
                .commit();

        List<MyLocation> myLocations = new ArrayList<>();
        mContentView = findViewById(R.id.fullscreen_content);

        MapDisplayFragment mapFragment = MapSearchDisplayFragment.newInstance(myLocations);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fullscreen_content, mapFragment)
                .commit();
        mPlacesClient = Places.createClient(this);

        List<String> placeIds = new ArrayList<>();
        placeIds.add("ChIJP3Sa8ziYEmsRUKgyFmh9AQM");
        placeIds.add("ChIJEVCBAZpmAGAR3vBoBTxlQdM");

        for (String placeId: placeIds) {
            // Specify the fields to return.
            List<Place.Field> placeFields = Arrays.asList(Place.Field.ID,
                    Place.Field.LAT_LNG,
                    Place.Field.NAME,
                    Place.Field.ADDRESS);
            // Construct a request object, passing the place ID and fields array.
            FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);
            mPlacesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                Log.i(TAG, response.toString());
                Place place = response.getPlace();
                Log.i(TAG, "Place found: " + place.getName());
                List<MyLocation> myLocations1 = mapFragment.getLocations();
                myLocations1.add(new MyLocation(place));
                mapFragment.setDisplayLocations(myLocations1);
            }).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    int statusCode = apiException.getStatusCode();
                    // Handle error with given status code.
                    Log.e(TAG, "Place not found: " + exception.getMessage());
                }
            });
        }
   }
}
