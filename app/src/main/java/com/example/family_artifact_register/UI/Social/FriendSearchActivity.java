package com.example.family_artifact_register.UI.Social;

import android.app.SearchManager;
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
import com.example.family_artifact_register.UI.Util.BaseActionBarActivity;

import java.util.ArrayList;

public class FriendSearchActivity extends BaseActionBarActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    private DividerItemDecoration divider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
//            System.out.println(query);

            // display search query in the app bar
            getMyActionBar().setTitle(query);

            // fake search result
            String[] candidate = new String[] {"5", "6", "7", "8", "9"};;
            ArrayList<String>  result = search(query, candidate);

            // get a match user
            if(result.size() > 0) {
                setContentView(R.layout.activity_friend_search);

                setupRecyclerView(result);
            }
            // no match user
            else {
                setContentView(R.layout.activity_friend_search_no_result);
            }
        }

        // TODO: implementation for search logic
    }

    // to be moved to a class in the logic layer
    private ArrayList<String> search(String target, String[] users) {
        ArrayList<String> out = new ArrayList<>();
        for(String i : users) {
            if(i.equals(target))
                out.add(i);
        }
        return out;
    }

    private void setupRecyclerView(ArrayList<String> dataSet) {
        // get the view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        // set layout manager for the view
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // set the adapter for the view
        adapter = new MyAdapter(dataSet);
        recyclerView.setAdapter(adapter);

        // set the divider between list item
        divider = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(divider);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private ArrayList<String> dataset;

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView textView;
            public ImageView imageView;
            public MyViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                this.textView = (TextView) itemView.findViewById(R.id.username);
                this.imageView = (ImageView) itemView.findViewById(R.id.avatar);
            }

            @Override
            public void onClick(View view) {
                String value = (String) textView.getText();
                AppCompatActivity currentActivity = (AppCompatActivity) view.getContext();
                Intent i = new Intent(currentActivity, FriendDetailActivity.class);
                i.putExtra("key", value);
                i.putExtra("new friend", "");
                startActivity(i);
            }
        }

        public MyAdapter(ArrayList<String> dataset) {
            this.dataset = dataset;
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
            return new MyAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
            holder.textView.setText(dataset.get(position));
        }

        @Override
        public int getItemCount() {
            return dataset.size();
        }
    }

    @Override
    protected int getLayoutResource() { return R.layout.activity_friend_search; }
}
