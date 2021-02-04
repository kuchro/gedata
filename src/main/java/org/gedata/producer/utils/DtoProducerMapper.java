package org.gedata.producer.utils;

import org.gedata.producer.model.data.HostTarget;
import org.gedata.producer.model.dto.GenericDataDetailedDto;
import org.gedata.producer.model.dto.GenericDataDto;
import org.gedata.producer.model.dto.HostTargetDto;
import org.gedata.producer.model.producer.GenericData;

public class DtoProducerMapper {

    public static GenericData convertToGenericData(GenericDataDto genericDataDto){
        return new GenericData(genericDataDto.getDataName(),genericDataDto.getJsonModel(), genericDataDto.getOutputFormat(),
                convertToHostTarget(genericDataDto.getHostTarget()));
    }
    public static HostTarget convertToHostTarget(HostTargetDto hostTargetDto){
        return new HostTarget(hostTargetDto.getHostAddress(),hostTargetDto.getPortNumber(),hostTargetDto.getUri());
    }

    public static GenericDataDto convertToGenericDataDto(GenericData genericData){
        return new GenericDataDto(genericData.getDataName(),genericData.getOutputFormat(),
                genericData.getJsonModel(),convertToHostTargetDto(genericData.getHostTarget()));
    }

    public static HostTargetDto convertToHostTargetDto(HostTarget hostTarget){
        return new HostTargetDto(hostTarget.getHostAddres(),hostTarget.getPortNumber(),hostTarget.getUri());
    }

    public static GenericDataDetailedDto convertToGDDetailedDto(GenericData genericData){
        return new GenericDataDetailedDto(genericData.getId(),genericData.getDataName(),genericData.getCreatedTime(),
                genericData.getLastModified(),
                genericData.getJsonModel(),convertToHostTargetDto(genericData.getHostTarget()));
    }
}
