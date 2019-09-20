package com.example.family_artifact_register.UI.Social;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.Util.BaseActionBarActivity;

public class FriendDetailActivity extends BaseActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);

        // force the system not to display action bar title
        getMyActionBar().setDisplayShowTitleEnabled(false);

        ImageView avatar = (ImageView) findViewById(R.id.avatar);
        TextView username = (TextView) findViewById(R.id.username);
        TextView nickname = (TextView) findViewById(R.id.nickname);
        TextView area = (TextView) findViewById(R.id.area);

        TextView function = findViewById(R.id.function);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            username.setText(extras.getString("key"));
            // page for new friend
            if(extras.getString("new friend") != null) {
                nickname.setText("");
                area.setText("");
                function.setText("Add to contact list");
            }
        }

//        avatar.setImageResource(R.drawable.my_logo);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_friend_detail;
    }
}
