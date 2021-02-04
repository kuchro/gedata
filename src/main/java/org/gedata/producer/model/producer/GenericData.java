package org.gedata.producer.model.producer;

import org.gedata.producer.model.data.HostTarget;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "generic_data")
public class GenericData {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String dataName;
    private Instant createdTime;
    private Instant lastModified;
    @Column( length = 1000000 )
    private String jsonModel;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private HostTarget hostTarget;

    public GenericData(String dataName, String jsonModel, HostTarget hostTarget) {
        this.dataName = dataName;
        this.createdTime = Instant.now();
        this.lastModified = Instant.now();
        this.jsonModel = jsonModel;
        this.hostTarget = hostTarget;
    }

    public GenericData() {
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
        return Instant.now();
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

    public HostTarget getHostTarget() {
        return hostTarget;
    }

    public void setHostTarget(HostTarget hostTarget) {
        this.hostTarget = hostTarget;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GenericData{");
        sb.append("id=").append(id);
        sb.append(", dataName='").append(dataName).append('\'');
        sb.append(", jsonModel='").append(jsonModel).append('\'');
        sb.append(", hostTarget=").append(hostTarget);
        sb.append('}');
        return sb.toString();
    }
}
