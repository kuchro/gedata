package org.gedata.producer.generator;

import org.gedata.producer.configuration.generator.AddressDataInit;
import org.gedata.producer.configuration.generator.PersonalDataGenerator;
import org.gedata.producer.repository.syntetic.SynteticAddressData;
import org.gedata.producer.repository.syntetic.SynteticUserData;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataPicker{

    private final PersonalDataGenerator personalDataGenerator;
    private final AddressDataInit addressDataInit;

    public DataPicker(PersonalDataGenerator personalDataGenerator, AddressDataInit addressDataInit) {
        this.personalDataGenerator = personalDataGenerator;
        this.addressDataInit = addressDataInit;

    }


    public List<String> listOfFemaleNames(){
            return personalDataGenerator.getFemale();
    }

    public List<String> listOfMaleNames(){
        return personalDataGenerator.getMale();
    }

    public List<String> listOfLastNames(){
        return personalDataGenerator.getLastname();
    }

    public List<String> listOfNickName(){
        return personalDataGenerator.getNickName();
    }

    public List<String> listOfPhones(){
        return personalDataGenerator.getPhone();
    }

    public List<String> listOfStreets(String region){
        return addressDataInit.getCountry().get(region).getStreet();
    }
    public List<String> listOfCiies(String region){
        return addressDataInit.getCountry().get(region).getCity();
    }

}
