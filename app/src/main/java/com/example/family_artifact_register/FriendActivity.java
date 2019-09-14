package com.example.family_artifact_register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

public class FriendActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private DividerItemDecoration divider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] dataSet = new String[] {"Tim", "Matt", "Leon", "coffee", "xulin", "zhuoqun", "haichao", "1", "2", "3", "4"};

        adapter = new MyAdapter(dataSet);
        recyclerView.setAdapter(adapter);

        divider = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(divider);
    }

    // probably become a separate class in the future
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private String[] dataSet;
        private final int[] avatars = new int[] {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5};

        // probably become a separate class in the future
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;
            public ImageView imageView;
            public MyViewHolder(View itemView) {
                super(itemView);
                this.textView = itemView.findViewById(R.id.username);
                this.imageView = itemView.findViewById(R.id.avatar);
            }
        }

        public MyAdapter(String[] dataSet) {
            this.dataSet = dataSet;
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
            Random rand = new Random();
            holder.textView.setText(dataSet[position]);
            holder.imageView.setImageResource(avatars[rand.nextInt(avatars.length)]);
        }

        @Override
        public int getItemCount () {
            return dataSet.length;
        }
    }
}
