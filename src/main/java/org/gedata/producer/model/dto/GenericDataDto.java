package org.gedata.producer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericDataDto {

    private String dataName;
    private String outputFormat;
    private String jsonModel;
    private HostTargetDto hostTarget;
}
