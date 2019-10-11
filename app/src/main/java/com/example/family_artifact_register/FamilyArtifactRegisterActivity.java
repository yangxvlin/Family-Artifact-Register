package com.example.family_artifact_register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.family_artifact_register.FoundationLayer.Util.FirebaseAuthHelper;
import com.example.family_artifact_register.Util.Callback;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.util.ExtraConstants;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class FamilyArtifactRegisterActivity extends AppCompatActivity implements Callback<Void> {
    /**
     * class tag
     */
    public static final String TAG = FamilyArtifactRegisterActivity.class.getSimpleName();

    /**
     * firebase request code
     */
    public static final int RC_SIGN_IN = 1;

    /**
     * firebase request code
     */
    public static final int CHECK_USER_DB = 2;

    /**
     * control firebase state info
     */
    private FirebaseAuth.AuthStateListener mAuthStateListner;

    private AuthUI.SignInIntentBuilder signInIntentBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_artifact_register);

        buildSignInIntentBuilder();
        catchEmailLinkSignIn();
        createCheckAndSigninListener();

        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListner);
    }

    private void buildSignInIntentBuilder() {

        ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                        .setAndroidPackageName("com.example.family_artifact_register", true, null)
                        .setHandleCodeInApp(true)
                        .setUrl(getString(R.string.dynamic_link_url))
                        .build();

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder()
                        .enableEmailLinkSignIn()
                        .setActionCodeSettings(actionCodeSettings)
                        .build(),
                // new AuthUI.IdpConfig.EmailBuilder().setRequireName(false).build(),
                new AuthUI.IdpConfig.PhoneBuilder()
                        .build(),
                new AuthUI.IdpConfig.GoogleBuilder()
                        .build(),
                new AuthUI.IdpConfig.FacebookBuilder()
                        .build());

        signInIntentBuilder = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(providers)
                .setLogo(R.drawable.icon_forget_me_not_1);
    }

    private void catchEmailLinkSignIn() {
        Log.d(TAG, "Intent: " + getIntent().getExtras());
        if (AuthUI.canHandleIntent(getIntent())) {
            if (getIntent().getExtras() == null) {
                return;
            }
            String link = getIntent().getExtras().getString(ExtraConstants.EMAIL_LINK_SIGN_IN);
            Log.d(TAG, "link: " + link);
            if (link != null) {
                signInIntentBuilder.setEmailLink(link);
            }
        }
    }

    private void createCheckAndSigninListener() {
        // set firebase sign in listener
        mAuthStateListner = firebaseAuth -> {
            // Already logged in
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Log.d(TAG, "user already signed in");
                // Check user even if signed in to register him to database (if haven't)
                FirebaseAuthHelper.getInstance().checkRegisterUser(user, this, CHECK_USER_DB);

                // Toast.makeText(this, "User Signed In", Toast.LENGTH_SHORT).show();
//                Toasty.info(this, R.string.user_signed_in, Toasty.LENGTH_LONG)
//                        .show();
//                startHomeActivity();
            } else {
                Log.d(TAG, "user hasn't signed in");
                // Signed out or hasn't logged in
                startActivityForResult(
                        signInIntentBuilder
                                .build(),
                        RC_SIGN_IN
                );

            }
        };
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void startCollectUserInfoActivity() {
        Intent intent = new Intent(this, CollectUserInfoActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Log.i(TAG, String.format("User signin - resultCode: %d", resultCode));
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser == null) {
                    Log.e(TAG, "Auth with FireBase failed!", new Throwable());
                    return;
                }
                // Successfully signed in, get user and start home activity
                FirebaseAuthHelper.getInstance().checkRegisterUser(firebaseUser,
                        this , CHECK_USER_DB);
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                Log.e(TAG, "Auth failed, Error code: " + response.getError().getErrorCode());
            }
        }
    }

    @Override
    public void callback(int requestCode, int resultCode, Void data) {
        Log.d(TAG, "Auth callback triggered with request code: " + requestCode +
                ", result code: " + resultCode);
        if (requestCode == CHECK_USER_DB) {
            switch (resultCode) {
                case (FirebaseAuthHelper.RESULT_USER_EXIST):
//                     Toast.makeText(this, R.string.user_signed_in, Toast.LENGTH_SHORT).show();
                    Toasty.info(this, R.string.user_signed_in, Toasty.LENGTH_LONG)
                            .show();
                     startHomeActivity();
//                    startCollectUserInfoActivity();
                    break;
                case (FirebaseAuthHelper.RESULT_NEW_USER):
                    // Toast.makeText(this, R.string.registration_successful,
                    //        Toast.LENGTH_SHORT).show();
                    Toasty.success(this, R.string.registration_successful, Toasty.LENGTH_LONG)
                            .show();
                    startCollectUserInfoActivity();
                    break;
                case (FirebaseAuthHelper.RESULT_FAILURE):
                    Log.e(TAG, "Storing user information to database failed");
                    Toasty.error(this, R.string.auth_failure, Toasty.LENGTH_LONG)
                            .show();
                    break;
            }
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d(TAG, "FamilyArtifactRegisterMain onResume");
//        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListner);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "FamilyArtifactRegisterMain onDestroy");
        FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListner);
    }
}
