package com.unimelb.family_artifact_register.Util;

/**
 * @param <T> class to be callback
 */
public interface Callback<T> {
    /**
     * @param requestCode callback request code
     * @param resultCode callback result code
     * @param data data object
     */
    void callback(int requestCode, int resultCode, T data);
}
