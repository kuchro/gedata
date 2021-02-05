package org.gedata.producer.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.gedata.producer.model.producer.GenericData;
import org.gedata.producer.model.producer.InputProducer;

public interface DataProducer {
    JsonNode produceGenericData(String value) throws JsonProcessingException;
    byte[] prepareDataForDownload(JsonNode jsonNode) throws JsonProcessingException;
}
