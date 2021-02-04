package org.gedata.producer.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface DataProducer {
    JsonNode produceGenericData(String value) throws JsonProcessingException;
    byte[] prepareDataForDownload(String jsonNode) throws JsonProcessingException;
    boolean validateJsonString(String value) throws JsonProcessingException;
}
