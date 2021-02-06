package org.gedata.producer.model.user.dto;

import lombok.*;
import org.gedata.producer.model.producer.GenericData;

import java.util.Set;

@Data
@Builder

public class UserDataDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String nickname;
    private Set<GenericData> genericDataList;
}
