package org.gedata.producer.generator;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.gedata.producer.configuration.generator.AddressDataConfiguration;
import org.gedata.producer.configuration.generator.PersonalDataConfiguration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@Component
public class DataPicker{

    private final PersonalDataConfiguration personalDataConfiguration;
    private final AddressDataConfiguration addressDataConfiguration;

    public List<String> personalGen(String genValue){
         return personalDataConfiguration.getPersonaldata().get(genValue);
    }

    public List<String> listOfStreets(String region){
        return addressDataConfiguration.getData().get(region).getStreet();
    }
    public List<String> listOfCiies(String region){
        return addressDataConfiguration.getData().get(region).getCity();
    }
    public List<String> perdonalData(){
        return new ArrayList<>(personalDataConfiguration.getPersonaldata().keySet());
    }
}
