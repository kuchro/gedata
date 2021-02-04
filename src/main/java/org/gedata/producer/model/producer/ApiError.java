package org.gedata.producer.model.producer;

import lombok.*;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private int statusCode;
    private Instant time;
    private String message;
    private String description;
}
