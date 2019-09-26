package com.example.family_artifact_register.UI.Social;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

import java.util.ArrayList;
import java.util.Collections;

public class ContactSearchResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    private DividerItemDecoration divider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String[] result = intent.getStringArrayExtra("key");
        if(result.length > 0) {
            setContentView(R.layout.activity_friend_search);
            ArrayList<String> data = new ArrayList<>();
            Collections.addAll(data, result);
            setupRecyclerView(data);
        }
        else {
            setContentView(R.layout.activity_friend_search_no_result);
        }
        getSupportActionBar().setTitle(intent.getStringExtra("query"));
    }

    private void setupRecyclerView(ArrayList<String> dataSet) {
        // get the view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        // set layout manager for the view
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // set the adapter for the view
        adapter = new SearchResultAdapter(dataSet);
        recyclerView.setAdapter(adapter);

        // set the divider between list item
        divider = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(divider);
    }

    public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {

        private ArrayList<String> dataSet;

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
                String value = textView.getText().toString();
                Intent i = new Intent(view.getContext(), NewContactDetailActivity.class);
                i.putExtra("key", value);
                startActivity(i);
            }
        }

        public SearchResultAdapter(ArrayList<String> dataSet) { this.dataSet = dataSet; }

        @NonNull
        @Override
        public SearchResultAdapter.SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
            return new SearchResultViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchResultAdapter.SearchResultViewHolder holder, int position) {
            holder.textView.setText(dataSet.get(position));
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

    }
}
