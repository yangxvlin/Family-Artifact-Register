package com.example.family_artifact_register.UI.Social;

import android.content.Intent;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.FoundationLayer.UserModel.UserInfo;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactDetailViewModel;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactDetailViewModelFactory;
import com.example.family_artifact_register.R;


public class ContactDetailActivity extends AppCompatActivity {

    public static final String TAG = ContactDetailActivity.class.getSimpleName();

    private ContactDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);

        // force the system not to display action bar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setBackgroundDrawable(this.getDrawable(R.drawable.gradient_background));

        ImageView avatar = (ImageView) findViewById(R.id.avatar);
        TextView username = (TextView) findViewById(R.id.username);
        TextView nickname = (TextView) findViewById(R.id.nickname);
        TextView area = (TextView) findViewById(R.id.area);

        RelativeLayout sendMessage = (RelativeLayout) findViewById(R.id.send_button);

        Intent intent = getIntent();
        String selectedUid = intent.getStringExtra("selectedUid");
//        username.setText(selectedUser);

        viewModel = ViewModelProviders.of(this, new ContactDetailViewModelFactory(getApplication(), selectedUid)).get(ContactDetailViewModel.class);

        Observer<UserInfo> contactObserver = new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo newData) {
                // display user detail information using the latest data
                Log.i(TAG, "some changes happened");
                username.setText(newData.getDisplayName());
                nickname.setText("nickName");
                area.setText("area");
            }
        };

        viewModel.getUser().observe(this, contactObserver);

        sendMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("sending messssssssage #####");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.friend_detail_menu, menu);
        return true;
    }
}
