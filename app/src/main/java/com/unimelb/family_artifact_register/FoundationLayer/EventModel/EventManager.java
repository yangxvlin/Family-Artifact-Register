package com.unimelb.family_artifact_register.FoundationLayer.EventModel;

import com.unimelb.family_artifact_register.MyApplication;
import com.unimelb.family_artifact_register.R;

import java.util.ArrayList;
import java.util.List;

/**
 * singleton event manager to pull event data from DB to provide global access
 */
public class EventManager {
    /**
     * class tag
     */
    public static final String TAG = EventManager.class.getSimpleName();

    /**
     * singleton instance
     */
    private static final EventManager ourInstance = new EventManager();

    private EventManager() {
    }

    public static EventManager getInstance() {
        return ourInstance;
    }

    public List<Event> getEventByUid(String uid) {
        List<Event> events = new ArrayList<>();

        // TODO factor to source string
        events.add(Event.newInstance("0", MyApplication.getContext().getString(R.string.melbourne_museum_address),
                R.drawable.melbourne_museum, null, MyApplication.getContext().getString(R.string.melbourne_museum),
                MyApplication.getContext().getString(R.string.melbourne_museum_description)));
        events.add(Event.newInstance("1", MyApplication.getContext().getString(R.string.seven_seeds_address),
                R.drawable.seven_seeds, null, MyApplication.getContext().getString(R.string.seven_seeds_name),
                MyApplication.getContext().getString(R.string.seven_seeds_description)));
        events.add(Event.newInstance("2", MyApplication.getContext().getString(R.string.uyuni_address),
                R.drawable.uyuni, null, MyApplication.getContext().getString(R.string.uyuni_name),
                MyApplication.getContext().getString(R.string.uyuni_description)));
        events.add(Event.newInstance("3", MyApplication.getContext().getString(R.string.oko_oko_address),
                R.drawable.oko_oko, null, MyApplication.getContext().getString(R.string.oko_oko_name),
                MyApplication.getContext().getString(R.string.oko_oko_description)));
        events.add(Event.newInstance("4", MyApplication.getContext().getString(R.string.unimelb_address),
                R.drawable.unimelb, null, MyApplication.getContext().getString(R.string.unimelb_name),
                MyApplication.getContext().getString(R.string.unimelb_description)));
        return events;
    }
}
