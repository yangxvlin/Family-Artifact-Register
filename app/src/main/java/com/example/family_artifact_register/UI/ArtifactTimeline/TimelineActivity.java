package com.example.family_artifact_register.UI.ArtifactTimeline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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

import static com.example.family_artifact_register.UI.Util.TimelineRecyclerViewHelper.getRecyclerView;

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

            public TextView time;
            public TextView description;
            public FrameLayout frame;
            // TODO handle multiple instance of same type, and different types
            public String itemId;

            public TimelineViewHolder(@NonNull View itemView, int viewType) {
                super(itemView);
                this.time = itemView.findViewById(R.id.item_time);
                this.description = itemView.findViewById(R.id.item_description);
                this.frame = itemView.findViewById(R.id.timeline_frame);
                this.timelineView = itemView.findViewById(R.id.timeline2);
                timelineView.initLine(viewType);
            }

            public void clearFrame() {
                frame.removeAllViews();
            }
        }

        private List<ArtifactItemWrapper> dataSet = new ArrayList<>();
        private Comparator<ArtifactItemWrapper> comparator;
//        private List<TimelineItemAdapter> itemAdapters = new ArrayList<>();

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
            return new TimelineViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
            ArtifactItemWrapper wrapper = dataSet.get(position);
            holder.time.setText(wrapper.getHappenedDateTime());
            holder.description.setText(wrapper.getDescription());
            holder.itemId = wrapper.getPostId();

            holder.clearFrame();
            holder.frame.addView(getRecyclerView(dataSet.get(position), context));
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
}
