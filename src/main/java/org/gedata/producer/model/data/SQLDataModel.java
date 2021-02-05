package org.gedata.producer.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SQLDataModel {
    private String tableName;
    private Map<String, String> columns;
}
