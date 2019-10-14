package com.example.family_artifact_register.UI.ArtifactTimeline;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.TimelineViewModel;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.TimelineViewModelFactory;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.Util.TimeToString;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.example.family_artifact_register.UI.Util.MediaViewHelper.getImageRecyclerView;

// import com.example.family_artifact_register.UI.ArtifactHub.ArtifactDetailActivity;

public class TimelineActivity extends AppCompatActivity {

    public static final String TAG = TimelineActivity.class.getSimpleName();

    public static final String TIMELINE_ID_KEY = "timelineID";

    private RecyclerView recyclerView;
    private TimelineAdapter adapter;

    private TimelineViewModel viewModel;

    private List<ArtifactItemWrapper> artifacts;

    private String timelineID;

    private LifecycleOwner owner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline2);
        owner = this;
        Intent intent = getIntent();
        timelineID = intent.getStringExtra(TIMELINE_ID_KEY);

        recyclerView = (RecyclerView) findViewById(R.id.timeline_recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TimelineAdapter(this);
        recyclerView.setAdapter(adapter);

//        ((TimelineView) findViewById(R.id.long_line)).initLine(0);

        viewModel = ViewModelProviders.of(this, new TimelineViewModelFactory(getApplication(), timelineID)).get(TimelineViewModel.class);

        viewModel.getTimeline(timelineID).observe(owner, new Observer<ArtifactTimeline>() {
            @Override
            public void onChanged(ArtifactTimeline artifactTimeline) {
                Log.d(TAG, "get timeline from DB");

                getSupportActionBar().setTitle(artifactTimeline.getTitle());

                viewModel.getArtifactItems(artifactTimeline.getArtifactItemPostIds()).observe(owner, new Observer<List<ArtifactItemWrapper>>() {
                    @Override
                    public void onChanged(List<ArtifactItemWrapper> newData) {
                        Log.d(TAG, "get artifacts from DB, using postIDs in timeline");
                        adapter.setData(newData);
                    }
                });
            }
        });

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setBackgroundDrawable(this.getDrawable(R.drawable.gradient_background));
        }

        // TODO what happens when back arrow is clicked (who is the parent)
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // https://stackoverflow.com/a/30059647
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {

        public class TimelineViewHolder extends RecyclerView.ViewHolder {

            public TimelineView timelineView;
//            public TextView time;
//            public TextView title;
//            public TextView description;
//            public MaterialButton detailButotn;
//            // TODO handle multiple instance of same type, and different types
//            public ImageView media;
            public String itemId;
            public TextView time;
            public TextView description;
//            public RecyclerView imageList;
            public FrameLayout frame;

            public TimelineViewHolder(@NonNull View itemView, int viewType, TimelineItemAdapter adapter) {
                super(itemView);
//                this.timelineView = itemView.findViewById(R.id.timeline);
//                this.time= itemView.findViewById(R.id.timeline_item_upload_time);
//                this.title = itemView.findViewById(R.id.timeline_item_title);
//                this.description = itemView.findViewById(R.id.timeline_item_description);
//                this.detailButotn = itemView.findViewById(R.id.artifact_detail);
//                this.media = itemView.findViewById(R.id.timeline_item_media);
//                if(viewType == 2) {
//                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) timelineView.getLayoutParams();
//                    params.height = 1375;
//                    timelineView.setLayoutParams(params);
//                }
//                timelineView.initLine(0);
//
//                detailButotn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // when artifact item is clicked, jump to detail page of the item
//                        Log.d(TAG, "timeline item is clicked");
//                        Intent intent = new Intent(view.getContext(), ArtifactDetailActivity.class);
//                        intent.putExtra("selectedPid", itemId);
//                        startActivity(intent);
//                    }
//                });
                this.time = itemView.findViewById(R.id.item_time);
                this.description = itemView.findViewById(R.id.item_description);
//                this.imageList = itemView.findViewById(R.id.timeline_item_images);
                this.frame = itemView.findViewById(R.id.timeline_frame);
                this.timelineView = itemView.findViewById(R.id.timeline2);
//                LinearLayoutManager manager = new LinearLayoutManager(itemView.getContext());
//                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                imageList.setLayoutManager(manager);
//                imageList.setAdapter(adapter);

                timelineView.initLine(viewType);
            }
            public void clearFrame() {
                frame.removeAllViews();
            }
        }

        private List<ArtifactItemWrapper> dataSet = new ArrayList<>();
        private Comparator<ArtifactItemWrapper> comparator;
        private List<TimelineItemAdapter> itemAdapters = new ArrayList<>();

        private long maxInterval, minInterval;

        private final int maxGap = 1000;
        private final int minGap = 500;
        private Context context;

        public TimelineAdapter(Context context) {
            maxInterval = Long.MIN_VALUE;
            minInterval = Long.MIN_VALUE;
            this.context = context;
            comparator = new Comparator<ArtifactItemWrapper>() {
                @Override
                public int compare(ArtifactItemWrapper artifactItemWrapper, ArtifactItemWrapper t1) {
                    return artifactItemWrapper.getHappenedDateTime().compareTo(t1.getHappenedDateTime());
                }
            };
        }

        @NonNull
        @Override
        public TimelineAdapter.TimelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.timeline_item2, null);
            TimelineItemAdapter adapter = new TimelineItemAdapter();
            itemAdapters.add(adapter);
            return new TimelineViewHolder(view, viewType, adapter);
        }

        @Override
        public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
            // change the gap distance between items dynamically, based on their times
