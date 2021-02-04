package org.gedata.producer.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadData {
    private byte[] content;
    private String fileName;
    private Instant downloadTime;

}
