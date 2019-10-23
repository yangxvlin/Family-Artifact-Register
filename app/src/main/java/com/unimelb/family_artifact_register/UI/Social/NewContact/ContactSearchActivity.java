package com.unimelb.family_artifact_register.UI.Social.NewContact;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter.ContactSearchViewModel;
import com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter.ContactSearchViewModelFactory;
import com.unimelb.family_artifact_register.R;

public class ContactSearchActivity extends AppCompatActivity {

    /**
     * class tag
     */
    public static final String TAG = ContactSearchActivity.class.getSimpleName();

    private ContactSearchViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_search2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.ContactSearchActivity_title);
        actionBar.setBackgroundDrawable(this.getDrawable(R.drawable.gradient_background));

        viewModel = ViewModelProviders.of(this, new ContactSearchViewModelFactory(getApplication())).get(ContactSearchViewModel.class);

        EditText searchEditText = findViewById(R.id.search_edit_text);
        if(searchEditText.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        String storedQuery = viewModel.getQuery();
        if(storedQuery != null)
            searchEditText.setText(storedQuery);

        // listen to see if the user has finished typing
        // https://stackoverflow.com/a/8063533
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event != null &&
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (event == null || !event.isShiftPressed()) {
                        // user is done typing
                        Intent intent = new Intent(v.getContext(), ContactSearchResultActivity.class);
                        String query = v.getText().toString();
                        viewModel.setQuery(query);
                        Log.i(TAG, query);
                        intent.putExtra("query", query);
                        startActivity(intent);
                        // consume the action
                        return true;
                    }
                }
                // pass on to other listeners
                return false;
            }
        });
    }
}
