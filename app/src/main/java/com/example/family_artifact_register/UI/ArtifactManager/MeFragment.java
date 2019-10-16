package com.example.family_artifact_register.UI.ArtifactManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.ArtifactManager.ViewMyArtifact.ViewMyArtifactItemsFragment;
import com.example.family_artifact_register.UI.ArtifactManager.ViewMyArtifact.ViewMyArtifactTimelinesFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-21 14:15:48
 * @description fragment for user to manage own artifact items and artifact timelines
 */
public class MeFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = MeFragment.class.getSimpleName();

    /**
     * Required empty public constructor
     */
    public MeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "me fragment created");
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(getString(R.string.my_artifact_items_title), ViewMyArtifactItemsFragment.class)
                .add(getString(R.string.my_artifact_timelines_title), ViewMyArtifactTimelinesFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.fragment_me_view_pager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.fragment_me_view_pager_tab);
        viewPagerTab.setViewPager(viewPager);
    }

    /**
     * @return created me fragment
     */
    public static MeFragment newInstance() { return new MeFragment(); }
}
