package com.example.family_artifact_register.FoundationLayer.Util;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO this class could be made more generic
 * Helper Class designed to help dispatch live data which requires multiple async update
 * For instance the search in UserInfoManager
 */
public class LiveDataListDispatchHelper<T> {
    private static final String TAG = LiveDataListDispatchHelper.class.getSimpleName();

    // Number of remaining task
    private int total;

    // Mutable live data it should set and dispatch
    private MutableLiveData<List<T>> mutableLiveData;

    // search result it should store
    private List<T> searchResults;

    public LiveDataListDispatchHelper(MutableLiveData<List<T>> mutableLiveData) {
        searchResults = new ArrayList<>();
        this.mutableLiveData = mutableLiveData;
    }

    /**
     * If the task queue is empty?
     * @return true if empty task queue
     */
    public boolean shouldDispatch() {
        return total == 0;
    }

    /**
     * dispatch the result regardless of queue status
     */
    private void dispatch() {
        mutableLiveData.setValue(searchResults);
    }

    /**
     * Add the counter by one
     */
    public void addWaitingTask() {
        total += 1;
    }

    public void completeWaitingTask() {
        total -= 1;
        if (total < 0) {
            Log.e(TAG, "total counter " + total + " is less than 0", new Throwable());
        }
    }

    public void completeWaitingTaskAndDispatch() {
        total -= 1;
    }

    /**
     * add new result to the result list
     * @param newResult the new result to add
     */
    public void addResult(T newResult) {
        searchResults.add(newResult);
    }

    /**
     * add new result to the results list
     * @param newResults the new list of results to add
     */
    public void addResult(List<T> newResults) {
        for (T newResult : newResults) {
            addResult(newResult);
        }
    }

    /**
     * add new result to the result list and decrease the counter, will dispatch result if counter hits zero
     * @param newResult the new result to add
     */
    public void addResultAfterTaskCompletion(T newResult) {
        addResult(newResult);
        total -= 1;

        if (shouldDispatch()) {
            dispatch();
        }
    }

    /**
     * add new results to the result list and decrease the counter, will dispatch result if counter hits zero
     * @param newResults the new list of results to add
     */
    public void addResultAfterTaskCompletion(List<T> newResults) {
        for (T newResult : newResults) {
            addResult(newResult);
        }
        total -= 1;

        if (shouldDispatch()) {
            dispatch();
        }
    }
}
