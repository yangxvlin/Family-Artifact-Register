package com.example.family_artifact_register.UI.Social;

import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class ContactFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = ContactFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private DividerItemDecoration divider;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        ArrayList<String> dataSet = new ArrayList<>();

        // fake data for testing use only
        String[] friends = new String[] {"Tim", "Matt", "Leon", "coffee", "xulin", "zhuoqun", "haichao", "1", "2", "3", "4"};
        Collections.addAll(dataSet, friends);

        setupRecyclerView(view, dataSet);

        FloatingActionButton fab = view.findViewById(R.id.friend_list_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), FriendSearchActivity.class));
            }
        });

        return view;
    }

    public static ContactFragment newInstance() { return new ContactFragment(); }

    private void setupRecyclerView(View view, ArrayList<String> dataSet) {
        // get the view
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        // set layout manager for the view
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // set the adapter for the view
        adapter = new FriendListAdapter(dataSet);
        recyclerView.setAdapter(adapter);

        // set the divider between list item
        divider = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(divider);
    }

    @Override
    public String getFragmentTag() { return TAG; }
}

