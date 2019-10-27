package com.unimelb.family_artifact_register.UI.Social.NewContact;

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

import com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter.NewContactDetailViewModel;
import com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter.NewContactDetailViewModelFactory;
import com.unimelb.family_artifact_register.PresentationLayer.Util.UserInfoWrapper;
import com.unimelb.family_artifact_register.R;

/**
 * UI class for displaying the detail information of new contacts in the system
 */
public class NewContactDetailActivity extends AppCompatActivity {

    /**
     * class tag
     */
    public static final String TAG = NewContactDetailActivity.class.getSimpleName();

    // view model for this activity
    private NewContactDetailViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend_detail);

        ActionBar actionBar = getSupportActionBar();
        // force the system not to display action bar title
        actionBar.setDisplayShowTitleEnabled(false);
        // set gradient color for action bar
        actionBar.setBackgroundDrawable(this.getDrawable(R.drawable.gradient_background));

        ImageView avatar = findViewById(R.id.avatar_new);
        TextView username = findViewById(R.id.username_new);
        TextView email = findViewById(R.id.email_new);

        TextView addFriend = findViewById(R.id.add_friend);

        // get the id of the user whose information is to be displayed
        Intent intent = getIntent();
        String selectedUid = intent.getStringExtra("selectedUid");

        // get view model
        viewModel = ViewModelProviders.of(this, new NewContactDetailViewModelFactory(getApplication(), selectedUid)).get(NewContactDetailViewModel.class);

        // retrieve data from DB
        viewModel.getUser().observe(this, new Observer<UserInfoWrapper>() {
            @Override
            public void onChanged(UserInfoWrapper newData) {
                Log.d(TAG, "user data come back from DB: " + newData.toString());
                String s = newData.getDisplayName();
                if (s != null && s.length() > 0) {
                    username.setText(s);
                }
                s = newData.getEmail();
                if (s != null && s.length() > 0) {
                    email.setText(s);
                }
                String url = newData.getPhotoUrl();
                if (url != null) {
                    Log.d(TAG, "URL is " + url);
                    avatar.setImageURI(Uri.parse(url));
                }
            }
        });

        // click listener for add friend button
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFriend.setClickable(false);
                addFriend.setFocusable(false);
                addFriend.setBackground(null);
                viewModel.addFriend(viewModel.getUserUid());
                addFriend.setText(R.string.NewContactDetailActivity_add_button);
            }
        });
    }
}
