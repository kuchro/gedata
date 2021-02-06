package org.gedata.producer.dtomapper.userdata;

import org.gedata.producer.model.user.UserData;
import org.gedata.producer.model.user.dto.RequestUserData;
import org.gedata.producer.model.user.dto.UserDataDto;

public class DtoUserDataMapper {

    public static UserDataDto convertToUserDataDto(UserData userData) {
        return UserDataDto.builder().firstName(userData.getFirstName())
                .lastName(userData.getLastName())
                .emailAddress(userData.getEmailAddress())
                .nickname(userData.getNickname())
                .build();
    }

    public static UserData convertToUserData(RequestUserData userData) {
        return new UserData(userData.getFirstName(), userData.getLastName(), userData.getEmailAddress(), userData.getNickname());
    }

}
