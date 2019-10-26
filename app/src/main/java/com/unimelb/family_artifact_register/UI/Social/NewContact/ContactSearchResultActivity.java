package com.unimelb.family_artifact_register.UI.Social.NewContact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter.ContactSearchResultViewModel;
import com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter.ContactSearchResultViewModelFactory;
import com.unimelb.family_artifact_register.PresentationLayer.Util.UserInfoWrapper;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.Social.Contact.ContactDetailActivity;

import java.util.List;

/**
 * UI class for displaying a list of contact search results
 */
public class ContactSearchResultActivity extends AppCompatActivity {

    /**
     * class tag
     */
    public static final String TAG = ContactSearchResultActivity.class.getSimpleName();

    /**
     * constant for mode "displaying email on action bar"
     */
    public static final String DISPLAY_EMAIL = "email";

    /**
     * constant for mode "displaying displayName on action bar"
     */
    public static final String DISPLAY_NAME = "displayname";

    // reference to the recyclerView used for displaying a list of contact search results
    private RecyclerView recyclerView;

    // linear layout manager for recyclerView
    private LinearLayoutManager linearLayoutManager;

    // adapter for recyclerView
    private SearchResultAdapter adapter;

    // divider between elements in recyclerView
    private DividerItemDecoration divider;

    // view model for this activity
    private ContactSearchResultViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_search);

        LinearLayout layout = findViewById(R.id.search_result_layout);

        TextView textView = findViewById(R.id.text_view_no_result);

        // get search query from the previous activity
        Intent intent = getIntent();
        String query = intent.getStringExtra("query");

        // get view model
        viewModel = ViewModelProviders.of(this, new ContactSearchResultViewModelFactory(getApplication(), query)).get(ContactSearchResultViewModel.class);

        setupRecyclerView();

        // retrieve data from DB
        viewModel.getUsers().observe(this, new Observer<List<UserInfoWrapper>>() {
            @Override
            public void onChanged(List<UserInfoWrapper> newData) {
                // search result back from database
                Log.d(TAG, "search observer invoked, result: "+ newData.size() +" comes back");
                if(newData.size() > 0) {
                    try {
                        layout.removeView(textView);
                    } catch (Exception e) {
                        Log.d(TAG, e.toString());
                    }
                    for (UserInfoWrapper user : newData)
                        Log.d(TAG, user.toString());
                    // got a match
                    adapter.setData(newData);
                }
            }
        });

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle(query);
        // set gradient color for action bar
        actionBar.setBackgroundDrawable(this.getDrawable(R.drawable.gradient_background));
    }

    // setup recyclerView
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

    /**
     * This is the Adapter class for recyclerView in {@link ContactSearchResultActivity}
     */
    public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {

        /**
         * This is the ViewHolder class for recyclerView in {@link ContactSearchResultActivity}
         */
        public class SearchResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            /**
             * the {@link TextView} in the item
             */
            public TextView textView;

            /**
             * the {@link ImageView} in the item
             */
            public ImageView imageView;

            /**
             * the unique id of the item
             */
            public String itemId;

            /**
             * public constructor for instantiating a new {@link SearchResultViewHolder}
             * @param itemView the inflated view for the item
             */
            public SearchResultViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                this.textView = itemView.findViewById(R.id.username);
                this.imageView = itemView.findViewById(R.id.avatar);
            }

            @Override
            public void onClick(View view) {
                Intent intent;
                if(itemId.equals(viewModel.getCurrentUser().getUid()) || viewModel.getCurrentUser().getFriendUids().containsKey(itemId)) {
                    // searching an existing friend or the current user
                    intent = new Intent(view.getContext(), ContactDetailActivity.class);
                }
                else {
                    // searching a new friend
                    intent = new Intent(view.getContext(), NewContactDetailActivity.class);
                }
                intent.putExtra("selectedUid", itemId);
                startActivity(intent);
            }
        }

        /**
         * public constructor for instantiating a new {@link SearchResultAdapter}
         * @param viewModel the view model of the {@link ContactSearchResultActivity}
         */
        public SearchResultAdapter(ContactSearchResultViewModel viewModel) {
            this.viewModel = viewModel;
        }

        // the view model from the recyclerView's owner
        private ContactSearchResultViewModel viewModel;

        // data to be used
        private List<UserInfoWrapper> dataSet;

        @NonNull
        @Override
        public SearchResultAdapter.SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
            return new SearchResultViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchResultAdapter.SearchResultViewHolder holder, int position) {
            if(dataSet != null) {
                String s;
                if(viewModel.getDisplayMode().equals(DISPLAY_EMAIL)) {
                    Log.d(TAG, "email, mode chosen by view model: " + viewModel.getDisplayMode());
                    s = dataSet.get(position).getEmail();
                    if(s != null && s.length() > 0) {
                        holder.textView.setText(s);
                    }
                }
                else if(viewModel.getDisplayMode().equals(DISPLAY_NAME)) {
                    Log.d(TAG, "displayname, mode chosen by view model: " + viewModel.getDisplayMode());
                    s = dataSet.get(position).getDisplayName();
                    if(s != null && s.length() > 0) {
                        holder.textView.setText(s);
                    }
                }
                holder.itemId = dataSet.get(position).getUid();
                String url = dataSet.get(position).getPhotoUrl();
                if(url != null) {
                    holder.imageView.setImageURI(Uri.parse(url));
                }
            }
            else
                holder.textView.setText("");
        }

        @Override
        public int getItemCount() {
            if(dataSet != null)
                return dataSet.size();
            return 0;
        }

        /**
         * set new data for adapter
         * @param newData the new data to be set
         */
        public void setData(List<UserInfoWrapper> newData) {
            dataSet = newData;
            notifyDataSetChanged();
        }
    }
}
