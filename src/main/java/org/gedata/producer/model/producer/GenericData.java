package org.gedata.producer.model.producer;

import org.gedata.producer.model.data.HostTarget;
import org.gedata.producer.model.user.UserData;

import javax.persistence.*;

import java.time.Instant;


@Entity(name = "generic_data")
public class GenericData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String dataName;
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserData userData;
    private Instant createdTime;
    private Instant lastModified;
    @Column(length = 1000000)
    private String jsonModel;
    private String outputFormat;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private HostTarget hostTarget;

    public GenericData(String dataName, String jsonModel, String outputFormat,HostTarget hostTarget) {
        this.dataName = dataName;
        this.createdTime = Instant.now();
        this.lastModified = Instant.now();
        this.jsonModel = jsonModel;
        this.outputFormat=outputFormat;
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

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public HostTarget getHostTarget() {
        return hostTarget;
    }

    public void setHostTarget(HostTarget hostTarget) {
        this.hostTarget = hostTarget;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
