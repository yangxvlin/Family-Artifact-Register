package com.example.family_artifact_register.FoundationLayer.Util;

public class DBConstant {
    // FireStore
    public final static String USER_INFO = "user_info";
    public final static String MAP_LOCATION = "map_location";
    public final static String ARTIFACT = "artifact";
    public final static String ARTIFACT_ITEM = ARTIFACT+"/artifact_item";
    public final static String ARTIFACT_TIMELINE = ARTIFACT+"/artifact_timeline";

    // Firebase Storage
    public final static String USER_INFO_PHOTO_URL = USER_INFO+"/photo_url";
    public final static String MAP_LOCATION_PHOTO_URL = MAP_LOCATION+"/photo_url";
    public final static String ARTIFACT_ITEM_MEDIA = ARTIFACT_ITEM+"/media";
}
