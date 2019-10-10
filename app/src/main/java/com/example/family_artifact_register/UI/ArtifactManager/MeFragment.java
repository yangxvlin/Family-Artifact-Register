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

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.MeFragmentPresenter;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.MeViewModel;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.MeViewModelFactory;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.Util.MyArtifactsRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-21 14:15:48
 * @description fragment for user to manage own artifacts
 */
//public class MeFragment extends Fragment implements MeFragmentPresenter.IView, View.OnClickListener {
public class MeFragment extends Fragment implements MeFragmentPresenter.IView, IFragment {
    /**
     * class tag
     */
    public static final String TAG = MeFragment.class.getSimpleName();

    // *********************************** recycler view *****************************************
    /**
     * recycler view adapter
     */
    private MyArtifactsRecyclerViewAdapter myArtifactsRecyclerViewAdapter;

    RecyclerView mRecyclerView;

    // *******************************************************************************************

    private MeFragmentPresenter mfp;

    private MeViewModel viewModel;

    public MeFragment() {
        // Required empty public constructor
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

        viewModel = ViewModelProviders.of(this, new MeViewModelFactory(getActivity().getApplication())).get(MeViewModel.class);

        // create artifacts recycler view
        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.recycler_view_my_artifacts);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(layoutManager);
            myArtifactsRecyclerViewAdapter = new MyArtifactsRecyclerViewAdapter(getContext());
            mRecyclerView.setAdapter(myArtifactsRecyclerViewAdapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
            mRecyclerView.addItemDecoration(dividerItemDecoration);
            Log.v(TAG, "recycler view created");
        } else {
            Log.e(TAG, "recycler view not created!!!");
        }

        // create presenter
        mfp = new MeFragmentPresenter(this);

        // add new artifact
        FloatingActionButton add = getView().findViewById(R.id.fragment_me_floating_button_add);
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
                Log.d(TAG, "enter onchange with adapter size: " + myArtifactsRecyclerViewAdapter.getItemCount());
                myArtifactsRecyclerViewAdapter.setData(artifactItemWrappers);
                Log.d(TAG, "enter onchange with adapter size: " + myArtifactsRecyclerViewAdapter.getItemCount());

            }
        });
    }

    /**
     * @return created me fragment
     */
    public static MeFragment newInstance() { return new MeFragment(); }

    // ********************************** implement presenter ************************************
    @Override
    public void addData(ArtifactItem artifactItem) {
        Log.e(getFragmentTag(), "illegal access !!!");
//        myArtifactsRecyclerViewAdapter.addData(artifactItem);
    }

    @Override
    public String getFragmentTag() { return TAG; }
}