//            float ratio;
//            if(dataSet.size() > 2 && position != dataSet.size() - 1) {
//                long diff = getTimeDiff(dataSet.get(position).getHappenedDateTime(),
//                        dataSet.get(position + 1).getHappenedDateTime());
//                ratio = (maxInterval - diff) / (maxInterval - minInterval);
//
//                int height = (int) (ratio * maxGap);
//                if(height < minGap) {
//                    height = minGap;
//                }
//
//                Log.d(TAG, "ratio is " + ratio);
//                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.timelineView.getLayoutParams();
//                params.height = height;
//                // params.height = 500;
//                Log.d(TAG, "gap set: "+ height);
//                holder.timelineView.setLayoutParams(params);
//            }
//
//            holder.time.setText(dataSet.get(position).getHappenedDateTime());
//            holder.description.setText(dataSet.get(position).getDescription());
//            holder.itemId = dataSet.get(position).getPostId();
//            List<String> urls = dataSet.get(position).getLocalMediaDataUrls();
//            Log.d(TAG, "urls from DB: " + urls.toString());
//            if(urls.size() > 0) {
//                // TODO implementation for displaying multiple images
//                holder.media.setImageURI(Uri.parse(urls.get(0)));
//            }


            ArtifactItemWrapper wrapper = dataSet.get(position);
            holder.time.setText(wrapper.getHappenedDateTime());
            holder.description.setText(wrapper.getDescription());
            holder.itemId = wrapper.getPostId();
//            itemAdapters.get(position).setData(wrapper.getLocalMediaDataUrls());
//            LinearLayoutManager manager = new LinearLayoutManager(context);
//            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            holder.imageList.setLayoutManager(manager);
//            holder.imageList.setAdapter(adapter);
            holder.clearFrame();
            // set frame layout param
            RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
//            layoutParam.gravity = Gravity.CENTER;
//            layoutParam.topMargin = 20;
//            layoutParam.bottomMargin = 20;
            List<Uri> uris = new ArrayList<>();
            for (String localUri: dataSet.get(position).getLocalMediaDataUrls()) {
                uris.add(Uri.parse(localUri));
            }

            holder.frame.addView(getImageRecyclerView(600,600,uris, context));
//            holder.frame.setLayoutParams(layoutParam);
        }

        @Override
        public int getItemCount() {
            if(dataSet != null) {
                return dataSet.size();
            }
            return 0;
        }

        @Override
        public int getItemViewType(int position) {
            return TimelineView.getTimeLineViewType(position, getItemCount());
        }

        public void setData(List<ArtifactItemWrapper> newData) {
            dataSet = newData;
            dataSet.sort(comparator);
            maxInterval = Long.MIN_VALUE;
            minInterval = Long.MIN_VALUE;
            long diff;
            for (int i = 0; i < dataSet.size() - 1; i++) {
                diff = getTimeDiff(dataSet.get(i).getHappenedDateTime(),
                        dataSet.get(i + 1).getHappenedDateTime());
                if(diff > maxInterval) {
                    maxInterval = diff;
                }
                if(diff < maxInterval) {
                    maxInterval = diff;
                }
            }
            notifyDataSetChanged();
        }

        private long getTimeDiff(String s1, String s2) {
            try {
                long time1 = TimeToString.standardDateFormat.parse(s1).toInstant().toEpochMilli();
                long time2 = TimeToString.standardDateFormat.parse(s2).toInstant().toEpochMilli();
                return time2 - time1;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public class TimelineItemAdapter extends RecyclerView.Adapter<TimelineItemAdapter.TimelineItemImageViewHolder> {

        public class TimelineItemImageViewHolder extends RecyclerView.ViewHolder {

            public ImageView image;

            public TimelineItemImageViewHolder(@NonNull View itemView) {
                super(itemView);
                this.image = itemView.findViewById(R.id.timeline_item_image);
            }
        }

        private List<String> dataSet;

        @NonNull
        @Override
        public TimelineItemAdapter.TimelineItemImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.timeline_item_image, null);
            return new TimelineItemImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TimelineItemAdapter.TimelineItemImageViewHolder holder, int position) {
            String url = dataSet.get(position);
            if(url != null) {
                holder.image.setImageURI(Uri.parse(url));
            }
        }

        @Override
        public int getItemCount() {
            if(dataSet != null) {
                dataSet.size();
            }
            return 0;
        }

        public void setData(List<String> newData) {
            dataSet = newData;
            notifyDataSetChanged();
        }
    }
}
