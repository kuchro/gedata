package org.gedata.producer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostTargetDto {
    private String hostAddress;
    private Integer portNumber;
    private String uri;
}
