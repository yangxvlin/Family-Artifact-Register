package com.example.family_artifact_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.family_artifact_register.util.ActivityNavigator.navigateFromTo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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


    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private DividerItemDecoration divider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] dataSet = new String[] {"Tim", "Matt", "Leon", "coffee", "xulin", "zhuoqun", "haichao", "1", "2", "3", "4"};

        adapter = new MyAdapter(dataSet);
        recyclerView.setAdapter(adapter);

        divider = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(divider);
    }

    // probably become a separate class in the future
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private String[] dataSet;
        private final int[] avatars = new int[] {R.drawable.my_logo};

        // probably become a separate class in the future
        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView textView;
            public ImageView imageView;
            public MyViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                this.textView = itemView.findViewById(R.id.username);
                this.imageView = itemView.findViewById(R.id.avatar);
            }

            @Override
            public void onClick(View view) {
                System.out.println("#############");
//                System.out.println(textView.getText());
                String value = (String) textView.getText();
                Intent i = new Intent((AppCompatActivity) view.getContext(), FriendDetailActivity.class);
                i.putExtra("key", value);
                startActivity(i);

//                navigateFromTo((AppCompatActivity) view.getContext(), FriendDetailActivity.class);
            }
        }

        public MyAdapter(String[] dataSet) {
            this.dataSet = dataSet;
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
            Random rand = new Random();
            holder.textView.setText(dataSet[position]);
//            holder.imageView.setImageResource(R.drawable.my_logo);
        }

        @Override
        public int getItemCount () {
            return dataSet.length;
        }
    }
}