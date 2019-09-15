package com.example.family_artifact_register;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HubActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMyAdapter = new MyAdapter(this, getMyList());
        mRecyclerView.setAdapter(mMyAdapter);

    }

    private ArrayList<Model> getMyList(){
        ArrayList<Model> models = new ArrayList<Model>();
        Model m = new Model();
        m.setTitle("This is Art1");
        m.setDescription("This is Art1 Description.");
        m.setImg(R.drawable.my_logo);
        models.add(m);

        Model n = new Model();
        n.setTitle("This is Art2");
        n.setDescription("This is Art2 Description.");
        n.setImg(R.drawable.my_logo);
        models.add(n);

        Model v = new Model();
        v.setTitle("This is Art3");
        v.setDescription("This is Art3 Description.");
        v.setImg(R.drawable.my_logo);
        models.add(v);

        return models;
    }
}