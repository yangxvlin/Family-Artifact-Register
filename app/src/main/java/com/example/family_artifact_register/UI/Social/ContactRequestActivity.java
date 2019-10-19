package com.example.family_artifact_register.UI.Social;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactRequestViewModel;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactRequestViewModelFactory;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.Request;
import com.example.family_artifact_register.R;
import com.google.android.material.button.MaterialButton;

import java.util.Iterator;
import java.util.Set;

public class ContactRequestActivity extends AppCompatActivity {

    public static final String TAG = ContactRequestActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private DividerItemDecoration divider;

    private RequestAdapter adapter;

    private ContactRequestViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request_list);

        // force the system not to display action bar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.ContactRequestActivity_title);
        actionBar.setBackgroundDrawable(this.getDrawable(R.drawable.gradient_background));

        setupRecyclerView();

        viewModel = ViewModelProviders.of(this, new ContactRequestViewModelFactory(getApplication())).get(ContactRequestViewModel.class);

        viewModel.getRequests().observe(this, new Observer<Set<Request>>() {
            @Override
            public void onChanged(Set<Request> requests) {
                Log.d(TAG, "get data from DB");
                adapter.setData(requests);
            }
        });
    }

    private void setupRecyclerView() {
        // get the view
        recyclerView = (RecyclerView) findViewById(R.id.friend_request_recycler_view);
        recyclerView.setHasFixedSize(true);

        // set layout manager for the view
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // set the adapter for the view
        adapter = new RequestAdapter();
        recyclerView.setAdapter(adapter);

        // set the divider between list item
        divider = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(divider);
    }


    public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

        public class RequestViewHolder extends RecyclerView.ViewHolder {

            public ImageView avatar;
            public TextView username;
            public MaterialButton accept;
            public String itemID;

            public RequestViewHolder(@NonNull View itemView) {
                super(itemView);
                avatar = itemView.findViewById(R.id.request_avatar);
                username = itemView.findViewById(R.id.request_username);
                accept = itemView.findViewById(R.id.request_accept_button);
            }
        }

//        private ContactRequestViewModel viewModel;
        private Set<Request> requests;
        private Iterator<Request> dataSetIterator;

        @NonNull
        @Override
        public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_request_list_item, parent, false);
            return new RequestViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
            if(requests != null) {
                Request request;
                if(dataSetIterator.hasNext()) {
                    request = dataSetIterator.next();
                    holder.itemID = request.getUser().getUid();
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent;
                            if(holder.itemID.equals(viewModel.getCurrentUid()) ||
                                    viewModel.getCurrentUser().getFriendUids().containsKey(holder.itemID)) {
                                intent = new Intent(view.getContext(), ContactDetailActivity.class);
                            }
                            else {
                                intent = new Intent(view.getContext(), NewContactDetailActivity.class);
                            }
                            intent.putExtra("selectedUid", holder.itemID);
                            startActivity(intent);
                        }
                    });
                    String s = request.getUser().getDisplayName();
                    if(s != null && s.length() > 0) {
                        holder.username.setText(s);
                    }
                    s = request.getUser().getPhotoUrl();
                    if(s != null) {
                        Log.d(TAG, "url set to image view: " + s);
                        holder.avatar.setImageURI(Uri.parse(s));
                    }
                    holder.accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d(TAG, "accept button has been clicked");
                            viewModel.accept(holder.itemID);
                            holder.accept.setEnabled(false);
                            holder.accept.setBackgroundColor(getResources().getColor(R.color.wechat_grey));
                        }
                    });
                }
            }
        }

        @Override
        public int getItemCount() {
            if(requests != null) {
                return requests.size();
            }
            return 0;
        }

        public void setData(Set<Request> newData) {
            requests = newData;
            dataSetIterator = requests.iterator();
            notifyDataSetChanged();
        }
    }
}
