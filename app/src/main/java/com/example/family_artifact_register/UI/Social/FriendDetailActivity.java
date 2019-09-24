package com.example.family_artifact_register.UI.Social;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.Util.BaseActionBarActivity;

public class FriendDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);

        // force the system not to display action bar title
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView avatar = (ImageView) findViewById(R.id.avatar);
        TextView username = (TextView) findViewById(R.id.username);
        TextView nickname = (TextView) findViewById(R.id.nickname);
        TextView area = (TextView) findViewById(R.id.area);

        TextView sendMessage = findViewById(R.id.send_message);

        Intent intent = getIntent();
        username.setText(intent.getStringExtra("key"));

        sendMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("sending messssssssage #####");
            }
        });
    }
}
