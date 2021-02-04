package org.gedata.producer.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.Map;

@Component
public class SQLInsertProducerImpl implements SQLInsertProducer {

    private final InputValueInterpreter inputValueInterpreter;
    private final ObjectMapper objectMapper;

    public SQLInsertProducerImpl(InputValueInterpreter inputValueInterpreter, ObjectMapper objectMapper) {
        this.inputValueInterpreter = inputValueInterpreter;
        this.objectMapper = objectMapper;
    }

    @Override
    public JsonNode insertProducer(String text) throws JsonProcessingException {
        return computeJsonString(text);
    }

    private JsonNode computeStringValue(String text) {
        return null;
    }

    private JsonNode computeJsonString(String text) throws JsonProcessingException {
        ArrayNode outerArray = objectMapper.createArrayNode();
        JsonNode jsonNode = objectMapper.readTree(text);
        int iterate = Integer.parseInt(getCounterAndRemoveIt(jsonNode));
        if (jsonNode.isArray()) {
            jsonNode = jsonNode.get(0);
        }
        for (int i = 0; i < iterate; i++) {
            StringBuilder start = new StringBuilder(String.format("INSERT INTO %s (", jsonNode.get("tableName").asText()));
            StringBuilder end = new StringBuilder(String.format(" VALUES(", jsonNode.get("tableName").asText()));
            Iterator<Map.Entry<String, JsonNode>> iter = jsonNode.get("columns").fields();
            Map.Entry<String, JsonNode> currentEntry;
            while (iter.hasNext()) {
                currentEntry = iter.next();
                if (currentEntry.getValue().isTextual()) {
                    start.append(String.format("%s,", currentEntry.getKey()));
                    end.append(String.format("'%s',", inputValueInterpreter.eval(currentEntry.getValue().asText())));
                } else if (currentEntry.getValue().isObject()) {
                    throw new InvalidParameterException("JsonObject is not allowed in [columns] for SQL Insert query generator.");
                } else if (currentEntry.getValue().isArray()) {
                    throw new InvalidParameterException("JsonArray is not allowed in [columns] for SQL Insert query generator.");
                }
            }
            start.replace(start.length() - 1, start.length(), "");
            end.replace(end.length() - 1, end.length(), "");
            start.append(")");
            end.append(");");
            start.append(end.toString());
            outerArray.add(start.toString());
        }
        return outerArray;
    }


    private boolean isJSON(String value) {
        try {
            objectMapper.readTree(value);
            return true;
        } catch (JsonProcessingException ex) {
            return false;
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


}
