package com.example.family_artifact_register;

import android.os.Bundle;
import android.view.View;
import android.text.Editable;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static util.ActivityNavigator.navigateFromTo;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final TextInputLayout usernameTextInput = findViewById(R.id.username_text_input);
        final TextInputEditText usernameEditText = findViewById(R.id.username_edit_text);

        final TextInputLayout emailTextInput = findViewById(R.id.email_text_input);
        final TextInputEditText emailEditText = findViewById(R.id.email_edit_text);

        final TextInputLayout passwordTextInput = findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = findViewById(R.id.password_edit_text);

        final TextInputLayout passwordConfirmTextInput = findViewById(R.id.password_confirm_text_input);
        final TextInputEditText passwordConfirmEditText = findViewById(R.id.password_confirm_edit_text);

        final TextInputLayout genderTextInput = findViewById(R.id.gender_text_input);
        final TextInputEditText genderEditText = findViewById(R.id.gender_edit_text);

        final TextInputLayout numberTextInput = findViewById(R.id.number_text_input);
        final TextInputEditText numberEditText = findViewById(R.id.number_edit_text);

        // user click "Confirm" to be directed to sign in activity
        final MaterialButton confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean allInputValid = true;

                if(isEmpty(usernameEditText.getText())) {
                    usernameTextInput.setError(getString(R.string.error_sign_up_username));
                    allInputValid = false;
                }
                else { usernameTextInput.setError(null); }

                if(isEmpty(emailEditText.getText())) {
                    emailTextInput.setError(getString(R.string.error_sign_up_email));
                    allInputValid = false;
                }
                else { emailTextInput.setError(null); }

                if(isEmpty(passwordEditText.getText())) {
                    passwordTextInput.setError(getString(R.string.error_sign_up_password));
                    allInputValid = false;
                }
                else { passwordTextInput.setError(null); }

                if(isEmpty(passwordConfirmEditText.getText())) {
                    passwordConfirmTextInput.setError(getString(R.string.error_sign_up_password_confirm));
                    allInputValid = false;
                }
                else { passwordConfirmTextInput.setError(null); }

                if(isEmpty(genderEditText.getText())) {
                    genderTextInput.setError(getString(R.string.error_sign_up_gender));
                    allInputValid = false;
                }
                else { genderTextInput.setError(null); }

                if(isEmpty(numberEditText.getText())) {
                    numberTextInput.setError(getString(R.string.error_sign_up_number));
                    allInputValid = false;
                }
                else { numberTextInput.setError(null); }

                if(allInputValid) {
                    navigateFromTo(SignUpActivity.this, SignInActivity.class);
                }
            }
        });

        // user click "Cancel" to be directed to main activity
        final MaterialButton cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                navigateFromTo(SignUpActivity.this, MainActivity.class);
            }
        });
    }

    // check if the input is empty
    private boolean isEmpty(@Nullable Editable text) {
        return text == null || text.length() == 0;
    }
}
