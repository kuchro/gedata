package org.gedata.producer.model.dto;

public class HostTargetDto {
    private String hostAddress;
    private Integer portNumber;
    private String uri;

    public HostTargetDto(String hostAddress, Integer portNumber, String uri) {
        this.hostAddress = hostAddress;
        this.portNumber = portNumber;
        this.uri = uri;
    }

    public HostTargetDto() {
    }

    public String getHostAddres() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public Integer getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(Integer portNumber) {
        this.portNumber = portNumber;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
