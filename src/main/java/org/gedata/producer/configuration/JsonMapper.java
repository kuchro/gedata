package org.gedata.producer.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonMapper {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY)
                .registerModule(new JavaTimeModule());

    }
}
