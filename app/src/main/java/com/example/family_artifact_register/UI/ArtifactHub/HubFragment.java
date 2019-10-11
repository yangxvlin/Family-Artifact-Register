package com.example.family_artifact_register.UI.ArtifactHub;

import android.content.Intent;
import android.database.Observable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.Artifact;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.PresentationLayer.HubPresenter.HubFragmentPresenter;
import com.example.family_artifact_register.PresentationLayer.HubPresenter.HubViewModel;
import com.example.family_artifact_register.PresentationLayer.HubPresenter.HubViewModelFactory;
import com.example.family_artifact_register.UI.ArtifactManager.NewArtifactActivity2;
import com.example.family_artifact_register.UI.Upload.PostActivity;
import com.example.family_artifact_register.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//public class HubFragment extends Fragment implements IFragment {
//
//    /**
//     * class tag
//     */
//    public static final String TAG = HubFragment.class.getSimpleName();
//
//    private RecyclerView mRecyclerView;
//    private LinearLayoutManager layoutManager;
//    private HubModelAdapter adapter;
//    private DividerItemDecoration divider;
//
//    private HubViewModel hubViewModel;
//
//    public HubFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_hub, container, false);
//
//        hubViewModel = ViewModelProviders.of(this, new HubViewModelFactory(getActivity().getApplication())).get(HubViewModel.class);
//
//        setupRecyclerView(view);
//
//        FloatingActionButton fab = view.findViewById(R.id.hub_fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(view.getContext(), PostActivity.class));
//            }
//        });
//
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                if(dy > 0) {
//                    fab.hide();
//                }
//                else if(dy < 0) {
//                    fab.show();
//                }
//            }
//        });
//
//        Observer<List<ArtifactItem>> postObserver = new Observer<List<ArtifactItem>>() {
//            @Override
//            public void onChanged(List<ArtifactItem> artifactItems) {
//                adapter.setData(artifactItems);
//            }
//        };
//
//        hubViewModel.getPosts().observe(this, postObserver);
//
//        return view;
//    }
//
//    public static HubFragment newInstance() { return new HubFragment(); }
//
//    private void setupRecyclerView(View view) {
//        // get the view
//        mRecyclerView = view.findViewById(R.id.recycler_view);
//        mRecyclerView.setHasFixedSize(true);
//
//        // set layout manager for the view
//        layoutManager = new LinearLayoutManager(view.getContext());
//        mRecyclerView.setLayoutManager(layoutManager);
//
//        // set the adapter for the view
//        adapter = new HubModelAdapter(hubViewModel);
//        mRecyclerView.setAdapter(adapter);
//
//        // set the divider between list item
//        divider = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
//        mRecyclerView.addItemDecoration(divider);
//    }
//
//    @Override
//    public String getFragmentTag() { return TAG; }
//
//    public class HubModelAdapter extends RecyclerView.Adapter<HubModelAdapter.HubModelViewHolder> {
//
//        public class HubModelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//            public TextView username, publisher, description;
//            public ImageView avatar;
//
//            public FrameLayout postImage;
//
//            public String itemId;
//
//            public HubModelViewHolder(View itemView) {
//                super(itemView);
//                itemView.setOnClickListener(this);
//                this.username = itemView.findViewById(R.id.username);
//                this.publisher = itemView.findViewById(R.id.publisher);
//                this.description = itemView.findViewById(R.id.description);
//                this.avatar = itemView.findViewById(R.id.avatar);
//                this.postImage = itemView.findViewById(R.id.post_image);
//            }
//
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(view.getContext(), ArtifactDetailActivity.class);
//                i.putExtra("selectedPid", itemId);
//                startActivity(i);
//            }
//        }
//
//        public HubModelAdapter(HubViewModel viewModel) {
//            this.viewModel = viewModel;
//        }
//
//        private HubViewModel viewModel;
//        private List<ArtifactItem> dataSet;
//        private Iterator<ArtifactItem> dataSetIterator;
//
//        @NonNull
//        @Override
//        public HubModelAdapter.HubModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
//
//            return new HubModelViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull HubModelAdapter.HubModelViewHolder holder, int position) {
//            // data is ready to be displayed
//            if(dataSet != null) {
//                ArtifactItem currentItem = null;
//                if(dataSetIterator.hasNext()) {
//                    currentItem = dataSetIterator.next();
//                    holder.username.setText(dataSet.get(position).getUid());
//                    holder.publisher.setText(dataSet.get(position).getUid());
//                    holder.description.setText(dataSet.get(position).getDescription());
//                    holder.itemId = dataSet.get(position).getPostId();
//                } else {
//                    Log.e(TAG ,"error iterating data", new Throwable());
//                }
//            }
//            // data is not ready yet
//            else {
//                // TODO what to display when data is not ready
//                holder.username.setText("Loading data");
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            if(dataSet != null)
//                return dataSet.size();
//            // initially, dataSet is null
//            return 0;
//        }
//
//        public void setData(List<ArtifactItem> newData) {
//            // solution from codelab
//            dataSet = newData;
//            dataSetIterator = dataSet.iterator();
//            notifyDataSetChanged();
//
////            StringDiffCallBack stringDiffCallback = new StringDiffCallBack(dataSet, newData);
////            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(stringDiffCallback);
////
////            dataSet.clear();
////            dataSet.addAll(newData);
////            diffResult.dispatchUpdatesTo(this);
//        }
//
//        // https://github.com/guenodz/livedata-recyclerview-sample/tree/master/app/src/main/java/me/guendouz/livedata_recyclerview
//        class StringDiffCallBack extends DiffUtil.Callback {
//
//            private ArrayList<String> newList;
//            private ArrayList<String> oldList;
//
//            public StringDiffCallBack(ArrayList<String> oldList, ArrayList<String> newList) {
//                this.oldList= oldList;
//                this.newList= newList;
//            }
//
//            @Override
//            public int getOldListSize() {
//                return oldList.size();
//            }
//
//            @Override
//            public int getNewListSize() {
//                return newList.size();
//            }
//
//            @Override
//            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//                return true;
//            }
//
//            @Override
//            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//                return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
//            }
//        }
//    }
//}

public class HubFragment extends Fragment implements HubFragmentPresenter.IView, IFragment {
    /**
     * class tag
     */
    public static final String TAG = HubFragment.class.getSimpleName();
    private List<ArtifactItemWrapper> artifactItemWrapperList;

    // *********************************** recycler view *****************************************
    /**
     * recycler view adapter
     */
    private HubModelAdapter hubModelAdapter;

    RecyclerView mRecyclerView;

    // *******************************************************************************************

    private HubFragmentPresenter hfp;

    private HubViewModel viewModel;

    public HubFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "hub fragment created");
        return inflater.inflate(R.layout.fragment_hub, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this, new HubViewModelFactory(getActivity().getApplication())).get(HubViewModel.class);

        // create artifacts recycler view
        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(layoutManager);
            hubModelAdapter = new HubModelAdapter(getContext());
            mRecyclerView.setAdapter(hubModelAdapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
            mRecyclerView.addItemDecoration(dividerItemDecoration);
            Log.v(TAG, "recycler view created");
        } else {
            Log.e(TAG, "recycler view not created!!!");
        }

        // create presenter
        hfp = new HubFragmentPresenter(this);

        // add new artifact
        FloatingActionButton add = view.findViewById(R.id.hub_fab);
        add.setOnClickListener(view1 -> {
            Log.d(TAG, "Press hub fab");
            Intent intent = new Intent(getContext(), NewArtifactActivity2.class);
            startActivity(intent);
        });

        // refresh hub
        FloatingActionButton refresh = view.findViewById(R.id.hub_refresh);
        refresh.setOnClickListener(view1 -> {
            viewModel.getPostsChange();
        });

        // recycler view's scroll to hide the floating button
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    add.hide();
                    refresh.hide();
                } else if (dy < 0) {
                    add.show();
                    refresh.show();
                }
            }
        });

        viewModel.getPosts().observe(this, new Observer<List<ArtifactItem>>() {
            @Override
            public void onChanged(List<ArtifactItemWrapper> artifactItemWrappers) {
                Log.d(TAG, "enter onchange");
                hubModelAdapter.setData(artifactItemWrappers);
            }
        });
    }

    /**
     * @return created me fragment
     */
    public static HubFragment newInstance() { return new HubFragment(); }

    // ********************************** implement presenter ************************************
    @Override
    public void addData(ArtifactItem artifactItem) {
        hubModelAdapter.addData(artifactItem);
    }

    @Override
    public String getFragmentTag() { return TAG; }
}