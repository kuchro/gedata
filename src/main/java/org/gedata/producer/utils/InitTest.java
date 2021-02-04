package org.gedata.producer.utils;

import lombok.AllArgsConstructor;
import org.gedata.producer.configuration.generator.PersonalDataConfiguration;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
@AllArgsConstructor
@Component
public class InitTest implements ApplicationRunner {

    private final PersonalDataConfiguration personalDataConfiguration;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var xx = personalDataConfiguration;
        System.out.println("");
    }
}
