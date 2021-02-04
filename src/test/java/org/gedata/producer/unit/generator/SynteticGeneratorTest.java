package org.gedata.producer.unit.generator;


import org.gedata.producer.generator.SynteticGenerator;


import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;




import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SynteticGeneratorTest {

    @Test
    void zipCodeGeneratorForDECountryCode() {
        SynteticGenerator synteticGenerator = new SynteticGenerator();
        String zipCode = synteticGenerator.generateFakeZipCode("DE");
        assertTrue(matchesPattern("\\d{5}-\\d{5}").matches(zipCode));
    }

    @Test
    void zipCodeGeneratorForPLCountryCode() {
        SynteticGenerator synteticGenerator = new SynteticGenerator();
        String zipCode = synteticGenerator.generateFakeZipCode("PL");
        assertTrue(matchesPattern("\\d{2}-\\d{3}").matches(zipCode));
    }

    @Test
    void zipCodeGeneratorForGBPCountryCode() {
        SynteticGenerator synteticGenerator = new SynteticGenerator();
        String zipCode = synteticGenerator.generateFakeZipCode("GBP");
        assertTrue(matchesPattern("[A-Z0-9]{4}-[A-Z0-9]{3}").matches(zipCode));
    }




}