package com.example.family_artifact_register.FoundationLayer.EventModel;

import com.example.family_artifact_register.R;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    public static final String TAG = EventManager.class.getSimpleName();

    private static final EventManager ourInstance = new EventManager();

    public static EventManager getInstance() {
        return ourInstance;
    }

    private EventManager() {
    }

    public List<Event> getEventByUid(String uid) {
        List<Event> events = new ArrayList<>();

        // TODO factor to source string
        events.add(Event.newInstance("0", "尼科尔森路 11号, 卡尔顿 VIC 3053", R.drawable.melbourne_museum, null, "墨尔本博物馆", "一家创始于 1854 年的公共机构所在的现代化建筑，展品包括恐龙和地区历史文物。"));
        events.add(Event.newInstance("1", "伯克利街 114号, 卡尔顿 VIC 3053", R.drawable.seven_seeds, null, "Seven Seeds 咖啡店", "通风良好、工业风格的雅致咖啡馆，也是一个微型咖啡烘焙店，全天供应早午餐、沙拉和特制茶品。"));
        events.add(Event.newInstance("2", "玻利维亚西南部，乌尤尼小镇附近", R.drawable.uyuni, null, "乌尤尼盐沼", "这是世界上最大的盐沼。在雨后，湖面会变得像镜子一样，反射着好似不是地球上的、美丽的令人窒息的天空景色，许多人称它为‘天空之境’。"));

        return events;
    }
}
