package com.example.family_artifact_register.UI.ArtifactManager;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NewArtifactPreviewVideoFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = NewArtifactPreviewVideoFragment.class.getSimpleName();

    public NewArtifactPreviewVideoFragment() {
        // required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_artifact_preview_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VideoView video = view.findViewById(R.id.fragment_new_artifact_preview_video_view);
        List<Uri> medias = ((NewArtifactActivity2)getActivity()).getData();
        if (!medias.isEmpty()) {
            // only one video uri in the list
            video.setVideoURI(medias.get(0));
            video.setMediaController(new MediaController(getContext()));
            video.start();
            video.requestFocus();
            video.setOnCompletionListener(mp -> {
                Log.d(TAG, "Video play finish.");
            });

            video.setOnErrorListener((mp, what, extra) -> {
                Log.d(TAG, "Video play error!!!");
                return false;
            });
        } else {
            Log.e(TAG, "no video found !!!");
        }

        FloatingActionButton confirm = view.findViewById(R.id.fragment_new_artifact_preview_video_floating_button_confirm);
        confirm.setOnClickListener(view1 -> {
            HappenedTimeFragment happenedTime = HappenedTimeFragment.newInstance();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack("next");
            fragmentTransaction.replace(R.id.activity_new_artifact_main_view, happenedTime);
            fragmentTransaction.commit();
        });
    }

    public static NewArtifactPreviewVideoFragment newInstance() { return new NewArtifactPreviewVideoFragment(); }
}
