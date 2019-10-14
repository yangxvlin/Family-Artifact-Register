package com.example.family_artifact_register.UI.Event;

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
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class EventFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = EventFragment.class.getSimpleName();

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "event fragment created");
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
//                .add(R.string.title, PageFragment.class)
//                .add(R.string.title, WithArgumentsPageFragment.class, new Bundler().putString("key", "value").get()),
//                 .add("title", PageFragment.class)
                .add(getString(R.string.event_recommended_title), RecommendedEventFragment.class)
                .add(getString(R.string.event_my_title), MyEventFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.fragment_event_view_pager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.fragment_event_view_pager_tab);
        viewPagerTab.setViewPager(viewPager);
    }

    /**
     * @return created me fragment
     */
    public static EventFragment newInstance() { return new EventFragment(); }
}
