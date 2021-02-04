package org.gedata.producer.configuration.generator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "address")
public class AddressDataInit {

    private  Map<String,CountryData> country;

    public Map<String, CountryData> getCountry() {
        return country;
    }

    public void setCountry(Map<String, CountryData> country) {
        this.country = country;
    }
}
