package com.example.family_artifact_register.UI.ArtifactTimeline;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.TimelineViewModel;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.TimelineViewModelFactory;
import com.example.family_artifact_register.R;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    public static final String TAG = TimelineActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private TimelineAdapter adapter;

    private TimelineViewModel viewModel;

    private List<ArtifactItem> artifacts;

    private String timelineID;

    private LifecycleOwner owner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        owner = this;
//        Intent intent = getIntent();
//        timelineID = intent.getStringExtra("timelineID");

        recyclerView = (RecyclerView) findViewById(R.id.timeline_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TimelineAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this, new TimelineViewModelFactory(getApplication())).get(TimelineViewModel.class);

        viewModel.getTimeline(timelineID).observe(owner, new Observer<ArtifactTimeline>() {
            @Override
            public void onChanged(ArtifactTimeline artifactTimeline) {
                Log.d(TAG, "get timeline from DB");

                getSupportActionBar().setTitle(artifactTimeline.getTitle());

                viewModel.getArtifactItems(artifactTimeline.getArtifactItemPostIds()).observe(owner, new Observer<List<ArtifactItem>>() {
                    @Override
                    public void onChanged(List<ArtifactItem> newData) {
                        Log.d(TAG, "get artifacts from DB, using postIDs in timeline");
                        adapter.setData(newData);
                    }
                });
            }
        });

        // TODO what happens when back arrow is clicked (who is the parent)
    }

    public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {

        public class TimelineViewHolder extends RecyclerView.ViewHolder {

            public TimelineView timelineView;
            public TextView time;
            public TextView title;
            public TextView description;
            // to be changed to fragment to handle different types of medias
            public ImageView media;
            public String itemId;

            public TimelineViewHolder(@NonNull View itemView, int viewType) {
                super(itemView);
                this.timelineView = itemView.findViewById(R.id.timeline);
                this.time= itemView.findViewById(R.id.timeline_item_upload_time);
                this.title = itemView.findViewById(R.id.timeline_item_title);
                this.description = itemView.findViewById(R.id.timeline_item_description);
                this.media = itemView.findViewById(R.id.timeline_item_media);
                timelineView.initLine(viewType);
            }
        }

        private List<ArtifactItem> dataSet;

        public TimelineAdapter() {}

        @NonNull
        @Override
        public TimelineAdapter.TimelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.timeline_item, null);
            return new TimelineViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
            holder.time.setText(dataSet.get(position).getUploadDateTime());
            holder.description.setText(dataSet.get(position).getDescription());
            holder.itemId = dataSet.get(position).getPostId();
//            holder.media.setImageResource(dataSet);
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        @Override
        public int getItemViewType(int position) {
            return TimelineView.getTimeLineViewType(position, getItemCount());
        }

        public void setData(List<ArtifactItem> newData) {
            dataSet = newData;
            notifyDataSetChanged();
        }
    }
}
