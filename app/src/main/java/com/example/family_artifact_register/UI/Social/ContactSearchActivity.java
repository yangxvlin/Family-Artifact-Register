package com.example.family_artifact_register.UI.Social;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactDetailViewModel;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactDetailViewModelFactory;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactSearchViewModel;
import com.example.family_artifact_register.PresentationLayer.SocialPresenter.ContactSearchViewModelFactory;
import com.example.family_artifact_register.R;

import java.util.ArrayList;
import java.util.List;

public class ContactSearchActivity extends AppCompatActivity {

    private ContactSearchViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_search2);

        getSupportActionBar().setTitle("Add new friend");

        viewModel = ViewModelProviders.of(this, new ContactSearchViewModelFactory(getApplication())).get(ContactSearchViewModel.class);

        EditText searchEditText = findViewById(R.id.search_edit_text);
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
                        String query = v.getText().toString();
                        User result = viewModel.getFriend(query).getValue();
                        Intent i = new Intent(v.getContext(), ContactSearchResultActivity.class);

                        i.putExtra("query", v.getText().toString());
                        if(result != null)
                            i.putExtra( "key", result.username);
                        else
                            i.putExtra( "key", "");

                        startActivity(i);
                        // consume the action
                        return true;
                    }
                }
                // pass on to other listeners
                return false;
            }
        });
        // TODO: implementation for search logic
    }

    // to be moved to a class in the logic layer
    private ArrayList<String> search(String target, String[] users) {
        ArrayList<String> out = new ArrayList<>();
        for(String i : users) {
            if(i.equals(target))
                out.add(i);
        }
        return out;
    }
}
