package com.example.family_artifact_register.UI.ArtifactManager;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.MeFragmentPresenter;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.Util.MyArtifactsRecyclerViewAdapter;

import java.util.List;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-21 14:15:48
 * @description fragment for user to manage own artifacts
 */
public class MeFragment extends Fragment implements MeFragmentPresenter.IView {
    /**
     * class tag
     */
    private final String TAG = getClass().getSimpleName();

    // *********************************** recycler view *****************************************
    /**
     * recycler view to display user's artifacts
     */
    private RecyclerView mRecyclerView;

    /**
     * recycler view's layout manager
     */
    private LinearLayoutManager layoutManager;


    /**
     * recycler view adapter
     */
    private MyArtifactsRecyclerViewAdapter myArtifactsRecyclerViewAdapter;

    /**
     * the divider for the recycler view
     */
    private DividerItemDecoration dividerItemDecoration;
    // *******************************************************************************************

    private MeFragmentPresenter mfp;

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

        // create artifacts recycler view
        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.recycler_view_my_artifacts);
            layoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(layoutManager);
            myArtifactsRecyclerViewAdapter = new MyArtifactsRecyclerViewAdapter();
            mRecyclerView.setAdapter(myArtifactsRecyclerViewAdapter);
            dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
            mRecyclerView.addItemDecoration(dividerItemDecoration);
            Log.v(TAG, "recycler view created");
        } else {
            Log.e(TAG, "recycler view not created!!!");
        }

        // create presenter
        mfp = new MeFragmentPresenter(this);
    }

    /**
     * @return created me fragment
     */
    public static MeFragment newInstance() { return new MeFragment(); }

    // ********************************** implement presenter ************************************
    @Override
    public void addData(String time, String description, List<Uri> images, List<Uri> videos) {
        myArtifactsRecyclerViewAdapter.addData(time, description, images, videos);
    }
}
