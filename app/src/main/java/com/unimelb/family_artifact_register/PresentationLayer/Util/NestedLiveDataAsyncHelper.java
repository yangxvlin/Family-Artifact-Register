package com.unimelb.family_artifact_register.PresentationLayer.Util;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Transformation T -> W -> V
 *
 * @param <T> The initial type
 * @param <W> The intermediate type
 * @param <V> The final type
 */
public class NestedLiveDataAsyncHelper<T, W, V> {

    private MediatorLiveData<List<V>> result = new MediatorLiveData<>();
    private InfoGetter infoGetter;

    public NestedLiveDataAsyncHelper(InfoGetter infoGetter) {
        this.infoGetter = infoGetter;
    }

    /**
     * The main method implemented in this class
     *
     * @param t The initial type
     * @return A list of data wrapped in a LiveData object
     */
    public MediatorLiveData<List<V>> get(LiveData<T> t) {

        t.observeForever(new Observer<T>() {
            @Override
            public void onChanged(T t) {
                result.setValue(new ArrayList<>());
                ArrayList<W> infoList = new ArrayList<>(infoGetter.getInfoList(t));

                List<LiveData<V>> resultList = infoGetter.getListLiveDataInfo(infoList);

                for (LiveData<V> j : resultList) {
                    result.addSource(j, new Observer<V>() {
                        @Override
                        public void onChanged(V v) {
                            result.getValue().add(v);
                            result.setValue(result.getValue());
                        }
                    });
                }
            }
        });
        // t.removeObserver();
        return result;
    }

    /**
     * Interface required to instantiate an instance of NestedLiveDataAsyncHelper
     *
     * @param <T> The initial type
     * @param <W> The intermediate type
     * @param <V> The final type
     */
    public interface InfoGetter<T, W, V> {
        /**
         * This method retrieves a list of items
         *
         * @param t Input parameter used to retrieve desire data
         * @return A list of items
         */
        List<W> getInfoList(T t);

        /**
         * This method retrieves an item wrapped in LiveData object
         *
         * @param w Input parameter used to retrieve desire data
         * @return A list of items held in LiveData object
         */
        LiveData<V> getListInfoLiveData(W w);

        /**
         * This method retrieves a list of items wrapped in LiveData object
         *
         * @param w Input parameter used to retrieve desire data
         * @return A list of items held in LiveData object
         */
        List<LiveData<V>> getListLiveDataInfo(List<W> w);
    }
}
