package com.example.family_artifact_register.UI.Social;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.FakeDB;
import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactViewModel;
import com.example.family_artifact_register.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ContactFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = ContactFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FriendListAdapter adapter;
    private DividerItemDecoration divider;

    private ContactViewModel contactModel;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        ArrayList<String> dataSet = FakeDB.getInstance();

//        // fake data for testing use only
//        String[] friends = new String[] {"Tim", "Matt", "Leon", "coffee", "xulin", "zhuoqun", "haichao", "1", "2", "3", "4"};
//
//        Collections.addAll(dataSet, friends);

        setupRecyclerView(view, dataSet);

        FloatingActionButton fab = view.findViewById(R.id.friend_list_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), FriendSearchActivity.class));
//                // for testing that the observation works
//                ArrayList<String> data = new ArrayList<>();
//                data.add("xxx");
//                contactModel.getContact().setValue(data);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        contactModel = ViewModelProviders.of(this).get(ContactViewModel.class);

        Observer<ArrayList<String>> contactObserver = new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> newData) {
                System.out.println("######################################");
                // when there is a change in the friend list, give the new one to list adapter
                adapter.setData(newData);
            }
        };

        contactModel.getContact().observe(this, contactObserver);

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


    public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListViewModel> {

        private ArrayList<String> dataSet;

        public class FriendListViewModel extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView textView;
            public ImageView imageView;

            public FriendListViewModel(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                this.textView = itemView.findViewById(R.id.username);
                this.imageView = itemView.findViewById(R.id.avatar);
            }

            @Override
            public void onClick(View view) {
                String value = textView.getText().toString();
                Intent i = new Intent(view.getContext(), NewFriendDetailActivity.class);
                i.putExtra("key", value);
                startActivity(i);
            }
        }

        public FriendListAdapter(ArrayList<String> dataSet) { this.dataSet = dataSet; }

        @NonNull
        @Override
        public FriendListAdapter.FriendListViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
            return new FriendListViewModel(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FriendListAdapter.FriendListViewModel holder, int position) {
            holder.textView.setText(dataSet.get(position));
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        public void setData(ArrayList<String> newData) {
            StringDiffCallBack stringDiffCallback = new StringDiffCallBack(dataSet, newData);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(stringDiffCallback);

            dataSet.clear();
            dataSet.addAll(newData);
            diffResult.dispatchUpdatesTo(this);
        }

        // https://github.com/guenodz/livedata-recyclerview-sample/tree/master/app/src/main/java/me/guendouz/livedata_recyclerview
        class StringDiffCallBack extends DiffUtil.Callback {

            private ArrayList<String> newList;
            private ArrayList<String> oldList;

            public StringDiffCallBack(ArrayList<String> oldList, ArrayList<String> newList) {
                this.oldList= oldList;
                this.newList= newList;
            }

            @Override
            public int getOldListSize() {
                return oldList.size();
            }

            @Override
            public int getNewListSize() {
                return newList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return true;
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
            }
        }
    }
}
