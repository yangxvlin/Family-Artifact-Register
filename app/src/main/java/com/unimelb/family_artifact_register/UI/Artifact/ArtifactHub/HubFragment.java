package com.unimelb.family_artifact_register.UI.Artifact.ArtifactHub;

import android.content.Intent;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.unimelb.family_artifact_register.PresentationLayer.Util.ArtifactPostWrapper;
import com.unimelb.family_artifact_register.UI.Artifact.ArtifactHub.Util.HubModelAdapter;
import com.unimelb.family_artifact_register.Util.IFragment;
import com.unimelb.family_artifact_register.PresentationLayer.Util.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.HubPresenter.HubFragmentPresenter;
import com.unimelb.family_artifact_register.PresentationLayer.HubPresenter.HubViewModel;
import com.unimelb.family_artifact_register.PresentationLayer.HubPresenter.HubViewModelFactory;
import com.unimelb.family_artifact_register.PresentationLayer.Util.UserInfoWrapper;
import com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.NewArtifactActivity2;
import com.unimelb.family_artifact_register.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Haichao Song 854035,
 * @time 2019-9-18 22:42:34
 * @description fragment to display a recycler view of artifact post item with floating buttons
 */
public class HubFragment extends Fragment implements HubFragmentPresenter.IView, IFragment {
    /**
     * class tag
     */
    public static final String TAG = HubFragment.class.getSimpleName();
    private List<ArtifactItemWrapper> artifactItemWrapperList;

    // *********************************** recycler view *****************************************
    /**
     * recycler view adapter
     */
    private HubModelAdapter hubModelAdapter;

    RecyclerView mRecyclerView;

    // *******************************************************************************************

    private HubFragmentPresenter hfp;

    private HubViewModel viewModel;

    public HubFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "hub fragment created");
        return inflater.inflate(R.layout.fragment_hub, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this, new HubViewModelFactory(getActivity().
                getApplication())).get(HubViewModel.class);

        // create artifacts recycler view
        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(layoutManager);
            hubModelAdapter = new HubModelAdapter(getContext(), viewModel);
            mRecyclerView.setAdapter(hubModelAdapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView
                    .getContext(), layoutManager.getOrientation());
            mRecyclerView.addItemDecoration(dividerItemDecoration);
            Log.v(TAG, "recycler view created");
        } else {
            Log.e(TAG, "recycler view not created!!!");
        }

        // create presenter
        hfp = new HubFragmentPresenter(this);

        // add new artifact
        FloatingActionButton add = view.findViewById(R.id.hub_fab);
        add.setOnClickListener(view1 -> {
            Log.d(TAG, "Press hub fab");
            Intent intent = new Intent(getContext(), NewArtifactActivity2.class);
            startActivity(intent);
        });

        // refresh hub
        FloatingActionButton refresh = view.findViewById(R.id.hub_refresh);
        refresh.setOnClickListener(view1 -> {
            viewModel.getPostsChange();
        });

        // recycler view's scroll to hide the floating button
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    add.hide();
                    refresh.hide();
                } else if (dy < 0) {
                    add.show();
                    refresh.show();
                }
            }
        });

        // get post information from view model
        viewModel.getPosts().observe(this, new Observer<List<ArtifactPostWrapper>>() {
            @Override
            public void onChanged(List<ArtifactPostWrapper> artifactPostWrappers) {
                hubModelAdapter.setData(artifactPostWrappers);
            }
        });

        // get friend information from view model
        viewModel.getFriends().observe(this, new Observer<List<UserInfoWrapper>>() {
            @Override
            public void onChanged(List<UserInfoWrapper> userInfoWrappers) {
                Log.d(TAG, "onChanged: get friends post change");
            }
        });

        // auto refresh when user come to hub page
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                viewModel.getPostsChange();
                Log.d(TAG, "task scheduled");
            }
        }, 1000);
    }

    /**
     * @return created me fragment
     */
    public static HubFragment newInstance() { return new HubFragment(); }

    // ********************************** implement presenter ************************************
    @Override
    public void addData(ArtifactItem artifactItem) {
        Log.d(TAG, "addData");
    }

    @Override
    public String getFragmentTag() { return TAG; }
}