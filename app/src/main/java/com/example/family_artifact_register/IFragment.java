package com.example.family_artifact_register;

public interface IFragment {

    default String getFragmentTag() {
        return this.getClass().getSimpleName();
    }
}
