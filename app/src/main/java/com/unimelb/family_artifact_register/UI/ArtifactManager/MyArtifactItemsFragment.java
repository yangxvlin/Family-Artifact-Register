package com.unimelb.family_artifact_register.UI.ArtifactManager;

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

import com.unimelb.family_artifact_register.IFragment;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.MeViewModelFactory;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.MyArtifactItemsViewModel;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.ArtifactManager.NewArtifact.ArtifactItemHeaderViewBinder;
import com.unimelb.family_artifact_register.UI.ArtifactManager.NewArtifact.ArtifactItemViewBinder;
import com.unimelb.family_artifact_register.UI.ArtifactManager.NewArtifact.StickyArtifactItemHeader;
import com.unimelb.family_artifact_register.UI.ArtifactManager.NewArtifact.StickyArtifactItemItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import tellh.com.stickyheaderview_rv.adapter.DataBean;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;

public class MyArtifactItemsFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = MyArtifactItemsFragment.class.getSimpleName();

    // *********************************** recycler view *****************************************
    /**
     * recycler view adapter
     */
    // private MyArtifactsRecyclerViewAdapter myArtifactsRecyclerViewAdapter;

    private StickyHeaderViewAdapter adapter;

    RecyclerView mRecyclerView;

    private MyArtifactItemsViewModel viewModel;

    public MyArtifactItemsFragment() {
        // Required empty public constructor
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
                .of(this, new MeViewModelFactory(getActivity().getApplication()))
                .get(MyArtifactItemsViewModel.class);

        // create recycler view
        mRecyclerView = view.findViewById(R.id.recycler_view_my_artifacts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
//        myArtifactsRecyclerViewAdapter = new MyArtifactsRecyclerViewAdapter(getContext());
//        mRecyclerView.setAdapter(myArtifactsRecyclerViewAdapter);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
//                mRecyclerView.getContext(),
//                layoutManager.getOrientation()
//        );
//        mRecyclerView.addItemDecoration(dividerItemDecoration);

        Log.v(TAG, "recycler view created");


        // add new artifact button
//        FloatingActionButton add = view.findViewById(R.id.fragment_me_floating_button_add);
//        add.setOnClickListener(view1 -> {
//            Intent intent = new Intent(getContext(), NewArtifactActivity2.class);
//            startActivity(intent);
//        });
//
//        // recycler view's scroll to hide the floating button
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                if (dy > 0) {
//                    add.hide();
//                } else if (dy < 0) {
//                    add.show();
//                }
//            }
//        });

        viewModel.getArtifactList().observe(this, new Observer<List<ArtifactItemWrapper>>() {
            @Override
            public void onChanged(List<ArtifactItemWrapper> artifactItemWrappers) {
//                mRecyclerView.removeAllViews();
//                Log.d(TAG, "enter onchange with adapter size: " + myArtifactsRecyclerViewAdapter.getItemCount());
//                myArtifactsRecyclerViewAdapter.setData(artifactItemWrappers);
//                Log.d(TAG, "enter onchange with adapter size: " + myArtifactsRecyclerViewAdapter.getItemCount());
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
