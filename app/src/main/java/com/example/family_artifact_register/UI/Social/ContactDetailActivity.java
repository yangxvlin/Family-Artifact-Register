package com.example.family_artifact_register.UI.Social;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactDetailViewModel;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactDetailViewModelFactory;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.UserInfoWrapper;
import com.example.family_artifact_register.R;


public class ContactDetailActivity extends AppCompatActivity {

    public static final String TAG = ContactDetailActivity.class.getSimpleName();

    private ContactDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);

        // force the system not to display action bar title
//        getSupportActionBar().hide();
        Toolbar toolbar = (Toolbar) findViewById(R.id.user_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView avatar = (ImageView) findViewById(R.id.avatar);
        TextView username = (TextView) findViewById(R.id.username);
        TextView phoneNumber = (TextView) findViewById(R.id.phone_number);
        TextView email = (TextView) findViewById(R.id.email);

        phoneNumber.setSelected(true);
        email.setSelected(true);

        Intent intent = getIntent();
        String selectedUid = intent.getStringExtra("selectedUid");
//        username.setText(selectedUser);

        viewModel = ViewModelProviders.of(this, new ContactDetailViewModelFactory(getApplication(), selectedUid)).get(ContactDetailViewModel.class);

        Observer<UserInfoWrapper> contactObserver = new Observer<UserInfoWrapper>() {
            @Override
            public void onChanged(UserInfoWrapper newData) {
                // display user detail information using the latest data
                Log.i(TAG, "some changes happened");
                Log.d(TAG, "user info: " + newData.toString());
                username.setText(newData.getDisplayName());
//                getSupportActionBar().setTitle(newData.getDisplayName());
//                phoneNumber.setText(newData.getPhoneNumber());
                email.setText(newData.getEmail());
                String url = newData.getPhotoUrl();
                if(url != null) {
                    Log.d(TAG, "URL is not null");
                    avatar.setImageURI(Uri.parse(url));
                }
            }
        };

        viewModel.getUser().observe(this, contactObserver);

//        sendMessage.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                System.out.println("sending messssssssage #####");
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.friend_detail_menu, menu);
        return true;
    }
}
