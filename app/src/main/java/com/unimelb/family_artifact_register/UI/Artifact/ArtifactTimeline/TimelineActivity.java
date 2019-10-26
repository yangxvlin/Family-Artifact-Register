package com.unimelb.family_artifact_register.UI.Artifact.ArtifactTimeline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;
import com.unimelb.family_artifact_register.PresentationLayer.Util.ArtifactItemWrapper;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactPresenter.ArtifactTimelinePresenter.TimelineViewModel;
import com.unimelb.family_artifact_register.PresentationLayer.ArtifactPresenter.ArtifactTimelinePresenter.TimelineViewModelFactory;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.Artifact.ArtifactDetail.ArtifactDetailActivity;
import com.unimelb.family_artifact_register.Util.TimeToString;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.unimelb.family_artifact_register.UI.Artifact.ArtifactTimeline.Util.TimelineRecyclerViewHelper.getRecyclerView;

/**
 * UI class for displaying a list of artifacts in a timeline
 */
public class TimelineActivity extends AppCompatActivity {

    /**
     * class tag
     */
    public static final String TAG = TimelineActivity.class.getSimpleName();

    /**
     * key value used for {@link Intent#putExtra(String, String)}
     */
    public static final String TIMELINE_ID_KEY = "timelineID";

    // reference to the recyclerView used for displaying a list of artifacts in a timeline
    private RecyclerView recyclerView;

    // adapter for recyclerView
    private TimelineAdapter adapter;

    // view model for this activity
    private TimelineViewModel viewModel;

    // the unique id of the timeline to be displayed
    private String timelineID;

    // the lifeCycle owner of the recyclerView
    private LifecycleOwner owner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline2);
        owner = this;

        // get the id of the timeline to be displayed
        Intent intent = getIntent();
        timelineID = intent.getStringExtra(TIMELINE_ID_KEY);

        recyclerView = (RecyclerView) findViewById(R.id.timeline_recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TimelineAdapter(this);
        recyclerView.setAdapter(adapter);

        // get view model
        viewModel = ViewModelProviders.of(this, new TimelineViewModelFactory(getApplication(), timelineID)).get(TimelineViewModel.class);

        // retrieve data from DB
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
            // set gradient color for action bar
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

    /**
     * This is the Adapter class for the recyclerView in {@link TimelineActivity}
     */
    public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {

        /**
         * This is the ViewHolder class for the recyclerView in {@link TimelineActivity}
         */
        public class TimelineViewHolder extends RecyclerView.ViewHolder {

            /**
             * the {@link TimelineView} in the item
             */
            public TimelineView timelineView;

            /**
             * the {@link LinearLayout} in the item
             */
            public LinearLayout timelineHeader;

            /**
             * the {@link TextView} in the item
             */
            public TextView time;

            /**
             * the {@link TextView} in the item
             */
            public TextView description;

            /**
             * the {@link FrameLayout} in the item
             */
            public FrameLayout frame;

            /**
             * the unique id of the item
             */
            public String itemId;

            /**
             * the public constructor for instantiating a new {@link TimelineViewHolder}
             * @param itemView the the inflated view for the item
             * @param viewType the type of the inflated view
             */
            public TimelineViewHolder(@NonNull View itemView, int viewType) {
                super(itemView);
                this.timelineHeader = itemView.findViewById(R.id.header);
                this.time = itemView.findViewById(R.id.item_time);
                this.description = itemView.findViewById(R.id.item_description);
                this.frame = itemView.findViewById(R.id.timeline_frame);
                this.timelineView = itemView.findViewById(R.id.timeline2);
                // END = 2
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) timelineView.getLayoutParams();
                if(viewType == 2) {
                    params.height = 1290;
                }
                // ONLYONE = 3
                else if(viewType == 3) {
                    params.height = 2095;
                }
                timelineView.setLayoutParams(params);
                timelineView.initLine(0);
            }

            /**
             * clear the view in the frame layout
             */
            public void clearFrame() {
                frame.removeAllViews();
            }
        }

        // the data to be used
        private List<ArtifactItemWrapper> dataSet = new ArrayList<>();

        // the comparator used for sorting elements in the data list
        private Comparator<ArtifactItemWrapper> comparator;
//        private List<TimelineItemAdapter> itemAdapters = new ArrayList<>();

        // two attributes used for setting dynamic gap between adjacent items
        private long maxInterval, minInterval;

        // max length of gap
        private final int maxGap = 1500;

        // min length of gap
        private final int minGap = 800;

        // the context
        private Context context;

        /**
         * public constructor for instantiating a new {@link TimelineAdapter}
         * @param context the context
         */
        public TimelineAdapter(Context context) {
            this.context = context;
            // order elements in ascending order of happened time
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
            holder.timelineHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ArtifactDetailActivity.class);
                    intent.putExtra("artifactItemPostId", holder.itemId);
                    startActivity(intent);
                }
            });
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                    holder.timelineView.getLayoutParams();
            if(dataSet.size() > 2 && position != dataSet.size() - 1) {
                long diff = getTimeDiff(dataSet.get(position).getHappenedDateTime(),
                        dataSet.get(position + 1).getHappenedDateTime());
//                float ratio = (maxInterval - diff) / (maxInterval - minInterval);
                double ratio = (double) (diff - minInterval) / (double) (maxInterval - minInterval);
                Log.d(TAG, "pos: " + position + ", maxgap: " + maxInterval + ", mingap: " + minInterval);
                Log.d(TAG, "pos: " + position + ", diff: " + diff + ", ratio:" + ratio);
                int setHeight = minGap + (int) (ratio * (maxGap - minGap));
                Log.d(TAG, "height value set for layout param: " + setHeight);
                params.height = setHeight;
                holder.timelineView.setLayoutParams(params);
            }
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

        /**
         * set new data for adapter
         * @param newData the new data to be set
         */
        public void setData(List<ArtifactItemWrapper> newData) {
            dataSet = newData;
            dataSet.sort(comparator);
            maxInterval = Long.MIN_VALUE;
            minInterval = Long.MAX_VALUE;
            long diff;
            for (int i = 0; i < dataSet.size() - 1; i++) {
                diff = getTimeDiff(dataSet.get(i).getHappenedDateTime(),
                        dataSet.get(i + 1).getHappenedDateTime());
                if(maxInterval < diff) {
                    maxInterval = diff;
                }
                if(minInterval > diff) {
                    minInterval = diff;
                }
            }
            notifyDataSetChanged();
        }

        // get the difference of two dateTimes represented as strings
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
