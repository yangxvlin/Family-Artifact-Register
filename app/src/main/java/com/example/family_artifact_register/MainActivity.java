package com.example.family_artifact_register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.util.ExtraConstants;
import com.google.android.gms.common.Scopes;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XuLin Yang 904904,
 * @time 2019-8-10 17:01:49
 * @description main activity let user to choose to sign in or sign up
 */
public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100;
    private static final String TAG = "MainActivity";
    private static final boolean mUseGoogleProvider = true;
    private static final boolean mUseFacebookProvider = true;
    private static final boolean mUseTwitterProvider = true;
    private static final boolean mUseEmailProvider = true;
    private static final boolean mUseEmailLinkProvider = true;
    private static final boolean mUsePhoneProvider = true;
    private static final boolean mGoogleScopeDriveFile = true;
    private static final boolean mGoogleScopeYoutubeData = true;
    private static final boolean mFacebookPermissionFriends = true;
    private static final boolean mFacebookPermissionPhotos = true;
    private static final boolean mRequireName = false;
    private static final boolean mAllowNewEmailAccounts = false;
    private static final boolean mEnableCredentialSelector = true;
    private static final boolean mEnableHintSelector = true;


    private List<String> getGoogleScopes() {
        List<String> result = new ArrayList<>();
        if (mGoogleScopeYoutubeData) {
            result.add("https://www.googleapis.com/auth/youtube.readonly");
        }
        if (mGoogleScopeDriveFile) {
            result.add(Scopes.DRIVE_FILE);
        }
        return result;
    }

    private List<String> getFacebookPermissions() {
        List<String> result = new ArrayList<>();
        if (mFacebookPermissionFriends) {
            result.add("user_friends");
        }
        if (mFacebookPermissionPhotos) {
            result.add("user_photos");
        }
        return result;
    }

    @NonNull
    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private List<AuthUI.IdpConfig> getSelectedProviders() {
        List<AuthUI.IdpConfig> selectedProviders = new ArrayList<>();

        if (mUseGoogleProvider) {
            selectedProviders.add(
                    new AuthUI.IdpConfig.GoogleBuilder().setScopes(getGoogleScopes()).build());
        }

        if (mUseFacebookProvider) {
            selectedProviders.add(new AuthUI.IdpConfig.FacebookBuilder()
                    .setPermissions(getFacebookPermissions())
                    .build());
        }

        if (mUseTwitterProvider) {
            selectedProviders.add(new AuthUI.IdpConfig.TwitterBuilder().build());
        }

        if (mUseEmailProvider) {
            selectedProviders.add(new AuthUI.IdpConfig.EmailBuilder()
                    .setRequireName(mRequireName)
                    .setAllowNewAccounts(mAllowNewEmailAccounts)
                    .build());
        }

        if (mUseEmailLinkProvider) {
            ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                    .setAndroidPackageName("com.firebase.uidemo", true, null)
                    .setHandleCodeInApp(true)
                    .setUrl("https://google.com")
                    .build();

            selectedProviders.add(new AuthUI.IdpConfig.EmailBuilder()
                    .setAllowNewAccounts(true)
                    .setActionCodeSettings(actionCodeSettings)
                    .enableEmailLinkSignIn()
                    .build());
        }

        if (mUsePhoneProvider) {
        selectedProviders.add(new AuthUI.IdpConfig.PhoneBuilder().build());
        }

        return selectedProviders;
    }

    @OnClick(R.id.button_sign_in)
    public void signIn() {
        startActivityForResult(buildSignInIntent(/*link=*/null), RC_SIGN_IN);
    }

    public void signInWithEmailLink(@Nullable String link) {
        startActivityForResult(buildSignInIntent(link), RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode, data);
        }
    }

    private void handleSignInResponse(int resultCode, @Nullable Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        // Successfully signed in
        if (resultCode == RESULT_OK) {
            startSignedInActivity(response);
            finish();
        } else {
            // Sign in failed
            Log.e(TAG, "Sign-in error: ", response.getError());
        }
    }

    private void startSignedInActivity(@Nullable IdpResponse response) {
        startActivity(SignInActivity.createIntent(this, response));
    }

    @NonNull
    public Intent buildSignInIntent(@Nullable String link) {
        AuthUI.SignInIntentBuilder builder = AuthUI.getInstance().createSignInIntentBuilder()
                .setTheme(AuthUI.getDefaultTheme())
                .setLogo(AuthUI.NO_LOGO)
                .setAvailableProviders(getSelectedProviders())
                .setIsSmartLockEnabled(mEnableCredentialSelector,
                                       mEnableHintSelector);


//        if (getSelectedTosUrl() != null && getSelectedPrivacyPolicyUrl() != null) {
//            builder.setTosAndPrivacyPolicyUrls(
//                    getSelectedTosUrl(),
//                    getSelectedPrivacyPolicyUrl());
//        }

        if (link != null) {
            builder.setEmailLink(link);
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null && auth.getCurrentUser().isAnonymous()) {
            builder.enableAnonymousUsersAutoUpgrade();
        }

        return builder.build();
    }
}
