package com.example.family_artifact_register.Util;

public interface Callback<T> {
    void callback(int requestCode, int resultCode, T data);
}
