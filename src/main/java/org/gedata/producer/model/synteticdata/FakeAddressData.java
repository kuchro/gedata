package org.gedata.producer.model.synteticdata;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FakeAddressData {
    @Id
    private Long id;
    private String street;
    private String city;
    private String zipCode;
    private String region;
    private String country;

    public FakeAddressData(Long id, String street, String city, String zipCode, String region, String country) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.region = region;
        this.country = country;
    }

    public FakeAddressData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountryl() {
        return country;
    }

    public void setCountryl(String countryl) {
        this.country = countryl;
    }
}
