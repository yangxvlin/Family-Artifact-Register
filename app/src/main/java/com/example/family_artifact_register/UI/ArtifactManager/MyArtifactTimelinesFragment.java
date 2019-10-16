package com.example.family_artifact_register.UI.ArtifactManager;

import android.content.Context;
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
import com.example.family_artifact_register.UI.ArtifactTimeline.TimelineActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.Comparator;
import java.util.List;

import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.cropCenter;
import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getVideoThumbNail;

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
        adapter = new MyTimelineAdapter(view.getContext());
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this, new MyTimelineViewModelFactory(getActivity().getApplication())).get(MyTimelineViewModel.class);

        viewModel.getTimelines().observe(this, new Observer<List<ArtifactTimelineWrapper>>() {
            @Override
            public void onChanged(List<ArtifactTimelineWrapper> newData) {
                adapter.setData(newData);
            }
        });
    }

    /**
     * @return created MyArtifactTimelinesFragment
     */
    public static MyArtifactTimelinesFragment newInstance() { return new MyArtifactTimelinesFragment(); }

    public class MyTimelineAdapter extends RecyclerView.Adapter<MyTimelineAdapter.MyTimelineViewHolder> {

        public class MyTimelineViewHolder extends RecyclerView.ViewHolder {

            public MaterialCardView cardView;
            public TextView title;
            public TextView uploadtime;
            public TextView duration;
            public ImageView image1;
            public ImageView image2;
            public ImageView image3;
            public String itemID;

            public MyTimelineViewHolder(@NonNull View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.cardview);
                title = itemView.findViewById(R.id.my_timeline_recyclerview_item_title);
                title.setSelected(true);
                uploadtime = itemView.findViewById(R.id.my_timeline_recyclerview_item_uploadTime);
                duration = itemView.findViewById(R.id.my_timeline_recyclerview_item_duration);
                duration.setSelected(true);
                image1 = itemView.findViewById(R.id.my_timeline_recyclerview_item_image1);
                image2 = itemView.findViewById(R.id.my_timeline_recyclerview_item_image2);
                image3 = itemView.findViewById(R.id.my_timeline_recyclerview_item_image3);
            }
        }

        private List<ArtifactTimelineWrapper> dataSet;
        private Comparator<ArtifactTimelineWrapper> comparator;
        private Context context;

        public MyTimelineAdapter(Context context) {
            this.context = context;
            comparator = new Comparator<ArtifactTimelineWrapper>() {
                @Override
                public int compare(ArtifactTimelineWrapper artifactTimelineWrapper, ArtifactTimelineWrapper t1) {
                    return artifactTimelineWrapper.getUploadDateTime().compareTo(t1.getUploadDateTime());
                }
            };
        }

        @NonNull
        @Override
        public MyTimelineAdapter.MyTimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_timeline_recyclerview_item, parent, false);
            return new MyTimelineViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyTimelineViewHolder holder, int position) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "timeline item is clicked, jump to timeline detail page");
                    Intent intent = new Intent(context, TimelineActivity.class);
                    intent.putExtra(TimelineActivity.TIMELINE_ID_KEY, holder.itemID);
                    startActivity(intent);
                }
            });
            // number of images that has been set
            int imagesCount = 0;
            holder.itemID = dataSet.get(position).getPostID();
            holder.title.setSelected(true);
            holder.title.setText(dataSet.get(position).getTitle());
            holder.uploadtime.setText(dataSet.get(position).getUploadDateTime());
            List<ArtifactItemWrapper> items = dataSet.get(position).getArtifacts();
            String timelineDuration = null;
            if(items.size() == 1) {
                timelineDuration = dataSet.get(position).getUploadDateTime().substring(0, 7);
            } else if(items.size() > 1) {
                String oldestTime = items.get(0).getHappenedDateTime().substring(0, 7);
                String newestTime = items.get(items.size() - 1).getHappenedDateTime().substring(0, 7);
                timelineDuration = oldestTime + " -- " + newestTime;
            }
            holder.duration.setSelected(true);
            holder.duration.setText(timelineDuration);
            for(ArtifactItemWrapper item: dataSet.get(position).getArtifacts()) {
                // all views have been set
                if(imagesCount == 3) {
                    break;
                }
                for(String url: item.getLocalMediaDataUrls()) {
                    // all views have been set
                    if(imagesCount == 3) {
                        break;
                    }
                    if(url != null) {
                        if(imagesCount == 0) {
                            setImage(holder.image1, Uri.parse(url), item.getMediaType());
                            imagesCount++;
                        }
                        else if(imagesCount == 1) {
                            setImage(holder.image2, Uri.parse(url), item.getMediaType());
                            imagesCount++;
                        }
                        else if(imagesCount == 2) {
                            setImage(holder.image3, Uri.parse(url), item.getMediaType());
                            imagesCount++;
                        }
                    }
                }
            }
        }

        private void setImage(ImageView image, Uri src, int type) {
            if (type == TYPE_IMAGE) {
                image.setImageURI(src);
            } else if (type == TYPE_VIDEO) {
                image.setImageBitmap(cropCenter(getVideoThumbNail(src.toString())));
            } else {
                Log.e(getFragmentTag(), "unknown media type !!!");
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
            dataSet.sort(comparator);
            notifyDataSetChanged();
        }
    }
}
