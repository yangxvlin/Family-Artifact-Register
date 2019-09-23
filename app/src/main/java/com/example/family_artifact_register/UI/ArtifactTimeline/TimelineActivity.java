package com.example.family_artifact_register.UI.ArtifactTimeline;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.Collections;

public class TimelineActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        getSupportActionBar().setTitle("My Artifact timeline");

        // to be replaced with string of artifact items
        String[] x = new String[] {"1", "2", "3", "4", "5", "6"};
        ArrayList<String> data = new ArrayList<>();
        Collections.addAll(data, x);

        recyclerView = (RecyclerView) findViewById(R.id.timeline_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Myadapter(data));

    }

    public class Myadapter extends RecyclerView.Adapter<Myadapter.Myviewholder> {

        private ArrayList<String> dataSet;

        public Myadapter(ArrayList<String> dataSet) {
            this.dataSet = dataSet;
        }

        @NonNull
        @Override
        public Myadapter.Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.timeline_item, null);
            return new Myviewholder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
            holder.title.setText(dataSet.get(position));
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        @Override
        public int getItemViewType(int position) {
            return TimelineView.getTimeLineViewType(position, getItemCount());
        }

        public class Myviewholder extends RecyclerView.ViewHolder {

            public TimelineView timelineView;
            public TextView time;
            public TextView title;
            public TextView description;
            public ImageView photo;

            public Myviewholder(@NonNull View itemView, int viewType) {
                super(itemView);
                this.timelineView = itemView.findViewById(R.id.timeline);
                this.time= itemView.findViewById(R.id.timeline_item_time);
                this.title = itemView.findViewById(R.id.timeline_item_title);
                this.description = itemView.findViewById(R.id.timeline_item_description);
                this.photo = itemView.findViewById(R.id.timeline_item_photo);
                timelineView.initLine(viewType);
            }
        }
    }
}
