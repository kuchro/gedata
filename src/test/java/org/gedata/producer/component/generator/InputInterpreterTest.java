package org.gedata.producer.component.generator;

import org.gedata.producer.generator.InputValueInterpreter;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.spel.SpelEvaluationException;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class InputInterpreterTest {

    @Autowired
    private InputValueInterpreter interpreter;

    @Test
    public void validateIntepreterForPersonalDataGen(){
        assertThat(interpreter.eval("${personalDataGen('male')}"),not(isEmptyString()));
        assertThat(interpreter.eval("${personalDataGen('female')}"),not(isEmptyString()));
        assertThat(interpreter.eval("${personalDataGen('lastname')}"),not(isEmptyString()));
        assertThat(interpreter.eval("${personalDataGen('nickname')}"),not(isEmptyString()));
    }

    @Test
    public void validateIntepreterForSynteticGen(){
        assertThat(interpreter.eval("${passwordGen(15)}"),not(isEmptyString()));
        assertThat(interpreter.eval("${numberGen()}"),not(isEmptyString()));
        assertThat(interpreter.eval("${zipCodeGen('GBP')}"),not(isEmptyString()));
        assertThat(interpreter.eval("${zipCodeGen('DE')}"),not(isEmptyString()));
    }

    @Test
    public void validateIntepreterForAddressData(){
        assertThat(interpreter.eval("${streetNameGen()}"),not(isEmptyString()));
        assertThat(interpreter.eval("${cityNameGen()}"),not(isEmptyString()));
    }

    @Test
    public void validateIntepreteThrowsErrorWhenGenIsInvalid(){
        assertThrows(SpelEvaluationException.class,()->{
            assertThat(interpreter.eval("${fakeGen()}"),not(isEmptyString()));
        });
    }

}