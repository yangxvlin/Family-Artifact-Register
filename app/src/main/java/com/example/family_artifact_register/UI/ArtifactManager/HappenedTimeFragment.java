package com.example.family_artifact_register.UI.ArtifactManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HappenedTimeFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = HappenedTimeFragment.class.getSimpleName();

    public HappenedTimeFragment() {
        // required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_artifact_happened_time, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.artifact_happened_title);

        // get user chosen date
        DatePicker datePicker = view.findViewById(R.id.fragment_new_artifact_happened_time_date_picker);
        datePicker.setOnDateChangedListener((datePicker1, year, monthOfYear, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Toast.makeText(getContext(), format.format(calendar.getTime()), Toast.LENGTH_SHORT)
                    .show();
        });

        // to next fragment
        FloatingActionButton confirm = view.findViewById(R.id.fragment_new_artifact_happened_time_floating_button_confirm);
        confirm.setOnClickListener(view1 -> {

        });
    }

    public static HappenedTimeFragment newInstance() { return new HappenedTimeFragment(); }
}
