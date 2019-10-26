package com.unimelb.family_artifact_register.UI.Social;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.UI.Social.Contact.ContactDetailActivity;
import com.unimelb.family_artifact_register.UI.Social.NewContact.ContactRequestActivity;
import com.unimelb.family_artifact_register.UI.Social.NewContact.ContactSearchActivity;
import com.unimelb.family_artifact_register.Util.IFragment;
import com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter.ContactViewModel;
import com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter.ContactViewModelFactory;
import com.unimelb.family_artifact_register.PresentationLayer.Util.UserInfoWrapper;
import com.unimelb.family_artifact_register.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * UI class for displaying a list of contacts
 */
public class ContactFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = ContactFragment.class.getSimpleName();

    // reference to the recyclerView used for displaying a list of contacts
    private RecyclerView recyclerView;

    // linear layout manager for recyclerView
    private LinearLayoutManager layoutManager;

    // adapter for recyclerView
    private FriendListAdapter adapter;

    // divider between elements in recyclerView
    private DividerItemDecoration divider;

    // view model for this fragment
    private ContactViewModel contactModel;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        // get view model
        contactModel = ViewModelProviders.of(this, new ContactViewModelFactory(getActivity().getApplication())).get(ContactViewModel.class);

        setupRecyclerView(view);

        ImageView userProfile = (ImageView) view.findViewById(R.id.user_profile);
        // clickl listener for the user's own profile button
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ContactDetailActivity.class);
                intent.putExtra("selectedUid", contactModel.getCurrentUid());
                startActivity(intent);
            }
        });

        TextView requests = (TextView) view.findViewById(R.id.friend_request);
        // click listener for request list button
        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ContactRequestActivity.class));
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.friend_list_fab);
        // click listener for FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ContactSearchActivity.class));
            }
        });

        // scroll listener for FAB so that it disappears when scrolls down and re-appears when up
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

        // retrieve user's own data from DB
        contactModel.getPersonalProfile().observe(this, new Observer<UserInfoWrapper>() {
            @Override
            public void onChanged(UserInfoWrapper wrapper) {
                String url = wrapper.getPhotoUrl();
                if(url != null) {
                    userProfile.setImageURI(Uri.parse(url));
                }
            }
        });

        // retrieve contacts data from DB
        contactModel.getContacts().observe(this, new Observer<Set<UserInfoWrapper>>() {
            @Override
            public void onChanged(Set<UserInfoWrapper> newData) {
                // when there is a change in the friend list, give the new one to list adapter
                // Update the cached copy of the users in the adapter
                adapter.setData(newData);
            }
        });

        return view;
    }

    /**
     * get a new instance of {@link ContactFragment}
     * @return a new instance of {@link ContactFragment}
     */
    public static ContactFragment newInstance() { return new ContactFragment(); }

    // setup recyclerView
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

    /**
     * This is the Adapter class for the recyclerView in {@link ContactFragment}
     */
    public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder> {

        /**
         * This is the ViewHolder class for the recyclerView in {@link ContactFragment}
         */
        public class FriendListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
             * public constructor for instantiating a new {@link FriendListViewHolder}
             * @param itemView the inflated view for the item
             */
            public FriendListViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                this.textView = itemView.findViewById(R.id.username);
                this.imageView = itemView.findViewById(R.id.avatar);
            }

            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ContactDetailActivity.class);
                i.putExtra("selectedUid", itemId);
                startActivity(i);
            }
        }

        /**
         * public constructor for instantiating a new {@link FriendListAdapter}
         * @param viewModel the view model of the {@link ContactFragment}
         */
        public FriendListAdapter(ContactViewModel viewModel) {
            this.viewModel = viewModel;
        }

        // the view model from the recyclerView's owner
        private ContactViewModel viewModel;

        // data to be used
        private Set<UserInfoWrapper> dataSet;

        // an iterator for iterating data list
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
                holder.textView.setText("");
            }
        }

        @Override
        public int getItemCount() {
            if(dataSet != null)
                return dataSet.size();
            // initially, dataSet is null
            return 0;
        }

        /**
         * set new data for adapter
         * @param newData the new data to be set
         */
        public void setData(Set<UserInfoWrapper> newData) {
            // solution from codelab
            dataSet = newData;
            dataSetIterator = dataSet.iterator();
            notifyDataSetChanged();

            // alternative
//            StringDiffCallBack stringDiffCallback = new StringDiffCallBack(dataSet, newData);
//            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(stringDiffCallback);
//
//            dataSet.clear();
//            dataSet.addAll(newData);
//            diffResult.dispatchUpdatesTo(this);
        }

        // https://github.com/guenodz/livedata-recyclerview-sample/tree/master/app/src/main/java/me/guendouz/livedata_recyclerview
        /**
         * class used to update data in recyclerView, when new data is retrieved from live data
         */
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
