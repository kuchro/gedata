package org.gedata.producer.model.dto;

import java.time.Instant;

public class GenericDataDto {

    private String dataName;
    private String jsonModel;
    private HostTargetDto hostTarget;

    public GenericDataDto(String dataName, String jsonModel, HostTargetDto hostTarget) {
        this.dataName = dataName;
        this.jsonModel = jsonModel;
        this.hostTarget = hostTarget;
    }

    public GenericDataDto() {
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
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

    public void setHostTarge(HostTargetDto hostTargetDto) {
        this.hostTarget = hostTargetDto;
    }
}
