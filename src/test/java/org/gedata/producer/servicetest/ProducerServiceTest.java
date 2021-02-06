package org.gedata.producer.servicetest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.gedata.producer.generator.SQLInsertProducer;
import org.gedata.producer.model.data.HostTarget;
import org.gedata.producer.model.producer.GenericData;
import org.gedata.producer.repository.GenericDataRepository;
import org.gedata.producer.service.ProducerService;
import org.gedata.producer.utils.JsonModelValidator;
import org.gedata.producer.utils.SQLDataModelExceptionFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
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
        var data = new GenericData("data_name",jsonInput,"json",
                new HostTarget("http://localhost",8081,"/service"));
        producerService.saveData(data);
        when(producerService.getAllGenericData()).thenReturn(List.of(data));
        List<GenericData> all = producerService.getAllGenericData();
        assertTrue(all.contains(data));
    }

    @Test
    public void TestSaveGenericDataWithInvalidJson() throws JsonProcessingException {
        var data = new GenericData("data_name",invalidJsonInput,"json",
                new HostTarget("http://localhost",8081,"/service"));
        when(producerService.saveData(data)).thenThrow(new SQLDataModelExceptionFormat("InvalidData"));
        assertThrows(SQLDataModelExceptionFormat.class, ()-> {
            producerService.saveData(data);
        });
    }
}
