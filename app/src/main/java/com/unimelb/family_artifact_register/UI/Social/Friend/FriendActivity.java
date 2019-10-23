package com.unimelb.family_artifact_register.UI.Social.Friend;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.Social.Contact.ContactDetailActivity;
import com.unimelb.family_artifact_register.UI.Util.BaseActionBarActivity;

import java.util.ArrayList;
import java.util.Collections;

public class FriendActivity extends BaseActionBarActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private DividerItemDecoration divider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMyActionBar().setTitle("Friend");

        ArrayList<String> dataSet = new ArrayList<>();

        // fake data for testing use only
        String[] friends = new String[] {"Tim", "Matt", "Leon", "coffee", "xulin", "zhuoqun", "haichao", "1", "2", "3", "4"};
        Collections.addAll(dataSet, friends);

        // retrieve user's friend data from DB
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        try {
//            dataSet.add(firebaseAuth.getCurrentUserInfo().getEmail());
//        }
//        catch (Exception e) {
//            System.out.println("@@@@  user is null");
//        }

        setupRecyclerView(dataSet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.friend_toolbar_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_friend).getActionView();

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setQueryHint("Search new friend");

        return super.onCreateOptionsMenu(menu);
    }

    private void setupRecyclerView(ArrayList<String> dataSet) {
        // get the view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        // set layout manager for the view
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // set the adapter for the view
        adapter = new MyAdapter(dataSet);
        recyclerView.setAdapter(adapter);

        // set the divider between list item
        divider = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(divider);
    }

    // probably become a separate class in the future
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private ArrayList<String> dataSet;
        private final int[] avatars = new int[] {R.drawable.my_logo};

        // probably become a separate class in the future
        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView textView;
            public ImageView imageView;
            public MyViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                this.textView = itemView.findViewById(R.id.username);
                this.imageView = itemView.findViewById(R.id.avatar);
            }

            @Override
            public void onClick(View view) {
                System.out.println("#############");
//                System.out.println(textView.getText());
                String value = (String) textView.getText();
                AppCompatActivity currentActivity = (AppCompatActivity) view.getContext();
                Intent i = new Intent(currentActivity, ContactDetailActivity.class);
                i.putExtra("key", value);
                startActivity(i);
            }
        }

        public MyAdapter(ArrayList<String> dataSet) {
            this.dataSet = dataSet;
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
            return new MyAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
            holder.textView.setText(dataSet.get(position));
        }

        @Override
        public int getItemCount () {
            return dataSet.size();
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_friend;
    }
}
