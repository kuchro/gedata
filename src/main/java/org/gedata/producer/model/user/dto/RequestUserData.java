package org.gedata.producer.model.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestUserData {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String nickname;
}
