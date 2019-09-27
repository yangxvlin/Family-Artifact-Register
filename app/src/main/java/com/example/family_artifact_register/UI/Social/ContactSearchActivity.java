package com.example.family_artifact_register.UI.Social;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactSearchViewModel;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactSearchViewModelFactory;
import com.example.family_artifact_register.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

        getSupportActionBar().setTitle("Add new friend");

        viewModel = ViewModelProviders.of(this, new ContactSearchViewModelFactory(getApplication())).get(ContactSearchViewModel.class);

        EditText searchEditText = findViewById(R.id.search_edit_text);

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
                        intent.putExtra("key", query);
//                        if(data == null) {
//                            // no match user in database
//                            intent.putExtra( "key", "");
//                        }
//                        else {
////                            User[] result = data.toArray(new User[data.size()]);
//                            String[] result = new String[data.size()];
//
//                            for(int i = 0; i < data.size(); i++)
//                                result[i] = data.get(i).username;
//
//                            intent.putExtra("query", v.getText().toString());
//                            Log.i(TAG, result.toString());
//                            intent.putExtra( "key", result);
//                        }
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
