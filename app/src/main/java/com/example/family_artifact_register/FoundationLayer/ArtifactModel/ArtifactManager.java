package com.example.family_artifact_register.FoundationLayer.ArtifactModel;

/**
 * Singleton manager for managing firebase related access with artifacts.
 */
public class ArtifactManager {
    private static final ArtifactManager ourInstance = new ArtifactManager();

    public static ArtifactManager getInstance() {
        return ourInstance;
    }

    private ArtifactManager() {
    }


}
