package org.gedata.producer.servicetest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.gedata.producer.dtomapper.producer.DtoProducerMapper;
import org.gedata.producer.model.dto.HostTargetDto;
import org.gedata.producer.model.dto.ReqGenericDataDto;
import org.gedata.producer.model.producer.GenericData;
import org.gedata.producer.service.ProducerService;
import org.gedata.producer.utils.SQLDataModelExceptionFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProducerServiceTest {
    private static String jsonInput = "{\"firstName\":\"${personalDataGen('male')}\",\"lastName\":\"${personalDataGen('lastname')}\"}";
    private static String invalidJsonInput = "\"firstName\":\"${personalDataGen('male')}\",\"lastName\":\"${personalDataGen('lastname')}\"}";

    @Mock
    private ProducerService producerService;

    @Test
    public void TestSaveProducerTemplateData() throws JsonProcessingException {
        var data = new ReqGenericDataDto(1L,"data_name",jsonInput,"json",
                new HostTargetDto("http://localhost",8081,"/service"),1L);
        producerService.saveData(data);
        when(producerService.getAllGenericData()).thenReturn(List.of(DtoProducerMapper.convertToGenericData(data)));
        List<GenericData> all = producerService.getAllGenericData();
        assertTrue(all.size()>0);
    }

    @Test
    public void TestSaveGenericDataWithInvalidJson() throws JsonProcessingException {
        var data = new ReqGenericDataDto(1L,"data_name",invalidJsonInput,"json",
                new HostTargetDto("http://localhost",8081,"/service"),1L);
        when(producerService.saveData(data)).thenThrow(new SQLDataModelExceptionFormat("InvalidData"));
        assertThrows(SQLDataModelExceptionFormat.class, ()-> {
            producerService.saveData(data);
        });
    }
}
