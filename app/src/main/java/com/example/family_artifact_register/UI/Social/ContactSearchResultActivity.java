package com.example.family_artifact_register.UI.Social;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactSearchResultViewModel;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactSearchResultViewModelFactory;
import com.example.family_artifact_register.R;

import java.util.ArrayList;
import java.util.List;

public class ContactSearchResultActivity extends AppCompatActivity {

    /**
     * class tag
     */
    public static final String TAG = ContactSearchResultActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SearchResultAdapter adapter;
    private DividerItemDecoration divider;

    private ContactSearchResultViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_search);

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");

        setupRecyclerView();

        viewModel = ViewModelProviders.of(this, new ContactSearchResultViewModelFactory(getApplication(), query)).get(ContactSearchResultViewModel.class);

        Observer<List<UserInfo>> resultObserver = new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> newData) {
                // search result back from database
                if(newData.size() > 0) {
                    // got a match
                    adapter.setData(newData);
                }
                else
                    // no match
                    setContentView(R.layout.activity_friend_search_no_result);
            }
        };

        viewModel.getUsers().observe(this, resultObserver);

        getSupportActionBar().setTitle(query);
//        Log.i(TAG, query);
//        List<User> result = viewModel.getUsers(nameList);
//        if(result.size() > 0)
//            setContentView(R.layout.activity_friend_search_no_result);
//        else {
//            setContentView(R.layout.activity_friend_search);
////            ArrayList<User> data = new ArrayList<>();
////            data.add(result);
//            setupRecyclerView(result);
//        }

//        ArrayList<User> result = new ArrayList<>();
//        result.add();
//
//        ArrayList<String> userList = new ArrayList<>();
//        Collections.addAll(userList, result);
//        Log.i(TAG, result.toString());
//        if(result.length > 0) {

//            ArrayList<String> data = new ArrayList<>();
//            Collections.addAll(data, result);
//            List<User> data = viewModel.getUsers(userList).getValue();
//            setupRecyclerView(data);
//        }
//        else {
//            setContentView(R.layout.activity_friend_search_no_result);
//        }
    }

    private void setupRecyclerView() {
        // get the view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        // set layout manager for the view
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // set the adapter for the view
        adapter = new SearchResultAdapter(viewModel);
        recyclerView.setAdapter(adapter);

        // set the divider between list item
        divider = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(divider);
    }

    public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {

        public class SearchResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView textView;
            public ImageView imageView;
            public SearchResultViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                this.textView = itemView.findViewById(R.id.username);
                this.imageView = itemView.findViewById(R.id.avatar);
            }

            @Override
            public void onClick(View view) {
                String selectedUserName = textView.getText().toString();
                Intent i = new Intent(view.getContext(), NewContactDetailActivity.class);
                i.putExtra("selectedUid", viewModel.getUidByName(selectedUserName));
                startActivity(i);
            }
        }

        public SearchResultAdapter(ContactSearchResultViewModel viewModel) {
            this.viewModel = viewModel;
        }

        private ContactSearchResultViewModel viewModel;
        private List<UserInfo> dataSet;

        @NonNull
        @Override
        public SearchResultAdapter.SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
            return new SearchResultViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchResultAdapter.SearchResultViewHolder holder, int position) {
            if(dataSet != null)
                holder.textView.setText(dataSet.get(position).getDisplayName());
            else
                // TODO what to display when data is not ready
                holder.textView.setText("Loading data");
        }

        @Override
        public int getItemCount() {
            if(dataSet != null)
                return dataSet.size();
            return 0;
        }

        public void setData(List<UserInfo> newData) {
            dataSet = newData;
            notifyDataSetChanged();
        }
    }
}
