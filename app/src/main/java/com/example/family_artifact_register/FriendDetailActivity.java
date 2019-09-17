package com.example.family_artifact_register;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FriendDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);

        // my_child_toolbar is defined in the layout file
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // force the system not to display action bar title
        ab.setDisplayShowTitleEnabled(false);

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        ImageView avatar = (ImageView) findViewById(R.id.avatar);
        TextView username = (TextView) findViewById(R.id.username);
        TextView nickname = (TextView) findViewById(R.id.nickname);
        TextView area = (TextView) findViewById(R.id.area);

        Bundle extras = getIntent().getExtras();
        String selectedUsername = "";
        if(extras != null) {
            selectedUsername = extras.getString("key");
        }

//        avatar.setImageResource(R.drawable.my_logo);
        username.setText(selectedUsername);
    }
}
