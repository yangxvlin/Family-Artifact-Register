package com.unimelb.family_artifact_register;

public interface IFragment {

    /**
     * Get the default Fragment Tag of a single class, no need to override this method.
     *
     * @return The simple class name for logging and finding by TAG.
     */
    default String getFragmentTag() {
        return this.getClass().getSimpleName();
    }
}
