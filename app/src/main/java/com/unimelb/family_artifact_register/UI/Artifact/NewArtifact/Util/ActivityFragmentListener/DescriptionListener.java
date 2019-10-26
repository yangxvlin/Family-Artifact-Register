package com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener;

/**
 * activity with String description and needed to be interacted with the fragment
 */
public interface DescriptionListener {
    /**
     * @return description of artifact item
     */
    String getDescription();

    /**
     * @param description description of artifact item
     */
    void setDescription(String description);

    /**
     * clear description
     */
    void clearDescription();
}
