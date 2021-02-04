package org.gedata.producer.generator;

public interface InputValueInterpreter {

    String eval(String expression);
    int evalQuantity(String expression);
}
