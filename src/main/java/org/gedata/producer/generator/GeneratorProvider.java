package org.gedata.producer.generator;

public interface GeneratorProvider {
    String personalDataGen(String genName) throws NoSuchMethodException;

    String zipCodeGen(String countryCode);

    String passwordGen(int length);

    String streetNameGen();

    String streetNameGen(String region);

    String cityNameGen();

    String cityNameGen(String region);

    String numberGen();

    int quantity(int value);
}
