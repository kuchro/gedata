package org.gedata.producer.model.dto;

import org.gedata.producer.model.data.HostTarget;


import java.time.Instant;

public class GenericDataDetailedDto {

    private Long id;
    private String dataName;
    private Instant createdTime;
    private Instant lastModified;
    private String jsonModel;
    private HostTargetDto hostTarget;

    public GenericDataDetailedDto(Long id, String dataName, Instant createdTime,
                                  Instant lastModified, String jsonModel, HostTargetDto hostTarget) {
        this.id = id;
        this.dataName = dataName;
        this.createdTime = createdTime;
        this.lastModified = lastModified;
        this.jsonModel = jsonModel;
        this.hostTarget = hostTarget;
    }

    public GenericDataDetailedDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getJsonModel() {
        return jsonModel;
    }

    public void setJsonModel(String jsonModel) {
        this.jsonModel = jsonModel;
    }

    public HostTargetDto getHostTarget() {
        return hostTarget;
    }

    public void setHostTarget(HostTargetDto hostTarget) {
        this.hostTarget = hostTarget;
    }
}
