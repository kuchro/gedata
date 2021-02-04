package org.gedata.producer.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@Component
public class JsonDataProducer implements DataProducer {


    private final InputValueInterpreter inputValueInterpreter;
    private final ObjectMapper objectMapper;

    public JsonDataProducer(InputValueInterpreter inputValueInterpreter, ObjectMapper objectMapper) {
        this.inputValueInterpreter = inputValueInterpreter;
        this.objectMapper = objectMapper;
    }

    @Override
    public JsonNode produceGenericData(String value) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(value);
        ObjectNode targetJson = objectMapper.createObjectNode();
        ArrayNode outerArray = objectMapper.createArrayNode();
      /*  Optional.ofNullable(jsonNode)
                .filter(JsonNode::isObject).ifPresent(x-> computeJsonModel(x,targetJson));

        Optional.ofNullable(jsonNode)
                .filter(JsonNode::isArray)
                .ifPresentOrElse(x -> {
                    x.get(0).fields().hasNext();
                    computeNestedArray(x,outerArray);},()-> computeArray(outerArray, jsonNode.get(0)));
        Optional.ofNullable(jsonNode)
                .filter(JsonNode::isObject).ifPresent(x-> computeJsonModel(x,targetJson));*/
        if (jsonNode.isArray()) {
            if (jsonNode.get(0).fields().hasNext()) {
                computeNestedArray(jsonNode, outerArray);
            } else {
                computeArray(outerArray, jsonNode.get(0));
            }

        } else if (jsonNode.isObject()) {
            computeJsonModel(jsonNode, targetJson);
        }
        var targetValue = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString((outerArray.isEmpty() ? targetJson : outerArray));
        return objectMapper.readTree(targetValue);
    }

    private void computeNestedArray(JsonNode jsonNode, ArrayNode outerArray) {
        int iterate = Integer.parseInt(getCounterAndRemoveIt(jsonNode));
        if (jsonNode.isArray()) {
            jsonNode = jsonNode.get(0);
        }
        for (int i = 0; i < iterate; i++) {
            ObjectNode newjson = objectMapper.createObjectNode();
            computeJsonModel(jsonNode, newjson);
            outerArray.add(newjson);
        }
    }


    @Override
    public byte[] prepareDataForDownload(JsonNode jsonNode) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(jsonNode).getBytes();
    }

    @Override
    public boolean validateJsonString(String value) throws JsonProcessingException {
        objectMapper.readTree(value);
        return true;
    }

    private void computeJsonModel(JsonNode jsonNode, ObjectNode targetJson) {
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

    private void computeJsonObject(ObjectNode map, Iterator<Map.Entry<String, JsonNode>> jsonFields)  {
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

    private void computeJsonArray(ArrayNode array, JsonNode jsonNode) {
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

    private void computeArray(ArrayNode array, JsonNode jsonNode)  {
        for (int i = 0; i < inputValueInterpreter.evalQuantity(jsonNode.asText()); i++) {
            array.add(inputValueInterpreter.eval(jsonNode.asText()));
        }
    }
}
