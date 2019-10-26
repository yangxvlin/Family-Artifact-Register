package com.unimelb.family_artifact_register.UI.Artifact.ArtifactHub;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.Artifact.ArtifactHub.Util.HubModelAdapter;
import com.unimelb.family_artifact_register.UI.Artifact.ArtifactHub.Util.Model;

import java.util.ArrayList;

/**
 * @author Haichao Song 854035,
 * @time 2019-9-18 12:15:48
 * @description activity for artifact hub. Test class for manually typed input.
 * Deprecated now because already get data from backend.
 */
@Deprecated
public class HubActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    HubModelAdapter mHubModelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mHubModelAdapter);

    }

    /**
     * Get a list of models of card view
     *
     * @return a list of models
     */
    private ArrayList<Model> getMyList() {
        ArrayList<Model> models = new ArrayList<Model>();
        Model m = new Model();
        m.setUsername("Dudu");
        m.setDescription("This is Art1 Description.");
        m.setAvatar(R.drawable.my_logo);
        m.setPostimage(R.drawable.a1);
        m.setPublisher("Liguo Chen");
        models.add(m);

        m.setUsername("Peter");
        m.setDescription("This is Art2 Description.");
        m.setAvatar(R.drawable.my_logo);
        m.setPostimage(R.drawable.a2);
        m.setPublisher("Liguo Chen");
        models.add(m);

        m.setUsername("Calvin");
        m.setDescription("This is Art3 Description.");
        m.setAvatar(R.drawable.my_logo);
        m.setPostimage(R.drawable.a3);
        m.setPublisher("Zhuoqun Huang");
        models.add(m);

        m.setUsername("George");
        m.setDescription("This is Art4 Description.");
        m.setAvatar(R.drawable.my_logo);
        m.setPostimage(R.drawable.a4);
        m.setPublisher("Haichao Song");
        models.add(m);

        m.setUsername("Genius");
        m.setDescription("This is Art5 Description.");
        m.setAvatar(R.drawable.my_logo);
        m.setPostimage(R.drawable.a5);
        m.setPublisher("Richard");
        models.add(m);

        return models;
    }
}