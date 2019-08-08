package com.example.family_artifact_register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignInActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(SignInActivity.this, MainActivity.class);

                // currentContext.startActivity(activityChangeIntent);
                SignInActivity.this.startActivity(activityChangeIntent);
            }
        });


        final TextInputLayout passwordTextInput = findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText =findViewById(R.id.password_edit_text);
        final MaterialButton nextButton = findViewById(R.id.next_button);

        // Set an error if the password is less than 8 characters.
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(getString(R.string.error_password));
                } else {
                    passwordTextInput.setError(null); // Clear the error
                }
            }
        });

        // Clear the error once more than 8 characters are typed.
        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(null); //Clear the error
                    // TODO Navigate to the next Fragment
                    // ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false);
                }
                return false;
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
}
