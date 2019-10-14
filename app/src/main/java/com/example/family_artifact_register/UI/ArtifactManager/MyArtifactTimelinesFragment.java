package com.example.family_artifact_register.UI.ArtifactManager;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactTimelineWrapper;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.MyTimelineViewModel;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.MyTimelineViewModelFactory;
import com.example.family_artifact_register.R;

import java.util.List;

public class MyArtifactTimelinesFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = MyArtifactTimelinesFragment.class.getSimpleName();

    private MyTimelineViewModel viewModel;

    private RecyclerView recyclerView;
    private MyTimelineAdapter adapter;

    public MyArtifactTimelinesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "My Artifact Items Fragment created");
        return inflater.inflate(R.layout.fragment_my_artifact_timelines, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.my_timeline_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new MyTimelineAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this, new MyTimelineViewModelFactory(getActivity().getApplication())).get(MyTimelineViewModel.class);

        viewModel.getTimelines().observe(this, new Observer<List<ArtifactTimelineWrapper>>() {
            @Override
            public void onChanged(List<ArtifactTimelineWrapper> newData) {
                // TODO logic when data comes back from DB
                adapter.setData(newData);
            }
        });
    }

    /**
     * @return created MyArtifactTimelinesFragment
     */
    public static MyArtifactTimelinesFragment newInstance() { return new MyArtifactTimelinesFragment(); }

    public class MyTimelineAdapter extends RecyclerView.Adapter<MyTimelineAdapter.MyTimelineViewHolder> {

        public class MyTimelineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView title;
            public TextView uploadtime;
            public ImageView image1;
            public ImageView image2;
            public ImageView image3;
            public String itemID;

            public MyTimelineViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.my_timeline_recyclerview_item_title);
                uploadtime = itemView.findViewById(R.id.my_timeline_recyclerview_item_uploadTime);
                image1 = itemView.findViewById(R.id.my_timeline_recyclerview_item_image1);
                image2 = itemView.findViewById(R.id.my_timeline_recyclerview_item_image2);
                image3 = itemView.findViewById(R.id.my_timeline_recyclerview_item_image3);
            }

            @Override
            public void onClick(View view) {
                Log.d(TAG, "timeline item is clicked, jump to timeline detail page");
                // TODO jump to timeline detial page
            }
        }

        private List<ArtifactTimelineWrapper> dataSet;

        @NonNull
        @Override
        public MyTimelineAdapter.MyTimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_timeline_recyclerview_item, parent, false);
            return new MyTimelineViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyTimelineViewHolder holder, int position) {
            // number of images that has been set
            int imagesCount = 0;
            holder.itemID = dataSet.get(position).getPostID();
            holder.title.setText(dataSet.get(position).getTitle());
            holder.uploadtime.setText(dataSet.get(position).getUploadDateTime());
            for(ArtifactItemWrapper item: dataSet.get(position).getArtifacts()) {
                if(imagesCount == 3) {
                    break;
                }
                for(String url: item.getLocalMediaDataUrls()) {
                    if(imagesCount == 3) {
                        break;
                    }
                    // no support for video
                    if(item.getMediaType() == 2) {
                        break;
                    }
                    if(url != null) {
                        if(imagesCount == 0) {
                            holder.image1.setImageURI(Uri.parse(url));
                            imagesCount++;
                        }
                        else if(imagesCount == 1) {
                            holder.image2.setImageURI(Uri.parse(url));
                            imagesCount++;
                        }
                        else if(imagesCount == 2) {
                            holder.image3.setImageURI(Uri.parse(url));
                            imagesCount++;
                        }
                    }
                }
            }
        }

        @Override
        public int getItemCount() {
            if(dataSet != null) {
                return dataSet.size();
            }
            return 0;
        }

        public void setData(List<ArtifactTimelineWrapper> newData) {
            dataSet = newData;
            notifyDataSetChanged();
        }
    }
}
