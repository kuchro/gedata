package org.gedata.producer.configuration.generator;

import java.util.List;

public class CountryData {
    private List<String> street;
    private List<String> city;

    public List<String> getStreet() {
        return street;
    }

    public void setStreet(List<String> street) {
        this.street = street;
    }

    public List<String> getCity() {
        return city;
    }

    public void setCity(List<String> city) {
        this.city = city;
    }
}
