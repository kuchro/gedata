package org.gedata.producer.component.generator;

import org.gedata.producer.generator.GenericDataProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class GenericDataProviderTest {
    @Autowired
    private GenericDataProvider genericDataProvider;

    @Test
    public void VerifyMalePersonalDataGen() throws NoSuchMethodException {
        String data= genericDataProvider.personalDataGen("male");
        assertThat(data,not(isEmptyString()));
    }

    @Test
    public void VerifyFemalePersonalDataGen() throws NoSuchMethodException {
        String data= genericDataProvider.personalDataGen("female");
        assertThat(data,not(isEmptyString()));
    }

    @Test
    public void VerifyStreetNamersonalUKDataGen() {
        String data= genericDataProvider.streetNameGen("UK");
        String defaultData= genericDataProvider.streetNameGen();
        assertThat(data,not(isEmptyString()));
        assertThat(defaultData,not(isEmptyString()));
    }

    @Test
    public void VerifyCityNamersonalUKDataGen() {
        String data= genericDataProvider.cityNameGen("UK");
        String defaultData= genericDataProvider.cityNameGen();
        assertThat(data,not(isEmptyString()));
        assertThat(defaultData,not(isEmptyString()));
    }

    @Test
    public void VerifyWrongInputPersonalDataGen() {
        assertThrows(NoSuchMethodException.class,()->{
            genericDataProvider.personalDataGen("blabla");
        });
    }
}
