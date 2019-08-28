package com.example.family_artifact_register;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.family_artifact_register.util.ActivityNavigator.navigateFromTo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * @author XuLin Yang 904904,
 * @time 2019-8-10 17:01:49
 * @description main activity let user to choose to sign in or sign up
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // skip sign in if user have signed in before
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        // User is signed in (getCurrentUser() will be null if not signed in)
        if (firebaseAuth.getCurrentUser() != null) {
            // navigate to HomeActivity
            navigateFromTo(MainActivity.this, HomeActivity.class);
        }

        // user click "Sign In" to be directed to sign in activity
        final Button signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateFromTo(MainActivity.this, SignInActivity.class);
            }
        });

        // user click "Sign Up" to be directed to sign up activity
        final Button signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateFromTo(MainActivity.this, SignUpActivity.class);
            }
        });

        // user click "Phone verify" to be directed to phone verification activity
        final Button phoneVeriButton = (Button) findViewById(R.id.phone_button);
        phoneVeriButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateFromTo(MainActivity.this, PhoneVerificationActivity.class);
            }
        });
    }
}