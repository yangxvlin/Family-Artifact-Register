package com.example.family_artifact_register.UI.Social;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactDetailViewModel;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactDetailViewModelFactory;
import com.example.family_artifact_register.R;


public class FriendDetailActivity extends AppCompatActivity {

    public static final String TAG = FriendDetailActivity.class.getSimpleName();

    private ContactDetailViewModel viewModel;

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
        String selectedUser = intent.getStringExtra("key");
//        username.setText(selectedUser);

        viewModel = ViewModelProviders.of(this, new ContactDetailViewModelFactory(getApplication(), selectedUser)).get(ContactDetailViewModel.class);

        Observer<User> contactObserver = new Observer<User>() {
            @Override
            public void onChanged(User newData) {
                // display user detail information using the latest data
                Log.e(TAG, "some changes happened");
                username.setText(newData.username);
                nickname.setText(newData.nickname);
                area.setText(newData.area);
            }
        };

        viewModel.getFriend(selectedUser).observe(this, contactObserver);
        Log.e(TAG, "finished subscripting");

        sendMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("sending messssssssage #####");
            }
        });
    }
}
