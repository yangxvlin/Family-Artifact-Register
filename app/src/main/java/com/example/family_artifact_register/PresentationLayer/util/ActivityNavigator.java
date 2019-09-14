package com.example.family_artifact_register.PresentationLayer.util;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author XuLin Yang 904904,
 * @time 2019-8-10 17:00:59
 * @description class pure fabric for navigation between activities
 */
public class ActivityNavigator {
    /**
     * e.g.: navigateFromTo(FromActivity.this, ToActivity.class);
     * @param from The current activity
     * @param to The next activity to directed to
     */
    public static void navigateFromTo(AppCompatActivity from, Class<?> to) {
        Intent activityChangeIntent = new Intent(from, to);
        from.startActivity(activityChangeIntent);
    }

    /**
     * e.g.: navigateFromTo(FromActivity.this, ToActivity.class);
     * @param from The current activity
     * @param to The next activity to directed to
     */
    public static void  navigateFromToEmpty(AppCompatActivity from, Class<?> to) {
        Intent activityChangeIntent = new Intent(from, to);
        // set navigation stack to empty
        activityChangeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activityChangeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        from.startActivity(activityChangeIntent);
    }
}
