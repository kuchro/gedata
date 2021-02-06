package org.gedata.producer.dtomapper.producer;

import org.gedata.producer.model.data.HostTarget;
import org.gedata.producer.model.dto.GenericDataDetailedDto;
import org.gedata.producer.model.dto.ReqGenericDataDto;
import org.gedata.producer.model.dto.HostTargetDto;
import org.gedata.producer.model.producer.GenericData;

public class DtoProducerMapper {

    public static GenericData convertToGenericData(ReqGenericDataDto reqGenericDataDto) {
        return new GenericData(reqGenericDataDto.getDataName(), reqGenericDataDto.getJsonModel(), reqGenericDataDto.getOutputFormat(),
                convertToHostTarget(reqGenericDataDto.getHostTarget()));
    }

    public static HostTarget convertToHostTarget(HostTargetDto hostTargetDto) {
        return new HostTarget(hostTargetDto.getHostAddress(), hostTargetDto.getPortNumber(), hostTargetDto.getUri());
    }

    public static ReqGenericDataDto convertToGenericDataDto(GenericData genericData) {
        return new ReqGenericDataDto(genericData.getId(),genericData.getDataName(), genericData.getOutputFormat(),
                genericData.getJsonModel(), convertToHostTargetDto(genericData.getHostTarget()),
                genericData.getUserData().getUserId());
    }

    public static HostTargetDto convertToHostTargetDto(HostTarget hostTarget) {
        return new HostTargetDto(hostTarget.getHostAddres(), hostTarget.getPortNumber(), hostTarget.getUri());
    }

    public static GenericDataDetailedDto convertToGDDetailedDto(GenericData genericData) {
        return new GenericDataDetailedDto(genericData.getId(), genericData.getDataName(), genericData.getCreatedTime(),
                genericData.getLastModified(),
                genericData.getJsonModel(), genericData.getOutputFormat(),
                convertToHostTargetDto(genericData.getHostTarget()),genericData.getUserData().getUserId());
    }

    public static GenericDataDetailedDto convertToGDLessDetailedDto(GenericData genericData) {

        return new GenericDataDetailedDto(genericData.getId(),genericData.getDataName(),
                genericData.getLastModified(),
                genericData.getJsonModel(), genericData.getOutputFormat(),
                convertToHostTargetDto(genericData.getHostTarget()));
    }
}
