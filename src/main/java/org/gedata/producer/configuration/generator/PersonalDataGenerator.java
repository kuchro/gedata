package org.gedata.producer.configuration.generator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "name.generator")
public class PersonalDataGenerator {
    private List<String> female;
    private List<String> male;
    private List<String> lastname;
    private List<String> nickName;
    private List<String> phone;

    public List<String> getFemale() {
        return female;
    }

    public void setFemale(List<String> female) {
        this.female = female;
    }

    public List<String> getMale() {
        return male;
    }

    public void setMale(List<String> male) {
        this.male = male;
    }

    public List<String> getLastname() {
        return lastname;
    }

    public void setLastname(List<String> lastname) {
        this.lastname = lastname;
    }

    public List<String> getNickName() {
        return nickName;
    }

    public void setNickName(List<String> nickName) {
        this.nickName = nickName;
    }

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }
}
