package com.example.family_artifact_register.FoundationLayer.Util;

public class DBConstant {
    // FireStore
    public final static String USER_INFO = "user_info";
    public final static String MAP_LOCATION = "map_location";
    public final static String ARTIFACT = "artifact";
    public final static String ARTIFACT_ITEM = ARTIFACT+"_item";
    public final static String ARTIFACT_TIMELINE = ARTIFACT+"_timeline";
    public final static String ARTIFACT_COMMENT = ARTIFACT+"_comment";

    // Firebase Storage
    public final static String USER_INFO_PHOTO_URL = USER_INFO+"/photo_url";
    public final static String MAP_LOCATION_PHOTO_URL = MAP_LOCATION+"/photo_url";
    public final static String ARTIFACT_ITEM_MEDIA = ARTIFACT_ITEM+"/media";
}
