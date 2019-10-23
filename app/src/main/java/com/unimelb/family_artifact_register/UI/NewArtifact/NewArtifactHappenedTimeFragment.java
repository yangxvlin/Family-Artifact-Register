package com.unimelb.family_artifact_register.UI.NewArtifact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.unimelb.family_artifact_register.Util.IFragment;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.NewArtifact.Util.HappenedTimeListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class NewArtifactHappenedTimeFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = NewArtifactHappenedTimeFragment.class.getSimpleName();

    String happenedTime;

    public NewArtifactHappenedTimeFragment() {
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
        getActivity().setTitle(R.string.artifact_when_happened_title);

        // get user chosen date
        DatePicker datePicker = view.findViewById(R.id.fragment_new_artifact_happened_time_date_picker);
        datePicker.setOnDateChangedListener((datePicker1, year, monthOfYear, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String time = format.format(calendar.getTime());
            this.happenedTime = time;
        });

        // to next fragment
        FloatingActionButton confirm = view.findViewById(R.id.fragment_new_artifact_happened_time_floating_button_confirm);
        confirm.setOnClickListener(view1 -> {
            if (happenedTime == null) {
                Toasty.error(getContext(), R.string.happened_time_not_chosen_warning).show();
                return;
            }
            ((HappenedTimeListener)getActivity()).setHappenedTime(happenedTime);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack("next");
            fragmentTransaction.replace(R.id.activity_new_artifact_main_view, NewArtifactHappenedLocationFragment.newInstance());
            fragmentTransaction.commit();
        });
    }

    public static NewArtifactHappenedTimeFragment newInstance() { return new NewArtifactHappenedTimeFragment(); }
}
