package com.example.family_artifact_register.UI.Social;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.NewContactDetailViewModel;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.NewContactDetailViewModelFactory;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.UserInfoWrapper;
import com.example.family_artifact_register.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class NewContactDetailActivity extends AppCompatActivity {

    public static final String TAG = NewContactDetailActivity.class.getSimpleName();

    private NewContactDetailViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setBackgroundDrawable(this.getDrawable(R.drawable.gradient_background));

        ImageView avatar = (ImageView) findViewById(R.id.avatar_new);
        TextView username = (TextView) findViewById(R.id.username_new);
        TextView email = (TextView) findViewById(R.id.email_new);

        TextView addFriend = (TextView) findViewById(R.id.add_friend);

        Intent intent = getIntent();
        String selectedUid = intent.getStringExtra("selectedUid");

        viewModel = ViewModelProviders.of(this, new NewContactDetailViewModelFactory(getApplication(), selectedUid)).get(NewContactDetailViewModel.class);

        viewModel.getUser().observe(this, new Observer<UserInfoWrapper>() {
            @Override
            public void onChanged(UserInfoWrapper newData) {
                Log.d(TAG, "user data come back from DB: " + newData.toString());
                String s = newData.getDisplayName();
                if(s != null && s.length() > 0) {
                    username.setText(s);
                }
                s = newData.getEmail();
                if(s != null && s.length() > 0) {
                    email.setText(s);
                }
                String url = newData.getPhotoUrl();
                if(url != null) {
                    Log.d(TAG, "URL is " + url);
                    avatar.setImageURI(Uri.parse(url));
                }
            }
        });

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                System.out.println("add function working ########");
//                FakeDB.getInstance().add(username.getText().toString());
                addFriend.setClickable(false);
                addFriend.setFocusable(false);
                addFriend.setBackground(null);
                viewModel.addFriend(viewModel.getUserUid());
                // TODO add to string resource
                addFriend.setText("Invitation Sent");
            }
        });
    }
}
