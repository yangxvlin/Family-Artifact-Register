package com.example.family_artifact_register;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.family_artifact_register.UI.ArtifactHub.HubFragment;
import com.example.family_artifact_register.UI.ArtifactManager.MeFragment;
import com.example.family_artifact_register.UI.MapServiceFragment.MapFragment;
import com.example.family_artifact_register.UI.Social.ContactFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {
    private FragmentTransaction transaction;
    private FragmentManager fm = getSupportFragmentManager();

    private HubFragment hubFragment = HubFragment.newInstance();
    private ContactFragment contactFragment = ContactFragment.newInstance();
    private MapFragment mapFragment = MapFragment.newInstance();
    private MeFragment meFragment = MeFragment.newInstance();
    Fragment active = hubFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.item_hub:
                        setTitle(R.string.artifact_hub);
//                        showFragment(1);
                        fm.beginTransaction().hide(active).show(hubFragment).commit();
                        active = hubFragment;
                        return true;
                    case R.id.item_contacts:
                        setTitle(R.string.bottom_bar_contacts);
//                        showFragment(2);
                        fm.beginTransaction().hide(active).show(contactFragment).commit();
                        active = contactFragment;
                        return true;
                    case R.id.item_map:
                        setTitle(R.string.artifact_map);
//                        showFragment(3);
                        fm.beginTransaction().hide(active).show(mapFragment).commit();
                        active = mapFragment;
                        return true;
                    case R.id.item_me:
                        setTitle(R.string.bottom_bar_profile);
//                        showFragment(4);
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

        //默认选中第一个tab
//        showFragment(1);
//        setDefaultFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        // avoid initial not updated bug
        setTitle(R.string.artifact_hub);
    }

    // ********************************** fragment manage ***************************************
    private void showFragment(int page) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // 想要显示一个fragment,先隐藏所有fragment，防止重叠
        hideFragments(ft);
        switch (page) {
            case 1:
                // 如果fragment1已经存在则将其显示出来
                if (hubFragment != null)
                    ft.show(hubFragment);
                    // 否则添加hubFragment，注意添加后是会显示出来的，replace方法也是先remove后add
                else {
                    hubFragment = new HubFragment();
                    ft.add(R.id.mainview, hubFragment);
                    transaction.addToBackStack(null);
                }
                break;
            case 2:
                if (contactFragment != null)
                    ft.show(contactFragment);
                else {
                    contactFragment = new ContactFragment();
                    ft.add(R.id.mainview, contactFragment);
                    transaction.addToBackStack(null);
                }
                break;
            case 3:
                if (mapFragment != null) {
                    ft.show(mapFragment);
                }
                else {
                    mapFragment = new MapFragment();
                    ft.add(R.id.mainview, mapFragment);
                    transaction.addToBackStack(null);
                }
                break;
            case 4:
                if (meFragment != null){
                    ft.show(meFragment);
                }
                else {
                    meFragment = new MeFragment();
                    ft.add(R.id.mainview, meFragment);
                    transaction.addToBackStack(null);
                }
                break;
        }
        ft.commit();
    }
    // 当fragment已被实例化，相当于发生过切换，就隐藏起来
    public void hideFragments(FragmentTransaction ft) {
        if (hubFragment != null)
            ft.hide(hubFragment);
        if (contactFragment != null)
            ft.hide(contactFragment);
        if (mapFragment != null)
            ft.hide(mapFragment);
        if (meFragment != null)
            ft.hide(meFragment);
    }
}
