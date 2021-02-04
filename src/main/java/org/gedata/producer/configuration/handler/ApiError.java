package org.gedata.producer.configuration.handler;

import java.util.Date;

public class ApiError {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;

    public ApiError() {
    }

    public ApiError(int statusCode, Date timestamp, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ApiError{");
        sb.append("statusCode=").append(statusCode);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", message='").append(message).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
