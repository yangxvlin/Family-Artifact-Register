package com.example.family_artifact_register.FoundationLayer.Artifact;

import java.util.Date;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-14 20:09:27
 * @description abstract artifact data type
 */
public abstract class AbstractArtifact {
    private Date createdDate;

    public AbstractArtifact() {

    }

    public Date getCreatedDate() { return createdDate; }

    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    private void newCreateDate() { createdDate = new Date(); }
}
