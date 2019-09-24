package com.example.family_artifact_register.UI.Post;

import android.os.Bundle;
import android.service.autofill.Dataset;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.ArtifactHub.HubModelAdapter;
import com.example.family_artifact_register.UI.ArtifactHub.Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    LinearLayoutManager layoutManager;
    DividerItemDecoration divider;

    private List<String> friendList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hub, container, false);
        setupRecyclerView(view);

        recyclerView = view.findViewById(R.id.recyclerView);
        checkFriend();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void setupRecyclerView(View view) {
        // get the view
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        // set layout manager for the view
        layoutManager = new LinearLayoutManager(view.getContext());

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        postList = new ArrayList<>();
        // set the adapter for the view
        postAdapter = new PostAdapter(getContext(), postList);
        recyclerView.setAdapter(postAdapter);

        // set the divider between list item
        divider = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(divider);
    }

    private void checkFriend(){
        friendList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase .getInstance().getReference("friends")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("friends");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friendList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    friendList.add(snapshot.getKey());
                }
                readpost();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readpost() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    for (String id : friendList) {
                        if (post.getPublisher().equals(id)) {
                            postList.add(post);
                        }
                    }
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
