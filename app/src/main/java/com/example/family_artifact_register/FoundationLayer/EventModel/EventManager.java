package com.example.family_artifact_register.FoundationLayer.EventModel;

import android.util.Log;

import com.example.family_artifact_register.R;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    public static final String TAG = EventManager.class.getSimpleName();

    private static final EventManager ourInstance = new EventManager();

    public static final int CHINESE = 0;

    public static final int ENGLISH = 1;

    public static EventManager getInstance() {
        return ourInstance;
    }

    private EventManager() {
    }

    public List<Event> getEventByUid(String uid, int language) {
        List<Event> events = new ArrayList<>();

        if (language == CHINESE) {
            events.add(Event.newInstance("尼科尔森路 11号, 卡尔顿 VIC 3053", R.drawable.melbourne_museum, null, "墨尔本博物馆", "一家创始于 1854 年的公共机构所在的现代化建筑，展品包括恐龙和地区历史文物。"));
            events.add(Event.newInstance("伯克利街 114号, 卡尔顿 VIC 3053", R.drawable.seven_seeds, null, "Seven Seeds 咖啡店", "通风良好、工业风格的雅致咖啡馆，也是一个微型咖啡烘焙店，全天供应早午餐、沙拉和特制茶品。"));

        } else if (language == ENGLISH) {
            // events.add(Event.newInstance("Melbourne Museum", ));

        } else {
            Log.e(TAG, "Unsupported language !!!");
        }


        return events;
    }
}
