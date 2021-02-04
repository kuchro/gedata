package org.gedata.producer.model.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputProducer {

    private String outputFormat;
    private String jsonModel;
}
