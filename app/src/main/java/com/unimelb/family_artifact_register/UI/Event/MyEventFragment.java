package com.unimelb.family_artifact_register.UI.Event;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.FoundationLayer.EventModel.EventListener;
import com.unimelb.family_artifact_register.IFragment;
import com.unimelb.family_artifact_register.PresentationLayer.EventPreseneter.EventViewModel;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.Util.EventAdapter;

public class MyEventFragment extends Fragment implements IFragment, EventListener {
    /**
     * class tag
     */
    public static final String TAG = MyEventFragment.class.getSimpleName();

    private RecyclerView recyclerView;

    private EventAdapter eventAdapter;

    public MyEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "my event fragment created");
        return inflater.inflate(R.layout.fragment_recommended_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.fragment_recommended_events_recycler_view);
        eventAdapter = new EventAdapter(EventViewModel.getInstance().getAttendedEvent(),
                false,
                this,
                getContext()
        );
        EventViewModel.getInstance().addObserver(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(eventAdapter);
    }

    /**
     * @return created me fragment
     */
    public static MyEventFragment newInstance() { return new MyEventFragment(); }

    @Override
    public void notifyEventsChange() {
        eventAdapter.setEventList(EventViewModel.getInstance().getAttendedEvent());
    }

    @Override
    public void attendEvent(String eventId) {
        //
    }

    @Override
    public void cancelEvent(String eventId) {
        EventViewModel.getInstance().cancelAttendEvent(eventId);
    }
}
