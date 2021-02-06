package org.gedata.producer.model.user;

import org.gedata.producer.model.producer.GenericData;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserData {
    @TableGenerator(name = "userData_gen", initialValue = 10, allocationSize = 100)
    @Id @GeneratedValue(strategy = GenerationType.AUTO,generator = "userData_gen")
    private Long userId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String nickname;
    @OneToMany(mappedBy = "userData")
    private Set<GenericData> genericDataList = new HashSet<>();

    public UserData() {
    }

    public UserData( @NotBlank String firstName, @NotBlank String lastName, @NotBlank String emailAddress,String nickname) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.nickname = nickname;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Set<GenericData> getGenericDataList() {
        return genericDataList;
    }

    public void setGenericDataList(Set<GenericData> genericDataList) {
        this.genericDataList = genericDataList;
    }
}
