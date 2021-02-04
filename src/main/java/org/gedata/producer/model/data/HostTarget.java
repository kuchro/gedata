package org.gedata.producer.model.data;

import org.gedata.producer.model.producer.GenericData;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class HostTarget {
    @Id @GeneratedValue
    private Long id;
    private String hostAddres;
    private Integer portNumber;
    private String uri;
    @OneToOne(mappedBy = "hostTarget")
    private GenericData genericData;

    public HostTarget(String hostAddres, Integer portNumber, String uri) {
        this.hostAddres = hostAddres;
        this.portNumber = portNumber;
        this.uri = uri;
    }

    public HostTarget() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostAddres() {
        return hostAddres;
    }

    public void setHostAddres(String hostAddres) {
        this.hostAddres = hostAddres;
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

    public GenericData getGenericData() {
        return genericData;
    }

    public void setGenericData(GenericData genericData) {
        this.genericData = genericData;
    }
}
