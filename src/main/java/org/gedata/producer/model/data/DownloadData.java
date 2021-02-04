package org.gedata.producer.model.data;

import java.time.Instant;

public class DownloadData {
    private byte[] content;
    private String fileName;
    private Instant downloadTime;

    public DownloadData(byte[] content, String fileName, Instant downloadTime) {
        this.content = content;
        this.fileName = fileName;
        this.downloadTime = downloadTime;
    }

    public DownloadData() {
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Instant getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(Instant downloadTime) {
        this.downloadTime = downloadTime;
    }
}
