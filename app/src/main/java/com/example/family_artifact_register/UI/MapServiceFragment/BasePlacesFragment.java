package com.example.family_artifact_register.UI.MapServiceFragment;

import androidx.fragment.app.Fragment;

import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.R;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

abstract class BasePlacesFragment extends Fragment implements IFragment {
    // Add tag for logging
    private static final String TAG = BasePlacesFragment.class.getSimpleName();

    // Create a new Places client instance
    protected PlacesClient mPlacesClient = null;

    /**
     * Initialize Places. For simplicity, the API key is hard-coded. In a production
     * environment we recommend using a secure mechanism to manage API keys.
     */
    public BasePlacesFragment() {
        // Initialize the SDK
        Places.initialize(getActivity(), getString(R.string.google_api_key));
        this.mPlacesClient = Places.createClient(getActivity());
    }
}
