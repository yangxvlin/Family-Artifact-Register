package com.example.family_artifact_register.FoundationLayer.ArtifactModel;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-14 20:09:27
 * @description abstract artifact data type
 */
public abstract class Artifact {
    private String createdDate;

    public Artifact() {

    }

    public Artifact(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDate() { return createdDate; }

    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
}
