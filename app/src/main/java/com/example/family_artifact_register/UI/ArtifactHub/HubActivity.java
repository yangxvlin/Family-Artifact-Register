package com.example.family_artifact_register.UI.ArtifactHub;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;

import java.util.ArrayList;

/**
 * @author Haichao Song 854035,
 * @time 2019-9-18 12:15:48
 * @description activity for artifact hub.
 */
public class HubActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    HubModelAdapter mHubModelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mHubModelAdapter = new HubModelAdapter(this, getMyList());
        mRecyclerView.setAdapter(mHubModelAdapter);

    }

    /**
     * Get a list of models of card view
     * @return a list of models
     */
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

        m = new Model();
        m.setUsername("Username4");
        m.setTitle("This is Art4");
        m.setDescription("This is Art4 Description.");
        m.setAvatar(R.drawable.my_logo);
        m.setImg(R.drawable.my_logo);
        models.add(m);

        return models;
    }
}