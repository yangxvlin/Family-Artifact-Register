package com.unimelb.family_artifact_register.UI.Social.Contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter.ContactDetailViewModel;
import com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter.ContactDetailViewModelFactory;
import com.unimelb.family_artifact_register.PresentationLayer.Util.UserInfoWrapper;
import com.unimelb.family_artifact_register.R;

/**
 * UI class for displaying the detail information of contacts in the system
 */
public class ContactDetailActivity extends AppCompatActivity {

    /**
     * class tag
     */
    public static final String TAG = ContactDetailActivity.class.getSimpleName();

    // view model for this activity
    private ContactDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);

        ActionBar actionBar = getSupportActionBar();
        // force the system not to display action bar title
        actionBar.setDisplayShowTitleEnabled(false);
        // set gradient color for action bar
        actionBar.setBackgroundDrawable(this.getDrawable(R.drawable.gradient_background));

        ImageView avatar = findViewById(R.id.avatar);
        TextView username = findViewById(R.id.username);
        TextView phoneNumber = findViewById(R.id.phone_number);
        TextView email = findViewById(R.id.email);

        TextView itemNumber = findViewById(R.id.item_number);
        itemNumber.setSelected(true);
        TextView timelineNumber = findViewById(R.id.timeline_number);
        timelineNumber.setSelected(true);
        TextView friendNumber = findViewById(R.id.friend_number);
        friendNumber.setSelected(true);

        phoneNumber.setSelected(true);
        email.setSelected(true);

        // get the id of the user whose information is to be displayed
        Intent intent = getIntent();
        String selectedUid = intent.getStringExtra("selectedUid");

        // get view model
        viewModel = ViewModelProviders.of(this, new ContactDetailViewModelFactory(getApplication(), selectedUid)).get(ContactDetailViewModel.class);

        // retrieve data from DB
        viewModel.getUser().observe(this, new Observer<UserInfoWrapper>() {
            @Override
            public void onChanged(UserInfoWrapper newData) {
                // display user detail information using the latest data
                Log.i(TAG, "some changes happened");
                Log.d(TAG, "user info: " + newData.toString());
                username.setText(newData.getDisplayName());
//                getSupportActionBar().setTitle(newData.getDisplayName());
//                phoneNumber.setText(newData.getPhoneNumber());
                email.setText(newData.getEmail());
                itemNumber.setText(intToString(newData.getArtifactItemIds().size()));
                itemNumber.setSelected(true);
                timelineNumber.setText(intToString(newData.getArtifactTimelineIds().size()));
                timelineNumber.setSelected(true);
                friendNumber.setText(intToString(newData.getFriendUids().size()));
                friendNumber.setSelected(true);
                String url = newData.getPhotoUrl();
                if (url != null) {
                    Log.d(TAG, "URL is not null");
                    avatar.setImageURI(Uri.parse(url));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.friend_detail_menu, menu);
        return true;
    }

    // convert int to string with appropriate format
    private String intToString(int n) {
        if (n < 1000) {
            return Integer.toString(n);
        } else if (n < 10000) {
            String numberString = Integer.toString(n);
            return numberString.charAt(0) + "." + numberString.charAt(1) + " K";
        } else {
            Log.e(TAG, "input number overflow!");
            return null;
        }
    }
}
