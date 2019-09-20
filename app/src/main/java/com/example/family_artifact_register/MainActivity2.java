package com.example.family_artifact_register;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.family_artifact_register.UI.ArtifactHub.HubFragment;
import com.example.family_artifact_register.UI.ArtifactManager.MeFragment;
import com.example.family_artifact_register.UI.MapServiceFragment.MapFragment;
import com.example.family_artifact_register.UI.Social.ContactFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {
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
    private MapFragment mapFragment = MapFragment.newInstance();

    /**
     * Me page
     */
    private MeFragment meFragment = MeFragment.newInstance();

    /**
     * the fragment is active
     */
    Fragment active = hubFragment;

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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fm.beginTransaction().add(R.id.mainview, meFragment).hide(meFragment).commit();
        fm.beginTransaction().add(R.id.mainview, mapFragment).hide(mapFragment).commit();
        fm.beginTransaction().add(R.id.mainview, contactFragment).hide(contactFragment).commit();
        fm.beginTransaction().add(R.id.mainview, hubFragment).commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        // avoid initial not updated bug
        setTitle(R.string.artifact_hub);
    }
}
