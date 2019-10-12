package com.example.family_artifact_register.UI.ArtifactManager;

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

import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.MeViewModelFactory;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.MyArtifactItemsViewModel;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.ArtifactManager.NewArtifact.NewArtifactActivity2;
import com.example.family_artifact_register.UI.Util.MyArtifactsRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MyArtifactItemsFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = MyArtifactItemsFragment.class.getSimpleName();

    // *********************************** recycler view *****************************************
    /**
     * recycler view adapter
     */
    private MyArtifactsRecyclerViewAdapter myArtifactsRecyclerViewAdapter;

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
        myArtifactsRecyclerViewAdapter = new MyArtifactsRecyclerViewAdapter(getContext());
        mRecyclerView.setAdapter(myArtifactsRecyclerViewAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(),
                layoutManager.getOrientation()
        );
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        Log.v(TAG, "recycler view created");


        // add new artifact button
        FloatingActionButton add = view.findViewById(R.id.fragment_me_floating_button_add);
        add.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), NewArtifactActivity2.class);
            startActivity(intent);
        });

        // recycler view's scroll to hide the floating button
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    add.hide();
                } else if (dy < 0) {
                    add.show();
                }
            }
        });

        viewModel.getArtifactList().observe(this, new Observer<List<ArtifactItemWrapper>>() {
            @Override
            public void onChanged(List<ArtifactItemWrapper> artifactItemWrappers) {
//                mRecyclerView.removeAllViews();
                Log.d(TAG, "enter onchange with adapter size: " + myArtifactsRecyclerViewAdapter.getItemCount());
                myArtifactsRecyclerViewAdapter.setData(artifactItemWrappers);
                Log.d(TAG, "enter onchange with adapter size: " + myArtifactsRecyclerViewAdapter.getItemCount());

            }
        });
    }

    /**
     * @return created MyArtifactItemsFragment
     */
    public static MyArtifactItemsFragment newInstance() { return new MyArtifactItemsFragment(); }
}
