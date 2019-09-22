package com.example.family_artifact_register;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.family_artifact_register.UI.ArtifactHub.HubFragment;
import com.example.family_artifact_register.UI.ArtifactManager.MeFragment;
import com.example.family_artifact_register.UI.MapServiceFragment.MapDisplayFragment;
import com.example.family_artifact_register.UI.MapServiceFragment.MapFragment;
import com.example.family_artifact_register.UI.Social.ContactFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-21 13:33:24
 * @description main activity let user to use the app
 */
public class MainActivity2 extends AppCompatActivity {
    /**
     * class tag
     */
    private final String TAG = getClass().getSimpleName();

    BottomNavigationView navigation;

    /**
     * fragment manager to manage fragments
     */
    private FragmentManager fm = getSupportFragmentManager();

    /**
     * Artifact hub page
     */
    private HubFragment hubFragment = HubFragment.newInstance();

    /**
     * Social contact page
     */
    private ContactFragment contactFragment = ContactFragment.newInstance();

    /**
     * Map Artifact page
     */
    private MapDisplayFragment mapFragment = MapDisplayFragment.newInstance();

    /**
     * Me page
     */
    private MeFragment meFragment = MeFragment.newInstance();

    /**
     * the fragment is active
     */
    Fragment active = hubFragment;

    /**
     * firebase authentication
     */
    private FirebaseAuth mFirebaseAuth;

    /**
     * firebase request code
     */
    public static final int RC_SIGN_IN = 1;

    /**
     * control firebase state info
     */
    private FirebaseAuth.AuthStateListener mAuthStateListner;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.PhoneBuilder().build());

    /**
     * bottom navigation item click listener to switch between fragments
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.item_hub:
                        setTitle(R.string.artifact_hub);
                        fm.beginTransaction().hide(active).show(hubFragment).commit();
                        active = hubFragment;
                        return true;
                    case R.id.item_contacts:
                        setTitle(R.string.bottom_bar_contacts);
                        fm.beginTransaction().hide(active).show(contactFragment).commit();
                        active = contactFragment;
                        return true;
                    case R.id.item_map:
                        setTitle(R.string.artifact_map);
                        fm.beginTransaction().hide(active).show(mapFragment).commit();
                        active = mapFragment;
                        return true;
                    case R.id.item_me:
                        setTitle(R.string.bottom_bar_profile);
                        fm.beginTransaction().hide(active).show(meFragment).commit();
                        active = meFragment;
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // set firebase sign in layout
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListner = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user!=null) {
                Toast.makeText(this, "User Signed In", Toast.LENGTH_SHORT).show();
            }
            else {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(providers)
                                .setLogo(R.drawable.icon_forget_me_not_1)
                                .build(),
                        RC_SIGN_IN
                );

            }
        };

        // setup bottom navigation bar
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fm.beginTransaction().add(R.id.main_view, meFragment).hide(meFragment).commit();
        fm.beginTransaction().add(R.id.main_view, mapFragment).hide(mapFragment).commit();
        fm.beginTransaction().add(R.id.main_view, contactFragment).hide(contactFragment).commit();
        fm.beginTransaction().add(R.id.main_view, hubFragment).commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        // avoid initial not updated bug
        String tag = ((IFragment)active).getFragmentTag();

        if (tag.equals(HubFragment.TAG)) {
            setTitle(R.string.artifact_hub);
        } else if (tag.equals(ContactFragment.TAG)) {
            setTitle(R.string.bottom_bar_contacts);
        } else if (tag.equals(MapFragment.TAG)) {
            setTitle(R.string.artifact_map);
        } else if (tag.equals(MeFragment.TAG)) {
            setTitle(R.string.bottom_bar_profile);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListner);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListner);
    }

    // **************************************** action bar menu ***********************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sign_out:
                Toast.makeText(this, R.string.menu_sign_out, Toast.LENGTH_SHORT).show();
                mFirebaseAuth.signOut();
                break;
        }
        return true;
    }
}
