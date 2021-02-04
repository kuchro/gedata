package org.gedata.producer.generator;

public interface GeneratorProvider {
    String femaleNameGen();

    String maleNameGen();

    String lastNameGen();

    String nickNameGen();

    String zipCodeGen(String countryCode);

    String passwordGen(int length);

    String phoneGen();

    String streetNameGen();

    String streetNameGen(String region);

    String cityNameGen();

    String cityNameGen(String region);

    String numberGen();

    int quantity(int value);
}
