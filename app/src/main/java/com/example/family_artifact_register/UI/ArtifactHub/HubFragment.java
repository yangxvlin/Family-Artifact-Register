package com.example.family_artifact_register.UI.ArtifactHub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.R;

import java.util.ArrayList;

public class HubFragment extends Fragment {

    RecyclerView mRecyclerView;
    HubModelAdapter mHubModelAdapter;
    LinearLayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    DividerItemDecoration divider;

public class HubFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = HubFragment.class.getSimpleName();


    public HubFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hub, container, false);
        setupRecyclerView(view, getMyList());
        return view;
    }

    private void setupRecyclerView(View view, ArrayList<Model> myList) {
        // get the view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // set layout manager for the view
        layoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        // set the adapter for the view
        adapter = new HubModelAdapter(view.getContext(), myList);
        mRecyclerView.setAdapter(adapter);

        // set the divider between list item
        divider = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }

    public static HubFragment newInstance() { return new HubFragment(); }


    /**
     * Get a list of models of card view
     * @return a list of models
     */
    private ArrayList<Model> getMyList(){
        ArrayList<Model> models = new ArrayList<Model>();
        Model m = new Model();
        m.setUsername("Username1");
        m.setTitle("This is Art1");
        m.setDescription("This is Art1 Description.");
        m.setAvatar(R.drawable.my_logo);
        m.setImg(R.drawable.my_logo);
        models.add(m);

        m = new Model();
        m.setUsername("Username2");
        m.setTitle("This is Art2");
        m.setDescription("This is Art2 Description.");
        m.setAvatar(R.drawable.my_logo);
        m.setImg(R.drawable.my_logo);
        models.add(m);

        m = new Model();
        m.setUsername("Username3");
        m.setTitle("This is Art3");
        m.setDescription("This is Art3 Description.");
        m.setAvatar(R.drawable.my_logo);
        m.setImg(R.drawable.my_logo);
        models.add(m);

        m = new Model();
        m.setUsername("Username4");
        m.setTitle("This is Art4");
        m.setDescription("This is Art4 Description.");
        m.setAvatar(R.drawable.my_logo);
        m.setImg(R.drawable.my_logo);
        models.add(m);

        return models;
    }

    @Override
    public String getFragmentTag() { return TAG; }

}

