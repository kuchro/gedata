package org.gedata.producer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.gedata.producer.model.data.SQLDataModel;
import org.gedata.producer.model.producer.GenericData;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Data
@AllArgsConstructor
@Component
public class JsonModelValidator {

    private final ObjectMapper objectMapper;

    public Object validateJsonString(String jsonModel,String outputformat) throws JsonProcessingException {

        return  outputformat.equals("sql") ?
                validateSqlDataModel(jsonModel) :
                objectMapper.readTree(jsonModel);
    }

    private SQLDataModel validateSqlDataModel(String jsonModel) {
        try {
            return objectMapper.readValue(jsonModel, SQLDataModel.class);
        } catch (Exception ex) {
            throw new SQLDataModelExceptionFormat(
                    String.format("jsonModel format has wrong format for SQL insert. Example of SQL Data model: %s",
                            preparePrettySQLDataObject(new SQLDataModel("<tableName>",
                                    new HashMap<>() {{
                                        put("<columnName1>", "{value}");
                                        put("<columnName2>", "{value}");
                                    }}))));
        }
    }

    private String preparePrettySQLDataObject(SQLDataModel sqlDataModel) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sqlDataModel);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Something went wrong during the preparation of the Error Message.";
        }
    }
}
