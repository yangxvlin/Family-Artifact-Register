package com.unimelb.family_artifact_register.UI.Artifact.ArtifactDetail.Util;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactPresenter.ArtifactDetailPresenter.DetailFragmentPresenter;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactPresenter.ArtifactDetailPresenter.DetailViewModel;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactPresenter.ArtifactDetailPresenter.DetailViewModelFactory;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.Util.IFragment;

/**
 * @author Haichao Song 854035,
 * @time 2019-10-3 12:15:48
 * @description activity for artifact fragment. Deprecated because now detail page is an activity.
 */
@Deprecated
public class DetailFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = DetailFragment.class.getSimpleName();

    // *********************************** recycler view *****************************************
    /**
     * recycler view adapter
     */
    private DetailImageAdapter detailImageAdapter;

    RecyclerView mRecyclerView;

    // *******************************************************************************************

    private DetailFragmentPresenter dfp;

    private DetailViewModel viewModel;

    String Pid;

    public DetailFragment(String Pid) {
        // Required empty public constructor
        this.Pid = Pid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "detail fragment created");
        return inflater.inflate(R.layout.activity_artifact_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new DetailViewModelFactory(getActivity().getApplication()))
                .get(DetailViewModel.class);

        // create artifacts recycler view
        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(layoutManager);
            detailImageAdapter = new DetailImageAdapter(getContext());
            mRecyclerView.setAdapter(detailImageAdapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                    mRecyclerView.getContext(), layoutManager.getOrientation());
            mRecyclerView.addItemDecoration(dividerItemDecoration);
            Log.v(TAG, "recycler view created");
        } else {
            Log.e(TAG, "recycler view not created!!!");
        }
    }

    /**
     * @return created me fragment
     */
//    public static DetailFragment newInstance() { return new DetailFragment(); }

    // ********************************** implement presenter ************************************

    @Override
    public String getFragmentTag() { return TAG; }
}
