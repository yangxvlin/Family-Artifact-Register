package com.example.family_artifact_register.UI.Social;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.family_artifact_register.FakeDB;
import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.NewContactDetailViewModel;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.NewContactDetailViewModelFactory;
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

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ArrayList<String> dataSet = FakeDB.getInstance();

        ImageView avatar = (ImageView) findViewById(R.id.avatar_new);
        TextView username = (TextView) findViewById(R.id.username_new);
        TextView area = (TextView) findViewById(R.id.area_new);

        TextView addFriend = (TextView) findViewById(R.id.add_friend);

        Intent intent = getIntent();
        String selectedUid = intent.getStringExtra("selectedUid");
//        username.setText();
//        area.setText("");

        viewModel = ViewModelProviders.of(this, new NewContactDetailViewModelFactory(getApplication(), selectedUid)).get(NewContactDetailViewModel.class);

        Observer<UserInfo> newContactObserver = new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo newData) {
                username.setText(newData.getDisplayName());
                area.setText("area");
            }
        };

        viewModel.getUser().observe(this, newContactObserver);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                System.out.println("add function working ########");
//                FakeDB.getInstance().add(username.getText().toString());
                addFriend.setClickable(false);
                addFriend.setFocusable(false);
                addFriend.setBackground(null);
                viewModel.insert(FirebaseAuth.getInstance().getCurrentUser().getUid());
                addFriend.setText("Added to list");
            }
        });
    }
}
