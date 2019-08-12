package com.example.family_artifact_register;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import io.opencensus.tags.Tag;

import static util.ActivityNavigator.navigateFromTo;

/**
 * @author XuLin Yang 904904,
 * @time 2019-8-10 17:02:26
 * @description sign in activity for user to use account and password to sign in
 */
public class SignInActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click to navigate to main activity
                navigateFromTo(SignInActivity.this, MainActivity.class);
            }
        });


        final TextInputLayout passwordTextInput = findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText =findViewById(R.id.password_edit_text);
        final TextInputLayout usernameTextInput = findViewById(R.id.username_text_input);
        final TextInputEditText usernameEditText = findViewById(R.id.username_edit_text);
        final MaterialButton nextButton = findViewById(R.id.next_button);
//        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Set an error if the password is less than 8 characters.
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(getString(R.string.error_password));
                } else {
                    passwordTextInput.setError(null); // Clear the error
                }

                if (!isUserNameValid(usernameEditText.getText())) {
                    usernameTextInput.setError(getString(R.string.error_username));
                } else {
                    usernameTextInput.setError(null); // Clear the error
                }
            }
        });

        // Clear the error once more than 8 characters are typed.
        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                boolean isPasswordValid = isPasswordValid(passwordEditText.getText()),
                        isUsernameValid = isUserNameValid(usernameEditText.getText());

                if (isPasswordValid) {
                    passwordTextInput.setError(null); //Clear the error
                }
                if (isUsernameValid) {
                    usernameTextInput.setError(null); //Clear the error
                }


//                firebaseAuth.signInWithEmailAndPassword(usernameEditText.getText().toString(), passwordEditText.getText().toString())
//                            .addOnCompleteListener(
//                                    new OnCompleteListener<AuthResult>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<AuthResult> task) {
//                                            // user exists in fire base database
//                                            if (task.isSuccessful()) {
//                                                // direct to Home page
//                                                Intent activityChangeIntent = new Intent(SignInActivity.this, HomeActivity.class);
//                                                SignInActivity.this.startActivity(activityChangeIntent);
//                                            }
//                                        }
//                                    }
//                            );
                return false;
            }
        });

        final String USER_KEY = "user";
        final String PASSWORD_KEY = "password";
        final String TAG = "LogInAccount";
        DocumentReference mDocRef = FirebaseFirestore.getInstance().
                document("sampleData/inspiration");
        String passwordText = passwordEditText.getText().toString();
        String userText = usernameEditText.getText().toString();
        HashMap<String, Object> account = new HashMap<String, Object>();
        account.put(USER_KEY, userText);
        account.put(PASSWORD_KEY, passwordText);
        mDocRef.set(account).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Document has been saved!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Document has not been saved!", e);
            }
        });

    }

    /**
    In reality, this will have more complex logic including, but not limited to, actual
    authentication of the username and password.
    */
    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }

    private boolean isUserNameValid(@Nullable Editable text) {
        return text != null && text.length() >= 1;
    }
}
