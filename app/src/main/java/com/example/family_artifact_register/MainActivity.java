package com.example.family_artifact_register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import static com.example.family_artifact_register.util.ActivityNavigator.navigateFromTo;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;


/**
 * @author XuLin Yang 904904,
 * @time 2019-8-10 17:01:49
 * @description main activity let user to choose to sign in or sign up
 */
public class MainActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // skip sign in if user have signed in before
//        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        // User is signed in (getCurrentUser() will be null if not signed in)
//        if (firebaseAuth.getCurrentUser() != null) {
//            // navigate to HomeActivity
//            navigateFromTo(MainActivity.this, HomeActivity.class);
//        }
//
//        // user click "Sign In" to be directed to sign in activity
//        final Button signInButton = (Button) findViewById(R.id.sign_in_button);
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                navigateFromTo(MainActivity.this, SignInActivity.class);
//            }
//        });
//
//        // user click "Sign Up" to be directed to sign up activity
//        final Button signUpButton = (Button) findViewById(R.id.sign_up_button);
//        signUpButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                navigateFromTo(MainActivity.this, SignUpActivity.class);
//            }
//        });
//
//        // user click "Phone verify" to be directed to phone verification activity
//        final Button phoneVeriButton = (Button) findViewById(R.id.phone_button);
//        phoneVeriButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                navigateFromTo(MainActivity.this, PhoneVerificationActivity.class);
//            }
//        });
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        ListView list = findViewById(R.id.friend_list);
        String[] friends = new String[] {"Tim", "Matt", "Leon", "coffee", "xulin", "zhuoqun", "haichao", "1", "2", "3", "4"};
        list.setAdapter(new MyAdapter(friends));
    }

        public class MyAdapter extends BaseAdapter {

            private String[] items;

            private MyAdapter(String[] items) {
                this.items = items;
            }
            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public String getItem(int position) {
                return items[position];
            }

            @Override
            public long getItemId(int position) {
                return items[position].hashCode();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup container) {
                Random rand = new Random();
                int[] avatars = new int[] {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5};
                int avatarchosed = avatars[rand.nextInt(5)];
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.friend_list_item, container, false);
                }

                ((TextView) convertView.findViewById(R.id.username))
                        .setText(getItem(position));
                ((ImageView) convertView.findViewById(R.id.avatar)).setImageResource(avatarchosed);
                return convertView;
            }
        }
}