package com.example.family_artifact_register.UI.ArtifactTimeline;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.family_artifact_register.UI.ArtifactHub.ArtifactDetailActivity;
import com.example.family_artifact_register.UI.Social.ContactSearchActivity;
import com.github.vipulasri.timelineview.TimelineView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        setContentView(R.layout.activity_timeline);
        owner = this;
        Intent intent = getIntent();
        timelineID = intent.getStringExtra(TIMELINE_ID_KEY);

        recyclerView = (RecyclerView) findViewById(R.id.timeline_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TimelineAdapter();
        recyclerView.setAdapter(adapter);

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
            public TextView time;
            public TextView title;
            public TextView description;
            public MaterialButton detailButotn;
            // TODO handle multiple instance of same type, and different types
            public ImageView media;
            public String itemId;

            public TimelineViewHolder(@NonNull View itemView, int viewType) {
                super(itemView);
                this.timelineView = itemView.findViewById(R.id.timeline);
                this.time= itemView.findViewById(R.id.timeline_item_upload_time);
                this.title = itemView.findViewById(R.id.timeline_item_title);
                this.description = itemView.findViewById(R.id.timeline_item_description);
                this.detailButotn = itemView.findViewById(R.id.artifact_detail);
                this.media = itemView.findViewById(R.id.timeline_item_media);
                timelineView.initLine(viewType);

                detailButotn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // when artifact item is clicked, jump to detail page of the item
                        Log.d(TAG, "timeline item is clicked");
                        Intent intent = new Intent(view.getContext(), ArtifactDetailActivity.class);
                        intent.putExtra("selectedPid", itemId);
                        startActivity(intent);
                    }
                });
            }
        }

        private List<ArtifactItemWrapper> dataSet = new ArrayList<>();
        private Comparator<ArtifactItemWrapper> comparator;

        public TimelineAdapter() {
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
            View view = View.inflate(parent.getContext(), R.layout.timeline_item, null);
            return new TimelineViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
            holder.time.setText(dataSet.get(position).getHappenedDateTime());
            holder.description.setText(dataSet.get(position).getDescription());
            holder.itemId = dataSet.get(position).getPostId();
            List<String> urls = dataSet.get(position).getLocalMediaDataUrls();
            Log.d(TAG, "urls from DB: " + urls.toString());
            if(urls.size() > 0) {
                // TODO implementation for displaying multiple images
                holder.media.setImageURI(Uri.parse(urls.get(0)));
            }
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
            notifyDataSetChanged();
        }
    }
}
