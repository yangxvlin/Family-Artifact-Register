package com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener;

/**
 * activity and its fragment communicate about the time of the artifact happened
 */
public interface HappenedTimeListener {
    /**
     * @return artifact item happened time
     */
    String getHappenedTime();

    /**
     * @param time artifact item happened time
     */
    void setHappenedTime(String time);
}
