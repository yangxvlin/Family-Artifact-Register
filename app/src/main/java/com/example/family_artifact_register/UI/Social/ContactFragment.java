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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactViewModel;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactViewModelFactory;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.UserInfoWrapper;
import com.example.family_artifact_register.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

//        String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        Log.d(TAG ,"uid of current user: " + currentUserID);

        contactModel = ViewModelProviders.of(this, new ContactViewModelFactory(getActivity().getApplication())).get(ContactViewModel.class);

        setupRecyclerView(view);

        ImageView userProfile = (ImageView) view.findViewById(R.id.user_profile);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ContactDetailActivity.class);
                intent.putExtra("selectedUid", contactModel.getCurrentUid());
                startActivity(intent);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.friend_list_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ContactSearchActivity.class));
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

        Observer<Set<UserInfoWrapper>> contactObserver = new Observer<Set<UserInfoWrapper>>() {
            @Override
            public void onChanged(Set<UserInfoWrapper> newData) {
                // when there is a change in the friend list, give the new one to list adapter
                // Update the cached copy of the users in the adapter
                adapter.setData(newData);
            }
        };

        contactModel.getContacts().observe(this, contactObserver);

        return view;
    }

    public static ContactFragment newInstance() { return new ContactFragment(); }

    private void setupRecyclerView(View view) {
        // get the view
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        // set layout manager for the view
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // set the adapter for the view
        adapter = new FriendListAdapter(contactModel);
        recyclerView.setAdapter(adapter);

        // set the divider between list item
        divider = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(divider);
    }

    @Override
    public String getFragmentTag() { return TAG; }

    public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder> {

        public class FriendListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView textView;
            public ImageView imageView;
            public String itemId;
            public FriendListViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                this.textView = itemView.findViewById(R.id.username);
                this.imageView = itemView.findViewById(R.id.avatar);
            }

            @Override
            public void onClick(View view) {
                String selectedUserName= textView.getText().toString();
                Intent i = new Intent(view.getContext(), ContactDetailActivity.class);
//                i.putExtra("selectedUid", viewModel.getUidByName(selectedUserName));
                i.putExtra("selectedUid", itemId);
                startActivity(i);
            }
        }

        public FriendListAdapter(ContactViewModel viewModel) {
            this.viewModel = viewModel;
        }

        private ContactViewModel viewModel;
        private Set<UserInfoWrapper> dataSet;
        private Iterator<UserInfoWrapper> dataSetIterator;

        @NonNull
        @Override
        public FriendListAdapter.FriendListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
            return new FriendListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FriendListAdapter.FriendListViewHolder holder, int position) {
            // data is ready to be displayed
            if(dataSet != null) {
                UserInfoWrapper currentItem = null;
                if(dataSetIterator.hasNext()) {
                    currentItem = dataSetIterator.next();
                    holder.textView.setText(currentItem.getDisplayName());
                    holder.itemId = currentItem.getUid();
                    String url = currentItem.getPhotoUrl();
                    if(url != null) {
                        holder.imageView.setImageURI(Uri.parse(url));
                    }
                } else {
                    Log.e(TAG ,"error iterating data", new Throwable());
                }
            }
            // data is not ready yet
            else {
                // TODO what to display when data is not ready
                holder.textView.setText("Loading data");
            }
        }

        @Override
        public int getItemCount() {
            if(dataSet != null)
                return dataSet.size();
            // initially, dataSet is null
            return 0;
        }

        public void setData(Set<UserInfoWrapper> newData) {
            // solution from codelab
            dataSet = newData;
            dataSetIterator = dataSet.iterator();
            notifyDataSetChanged();

//            StringDiffCallBack stringDiffCallback = new StringDiffCallBack(dataSet, newData);
//            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(stringDiffCallback);
//
//            dataSet.clear();
//            dataSet.addAll(newData);
//            diffResult.dispatchUpdatesTo(this);
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
