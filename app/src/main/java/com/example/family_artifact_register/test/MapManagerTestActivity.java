package com.example.family_artifact_register.test;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocation;
import com.example.family_artifact_register.FoundationLayer.MapModel.MapLocationManager;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.MapServiceFragment.MapSearchDisplayFragment;

public class MapManagerTestActivity extends AppCompatActivity {

    MapSearchDisplayFragment mapSearchDisplayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_manager_test);

        mapSearchDisplayFragment = MapSearchDisplayFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.test_map_search_add,
                mapSearchDisplayFragment).commit();
    }


    public void onClickAddToDatabase(View view) {
        MapLocation mapLocation = mapSearchDisplayFragment.getSelectedLocation();
        if (mapLocation != null) {
            mapLocation.addImageUrl(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.drawable.my_logo).toString());
            MapLocationManager.getInstance().storeMapLocation(mapLocation);
        }
    }
}
