package com.example.family_artifact_register.FoundationLayer.Util;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class DefaultListeners {
    private static DefaultListeners ourInstance = new DefaultListeners();

    public static DefaultListeners getInstance() {
        return ourInstance;
    }

    private DefaultListeners() {
    }

    private class DefaultOnSuccessListener<T> implements OnSuccessListener<T> {
        private String tag;
        private DefaultOnSuccessListener(String tag) {
            this.tag = tag;
        }

        @Override
        public void onSuccess(T t) {
            String object = null;
            if (t != null) {
                object = t.toString();
            }
            Log.d(tag, "Task successful, task result: " + object);
        }
    }

    private class DefaultOnFailureListener implements OnFailureListener {
        private String tag;
        private DefaultOnFailureListener(String tag) {
            this.tag = tag;
        }

        @Override
        public void onFailure(@NonNull Exception e) {
            Log.d(tag, "Task failed, task exception: " + e.toString());
        }
    }

    private class DefaultOnCanceledListener implements OnCanceledListener {

        private String tag;
        private DefaultOnCanceledListener(String tag) {
            this.tag = tag;
        }

        @Override
        public void onCanceled() {
            Log.d(tag, "Task cancelled");
        }
    }

    public <T> DefaultOnSuccessListener<T> getOnSuccessListener(String TAG) {
        return new DefaultOnSuccessListener<>(TAG);
    }


    public DefaultOnFailureListener getOnFailureListener(String TAG) {
        return new DefaultOnFailureListener(TAG);
    }

    public DefaultOnCanceledListener getOnCanceledListener(String TAG) {
        return new DefaultOnCanceledListener(TAG);
    }
}
