package org.gedata.producer.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface SQLInsertProducer {
    public JsonNode insertProducer(String text) throws JsonProcessingException;
}
