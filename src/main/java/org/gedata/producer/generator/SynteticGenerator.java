package org.gedata.producer.generator;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class SynteticGenerator {

    private final String digitStr = "0123456789";
    private final String alphabet = "ABCDEFGHIJKLMOPQRSTUVWXYZ";

    private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
    private static final String DIGIT = "0123456789";
    private static final String OTHER_PUNCTUATION = "!@#&()–[{}]:;',?/*";
    private static final String OTHER_SYMBOL = "~$^+=<>";
    private static final String OTHER_SPECIAL = OTHER_PUNCTUATION + OTHER_SYMBOL;
    private static final String PASSWORD_ALLOW =
            CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT + OTHER_SPECIAL;

    static SecureRandom rnd = new SecureRandom();
    private Map<String, int[]> countryMapping = new HashMap<>(){
        {put("DE", new int[]{5, 5});}
        {put("PL", new int[]{2, 3});}
        {put("GBP", new int[]{4, 3});}

        };


    public String generateFakePassword(int length){
        return generate(length,PASSWORD_ALLOW);
    }

    public String generateFakeZipCode(String countryCode){
        switch (countryCode){
            case "DE":
                return generateZipDigit(countryCode);
            case "PL":
                return generateZipDigit(countryCode);
            case "GBP":
                return generateZipAllChars(countryCode);
            default:
                throw new IllegalArgumentException(String.format("Country code %s does not exist on the list",countryCode));
        }
    }

    private String generateZipDigit(String countryCode){
        return String.format("%s-%s",generate(countryMapping.get(countryCode)[0],digitStr)
                ,generate(countryMapping.get(countryCode)[1],digitStr));
    }

    private String generateZipAllChars(String countryCode){
        return String.format("%s-%s",generate(countryMapping.get(countryCode)[0],digitStr+alphabet)
                ,generate(countryMapping.get(countryCode)[1],digitStr+alphabet));
    }


    private String generate( int size,String input) {

        if (input == null || input.length() <= 0)
            throw new IllegalArgumentException("Invalid input.");
        if (size < 1) throw new IllegalArgumentException("Invalid size.");

        StringBuilder result = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            // produce a random order
            int index = rnd.nextInt(input.length());
            result.append(input.charAt(index));
        }
        return result.toString();
    }

    public static String shuffleString(String input) {
        List<String> result = Arrays.asList(input.split(""));
        Collections.shuffle(result);
        return result.stream().collect(Collectors.joining());
    }



}
