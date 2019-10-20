package com.unimelb.family_artifact_register.UI.Util;

/**
 * activity with String description and needed to be interacted with the fragment
 */
public interface DescriptionListener {
    void setDescription(String description);

    String getDescription();

    void clearDescription();
}
