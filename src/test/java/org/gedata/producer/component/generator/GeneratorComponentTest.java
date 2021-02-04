package org.gedata.producer.component.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gedata.producer.generator.DataProducer;
import org.gedata.producer.generator.InputValueInterpreter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GeneratorComponentTest {

    @Autowired
    private  InputValueInterpreter inputValueInterpreter;
    @Autowired
    private  ObjectMapper objectMapper;
    @Autowired
    private DataProducer dataProducer;

    @Test
    public void GenerateSimpleJsonObject() throws JsonProcessingException {
        var jsonInput = "{\"firstName\":\"#{maleNameGen()}\",\"lastName\":\"#{lastNameGen}\"}";
        var json = dataProducer.produceGenericData(jsonInput);
        System.out.println(json);
    }
}
