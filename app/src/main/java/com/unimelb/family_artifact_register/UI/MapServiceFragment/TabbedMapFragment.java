package com.unimelb.family_artifact_register.UI.MapServiceFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.unimelb.family_artifact_register.Util.IFragment;
import com.unimelb.family_artifact_register.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class TabbedMapFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = TabbedMapFragment.class.getSimpleName();

    public TabbedMapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "me fragment created");
        return inflater.inflate(R.layout.fragment_tabbed_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.artifact_map_title);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(getString(R.string.artifact_items_map_happened_title), AllArtifactHappenedMapFragment.class)
                .add(getString(R.string.artifact_items_map_stored_title), AllArtifactStoredMapFragment.class)
                // .add(getString(R.string.me_my_title), MyEventFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.fragment_tabbed_map_view_pager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.fragment_tabbed_map_view_pager_tab);
        viewPagerTab.setViewPager(viewPager);
    }

    /**
     * @return created me fragment
     */
    public static TabbedMapFragment newInstance() { return new TabbedMapFragment(); }
}
