package org.gedata.producer.component.generator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gedata.producer.component.generator.testmodel.TestPersonal;
import org.gedata.producer.generator.DataProducer;
import org.gedata.producer.generator.InputValueInterpreter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.ExpressionInvocationTargetException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class GeneratorComponentTest {

    @Autowired
    private  ObjectMapper objectMapper;
    @Autowired
    private DataProducer dataProducer;

    @Test
    public void GenerateSimpleJsonObject() throws JsonProcessingException {
        var jsonInput = "{\"firstName\":\"${personalDataGen('male')}\",\"lastName\":\"${personalDataGen('lastname')}\"}";
        var json = dataProducer.produceGenericData(jsonInput);
        TestPersonal testObj = objectMapper.readValue(objectMapper.writeValueAsString(json), TestPersonal.class);
        assertThat(testObj,is(notNullValue()));
        assertThat(testObj.getFirstName(),is(notNullValue()));
        assertThat(testObj.getLastName(),is(notNullValue()));
    }

    @Test
    public void validateIfThereIsNoGenerator(){
        var jsonInput = "{\"firstName\":\"${personalDataGen('random')}\",\"lastName\":\"${personalDataGen('lastname')}\"}";
        assertThrows(ExpressionInvocationTargetException.class,()->{
            dataProducer.produceGenericData(jsonInput);
        });
    }

    @Test
    public void validateProduceGenericDataIfThejsonModelIsMalformed() {
        var jsonInput = "{{\"firstName\":\"${personalDataGen('random')}\",\"lastName\":\"${personalDataGen('lastname')}\"}";
        assertThrows(JsonParseException.class,()->{
            dataProducer.produceGenericData(jsonInput);
        });
    }

}
