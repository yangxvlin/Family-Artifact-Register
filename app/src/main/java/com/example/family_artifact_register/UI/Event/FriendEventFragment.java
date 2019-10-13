package com.example.family_artifact_register.UI.Event;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.R;

public class FriendEventFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = FriendEventFragment.class.getSimpleName();

    public FriendEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "friend event fragment created");
        return inflater.inflate(R.layout.fragment_friend_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * @return created me fragment
     */
    public static FriendEventFragment newInstance() { return new FriendEventFragment(); }
}
