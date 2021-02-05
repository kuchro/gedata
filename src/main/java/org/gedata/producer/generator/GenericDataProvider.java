package org.gedata.producer.generator;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Random;

@Component
public class GenericDataProvider implements GeneratorProvider {
    private final DataPicker dataPicker;
    private final Random random;
    private final SynteticGenerator synteticGenerator;

    public GenericDataProvider(DataPicker dataPicker, SynteticGenerator synteticGenerator) {
        this.dataPicker = dataPicker;
        this.random = new Random();
        this.synteticGenerator = synteticGenerator;
    }

    @Override
    public String personalDataGen(String genName) throws NoSuchMethodException {
        var data = dataPicker.personalGen(genName);
        if(Objects.isNull(data)){
            throw new NoSuchMethodException(String.format("Generator with name: %s does not exist.",genName));
        }
        return data.get(random.nextInt(data.size() - 1));
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
