package com.example.family_artifact_register.FoundationLayer.Artifact;

import java.util.ArrayList;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-14 20:09:27
 * @description artifact data type for timelined artifact i.e.: multiple artifact indexed by time
 */
public class TimelinedArtifact extends AbstractArtifact {

    private ArrayList<Artifact> artifacts;

    public TimelinedArtifact() {
        super();

        artifacts = new ArrayList<>();
    }

    public void addArtifact(Artifact artifact) {
        artifacts.add(artifact);
    }
}
