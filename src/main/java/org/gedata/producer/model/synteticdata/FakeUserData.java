package org.gedata.producer.model.synteticdata;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FakeUserData {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String nickname;
    private int age;
    private String region;

    public FakeUserData(Long id, String firstName, String lastName, String gender, String nickname, int age, String region) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nickname = nickname;
        this.age = age;
        this.region = region;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public FakeUserData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
