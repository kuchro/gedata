package org.gedata.producer.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

@Component
public class DataProducerImpl implements DataProducer {


    private final InputValueInterpreter inputValueInterpreter;
    private final ObjectMapper objectMapper;

    public DataProducerImpl(InputValueInterpreter inputValueInterpreter, ObjectMapper objectMapper) {
        this.inputValueInterpreter = inputValueInterpreter;
        this.objectMapper = objectMapper;
    }

    @Override
    public JsonNode produceGenericData(String value) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(value);
        ObjectNode targetJson = objectMapper.createObjectNode();
        ArrayNode outerArray = objectMapper.createArrayNode();
        if (jsonNode.isArray()) {
            if (jsonNode.get(0).fields().hasNext()) {
                int iterate = Integer.parseInt(getCounterAndRemoveIt(jsonNode));
                if (jsonNode.isArray()) {
                    jsonNode = jsonNode.get(0);
                }
                for (int i = 0; i < iterate; i++) {
                    ObjectNode newjson = objectMapper.createObjectNode();
                    computeJsonModel(jsonNode, newjson);
                    outerArray.add(newjson);
                }
            } else {
                computeArray(outerArray, jsonNode.get(0));
            }

        } else if (jsonNode.isObject()) {
            computeJsonModel(jsonNode, targetJson);
        }
        var targetValue = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString((outerArray.isEmpty() ? targetJson : outerArray));
        return objectMapper.readTree(targetValue);
    }


    @Override
    public byte[] prepareDataForDownload(String jsonNode) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(produceGenericData(jsonNode)).getBytes();
    }

    @Override
    public boolean validateJsonString(String value) throws JsonProcessingException {
        objectMapper.readTree(value);
        return true;
    }

    private void computeJsonModel(JsonNode jsonNode, ObjectNode targetJson) throws JsonProcessingException {
        Iterator<Map.Entry<String, JsonNode>> iter = jsonNode.fields();
        Map.Entry<String, JsonNode> currentEntry;

        while (iter.hasNext()) {
            currentEntry = iter.next();
            if (currentEntry.getValue().isTextual()) {
                targetJson.put(currentEntry.getKey(), inputValueInterpreter.eval(currentEntry.getValue().asText()));
            } else if (currentEntry.getValue().isObject()) {
                ObjectNode personalData = objectMapper.createObjectNode();
                computeJsonObject(personalData, currentEntry.getValue().fields());
                targetJson.set(currentEntry.getKey(), personalData);
            } else if (currentEntry.getValue().isArray()) {
                ArrayNode outerArray = objectMapper.createArrayNode();
                computeJsonArray(outerArray, currentEntry.getValue());
                targetJson.set(currentEntry.getKey(), outerArray);
            }
        }
    }

    public String getCounterAndRemoveIt(JsonNode currentEntry) {
        String val = "1";
        try {
            if (currentEntry.get(0).has("quantity")) {
                val = currentEntry.get(0).get("quantity").asText();
                ((ArrayNode) currentEntry).remove(0);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return val;
    }

    private void computeJsonObject(ObjectNode map, Iterator<Map.Entry<String, JsonNode>> jsonFields) throws JsonProcessingException {
        while (jsonFields.hasNext()) {
            Map.Entry<String, JsonNode> currentEntry = jsonFields.next();
            if (currentEntry.getValue().isTextual()) {
                map.put(currentEntry.getKey(), inputValueInterpreter.eval(currentEntry.getValue().asText()));
            } else if (currentEntry.getValue().isObject()) {
                ObjectNode outerObject = objectMapper.createObjectNode();
                computeJsonObject(outerObject, currentEntry.getValue().fields());
                map.set(currentEntry.getKey(), outerObject);
            }
        }
    }

    private void computeJsonArray(ArrayNode array, JsonNode jsonNode) throws JsonProcessingException {
        for (JsonNode node : jsonNode) {
            for (int i = 0; i < inputValueInterpreter.evalQuantity(node.asText()); i++) {
                if (node.isObject()) {
                    ObjectNode targetJson = objectMapper.createObjectNode();
                    computeJsonModel(node, targetJson);
                    array.add(targetJson);
                } else if (node.isTextual()) {
                    array.add(inputValueInterpreter.eval(node.asText()));
                }
            }
        }
    }

    private void computeArray(ArrayNode array, JsonNode jsonNode) throws JsonProcessingException {
        for (int i = 0; i < inputValueInterpreter.evalQuantity(jsonNode.asText()); i++) {
            array.add(inputValueInterpreter.eval(jsonNode.asText()));
        }
    }
}
