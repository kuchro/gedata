package org.gedata.producer.model.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericDataDetailedDto {

    private Long id;
    private String dataName;
    private Instant createdTime;
    private Instant lastModified;
    private String jsonModel;
    private String outputFormat;
    private HostTargetDto hostTarget;
    private Long userId;

    public GenericDataDetailedDto(Long id,String dataName, Instant lastModified, String jsonModel,
                                  String outputFormat, HostTargetDto convertToHostTargetDto) {
        this.id = id;
        this.dataName=dataName;
        this.lastModified= lastModified;
        this.jsonModel = jsonModel;
        this.outputFormat = outputFormat;
        this.hostTarget = convertToHostTargetDto;
    }
}
