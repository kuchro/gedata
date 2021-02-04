package org.gedata.producer.generator;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Random;

@Component
public class GeneratorProviderImpl implements GeneratorProvider {
    private final DataPicker dataPicker;
    private final Random random;
    private final SynteticGenerator synteticGenerator;

    public GeneratorProviderImpl(DataPicker dataPicker, SynteticGenerator synteticGenerator) {
        this.dataPicker = dataPicker;
        this.random = new Random();
        this.synteticGenerator = synteticGenerator;
    }

    @Override
    public String femaleNameGen() {
        var data = dataPicker.listOfFemaleNames();
        return dataPicker.listOfFemaleNames().get(random.nextInt(data.size() - 1));
    }
    @Override
    public String maleNameGen() {
        var data = dataPicker.listOfMaleNames();
        return data.get(random.nextInt(data.size() - 1));
    }

    @Override
    public String lastNameGen() {
        var data = dataPicker.listOfLastNames();
        return data.get(random.nextInt(data.size() - 1));
    }

    @Override
    public String nickNameGen() {
        var data = dataPicker.listOfNickName();
        return data.get(data.size() - 1);

    }

    @Override
    public String zipCodeGen(String countryCode) {
        return synteticGenerator.generateFakeZipCode(countryCode);
    }

    @Override
    public String passwordGen(int length) {
        return synteticGenerator.generateFakePassword(length);
    }

    @Override
    public String phoneGen() {
        var data = dataPicker.listOfPhones();
        return data.get(random.nextInt((data.size() - 1)));
    }

    @Override
    public String streetNameGen() {

        return this.streetNameGen("PL");
    }

    @Override
    public String streetNameGen(String region) {
        var data = dataPicker.listOfStreets(Objects.isNull(region) ? "PL" : region);
        return data.get(random.nextInt(data.size() - 1));
    }

    @Override
    public String cityNameGen() {
        return this.cityNameGen("PL");
    }

    @Override
    public String cityNameGen(String region) {
        var data = dataPicker.listOfCiies(Objects.isNull(region) ? "PL" : region);
        return data.get(random.nextInt(data.size() - 1));
    }

    @Override
    public String numberGen() {
        return String.format("%d", random.nextInt(90) + 10);
    }

    @Override
    public int quantity(int value) {
        return value;
    }
}
