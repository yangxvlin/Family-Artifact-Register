package com.example.family_artifact_register.UI.Social;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.family_artifact_register.FakeDB;
import com.example.family_artifact_register.R;

import java.util.ArrayList;

public class NewFriendDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend_detail);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ArrayList<String> dataSet = FakeDB.getInstance();

        ImageView avatar = (ImageView) findViewById(R.id.avatar_new);
        TextView username = (TextView) findViewById(R.id.username_new);
        TextView area = (TextView) findViewById(R.id.area_new);
        TextView addFriend = (TextView) findViewById(R.id.add_friend);

        Intent intent = getIntent();
        username.setText(intent.getStringExtra("key"));
        area.setText("");

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                System.out.println("add function working ########");
                FakeDB.getInstance().add(username.getText().toString());
                addFriend.setClickable(false);
                addFriend.setFocusable(false);
                addFriend.setBackground(null);
                addFriend.setText("Added to list");
            }
        });
    }
}
