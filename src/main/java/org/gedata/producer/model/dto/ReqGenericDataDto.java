package org.gedata.producer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqGenericDataDto {

    private Long id;
    private String dataName;
    @Valid
    @NotNull(message = "outputFormat is mandatory")
    private String outputFormat;
    @Valid
    @NotNull(message = "jsonModel is mandatory")
    private String jsonModel;
    private HostTargetDto hostTarget;
    @Valid
    @NotNull
    private Long userId;
}
