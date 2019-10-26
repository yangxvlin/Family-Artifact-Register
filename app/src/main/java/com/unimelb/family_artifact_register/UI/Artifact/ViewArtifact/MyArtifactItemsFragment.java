package com.unimelb.family_artifact_register.UI.Artifact.ViewArtifact;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.Util.IFragment;
import com.unimelb.family_artifact_register.PresentationLayer.Util.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactPresenter.ViewArtifactPresenter.MyArtifactItemsViewModelFactory;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactPresenter.ViewArtifactPresenter.MyArtifactItemsViewModel;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.Artifact.ViewArtifact.Util.ArtifactItemHeaderViewBinder;
import com.unimelb.family_artifact_register.UI.Artifact.ViewArtifact.Util.ArtifactItemViewBinder;
import com.unimelb.family_artifact_register.UI.Artifact.ViewArtifact.Util.StickyArtifactItemHeader;
import com.unimelb.family_artifact_register.UI.Artifact.ViewArtifact.Util.StickyArtifactItemItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import tellh.com.stickyheaderview_rv.adapter.DataBean;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;

/**
 * fragment for user to see artifact items history
 */
public class MyArtifactItemsFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = MyArtifactItemsFragment.class.getSimpleName();

    /**
     * sticky header recycler view
     */
    private StickyHeaderViewAdapter adapter;

    /**
     * recycler view
     */
    RecyclerView mRecyclerView;

    /**
     * view model
     */
    private MyArtifactItemsViewModel viewModel;

    /**
     * Required empty public constructor
     */
    public MyArtifactItemsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "My Artifact Items Fragment created");
        return inflater.inflate(R.layout.fragment_my_artifact_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders
                .of(this, new MyArtifactItemsViewModelFactory(getActivity().getApplication()))
                .get(MyArtifactItemsViewModel.class);

        // create recycler view
        mRecyclerView = view.findViewById(R.id.recycler_view_my_artifacts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        Log.v(TAG, "recycler view created");

        viewModel.getArtifactList().observe(this, new Observer<List<ArtifactItemWrapper>>() {
            @Override
            public void onChanged(List<ArtifactItemWrapper> artifactItemWrappers) {
                Collections.sort(artifactItemWrappers, new Comparator<ArtifactItemWrapper>() {
                    @Override
                    public int compare(ArtifactItemWrapper artifactItemWrapper, ArtifactItemWrapper t1) {
                        return -1 * artifactItemWrapper.getLastUpdateDateTime().compareTo(t1.getLastUpdateDateTime());
                    }
                });

                if (artifactItemWrappers.isEmpty()) {
                    return;
                }

                mRecyclerView.removeAllViews();
                List<DataBean> artifactItemList = new ArrayList<>();
                String currentPrefix = artifactItemWrappers.get(0).getUploadDateTime().substring(0, 7);
                artifactItemList.add(new StickyArtifactItemHeader(currentPrefix));

                for (ArtifactItemWrapper wrapper: artifactItemWrappers) {
                    Log.d(TAG, "size = " + artifactItemList.size());
                    String wrapperPrefix = wrapper.getUploadDateTime().substring(0, 7);
                    if (wrapperPrefix.equals(currentPrefix)) {
                        //artifactItemList.add(new StickyArtifactItemHeader(currentPrefix));
                        artifactItemList.add(new StickyArtifactItemItem(wrapper, getContext()));
                    } else {
                        currentPrefix = wrapperPrefix;
                        artifactItemList.add(new StickyArtifactItemHeader(currentPrefix));
                        artifactItemList.add(new StickyArtifactItemItem(wrapper, getContext()));
                    }
                    Log.d(TAG, "size = " + artifactItemList.size());
                }

                adapter = new StickyHeaderViewAdapter(artifactItemList)
                        .RegisterItemType(new ArtifactItemViewBinder())
                        .RegisterItemType(new ArtifactItemHeaderViewBinder());
                mRecyclerView.setAdapter(adapter);
            }
        });
    }

    /**
     * @return created MyArtifactItemsFragment
     */
    public static MyArtifactItemsFragment newInstance() { return new MyArtifactItemsFragment(); }
}
