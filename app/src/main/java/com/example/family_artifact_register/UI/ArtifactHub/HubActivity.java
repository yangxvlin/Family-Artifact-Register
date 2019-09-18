package com.example.family_artifact_register.UI.ArtifactHub;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.ArtifactHub.Model;
import com.example.family_artifact_register.UI.ArtifactHub.MyAdapter;

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
        m.setUsername("Username1");
        m.setTitle("This is Art1");
        m.setDescription("This is Art1 Description.");
        m.setAvatar(R.drawable.my_logo);
        m.setImg(R.drawable.my_logo);
        models.add(m);

        m = new Model();
        m.setUsername("Username2");
        m.setTitle("This is Art2");
        m.setDescription("This is Art2 Description.");
        m.setAvatar(R.drawable.my_logo);
        m.setImg(R.drawable.my_logo);
        models.add(m);

        m = new Model();
        m.setUsername("Username3");
        m.setTitle("This is Art3");
        m.setDescription("This is Art3 Description.");
        m.setAvatar(R.drawable.my_logo);
        m.setImg(R.drawable.my_logo);
        models.add(m);

        return models;
    }
}