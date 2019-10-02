package com.example.family_artifact_register.UI.ArtifactHub;

import android.content.Intent;
import android.database.Observable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.PresentationLayer.HubPresenter.HubViewModel;
import com.example.family_artifact_register.UI.Upload.PostActivity;
import com.example.family_artifact_register.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HubFragment extends Fragment implements IFragment {

    /**
     * class tag
     */
    public static final String TAG = HubFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private DividerItemDecoration divider;

    private HubViewModel hubViewModel;

    public HubFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hub, container, false);
        setupRecyclerView(view);

        FloatingActionButton fab = view.findViewById(R.id.hub_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), PostActivity.class));
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) {
                    fab.hide();
                }
                else if(dy < 0) {
                    fab.show();
                }
            }
        });

        Observer<List<ArtifactItem>> postObserver = new Observer<List<ArtifactItem>>() {
            @Override
            public void onChanged(List<ArtifactItem> artifactItems) {
                adapter.setData(artifactItems);
            }
        };

        hubViewModel.getContacts().observe(this, postObserver);

        return view;
    }

    private void setupRecyclerView(View view) {
        // get the view
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // set layout manager for the view
        layoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        // set the adapter for the view
        adapter = new HubModelAdapter(hubViewModel);
        mRecyclerView.setAdapter(adapter);

        // set the divider between list item
        divider = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }

    public static HubFragment newInstance() { return new HubFragment(); }


//    /**
//     * Get a list of models of card view
//     * @return a list of models
//     */
//    private ArrayList<Model> getMyList(){
//        ArrayList<Model> models = new ArrayList<Model>();
//        Model m = new Model();
//        m.setUsername("Dudu");
//        m.setDescription("This is Art1 Description.");
//        m.setAvatar(R.drawable.my_logo);
//        m.setPostimage(R.drawable.my_logo);
//        m.setPublisher("Liguo Chen");
//        models.add(m);
//
//        m = new Model();
//        m.setUsername("Peter");
//        m.setDescription("This is Art2 Description.");
//        m.setAvatar(R.drawable.my_logo);
//        m.setPostimage(R.drawable.my_logo);
//        m.setPublisher("Liguo Chen");
//        models.add(m);
//
//        m = new Model();
//        m.setUsername("Calvin");
//        m.setDescription("This is Art3 Description.");
//        m.setAvatar(R.drawable.my_logo);
//        m.setPostimage(R.drawable.my_logo);
//        m.setPublisher("Zhuoqun Huang");
//        models.add(m);
//
//        m = new Model();
//        m.setUsername("George");
//        m.setDescription("This is Art4 Description.");
//        m.setAvatar(R.drawable.my_logo);
//        m.setPostimage(R.drawable.my_logo);
//        m.setPublisher("Haichao Song");
//        models.add(m);
//
//        m = new Model();
//        m.setUsername("Genius");
//        m.setDescription("This is Art5 Description.");
//        m.setAvatar(R.drawable.my_logo);
//        m.setPostimage(R.drawable.my_logo);
//        m.setPublisher("Richard");
//        models.add(m);
//
//        return models;
//    }

    @Override
    public String getFragmentTag() { return TAG; }

    public class HubModelAdapter extends RecyclerView.Adapter<HubModelAdapter.HubModelHolder> {

    }

}

