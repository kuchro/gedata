package org.gedata.producer.model.producer;

import java.util.Map;

public class InputProducer {

    private String outputFormat;
    private String outputDestination;
    private String jsonModel;


    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String getOutputDestination() {
        return outputDestination;
    }

    public void setOutputDestination(String outputDestination) {
        this.outputDestination = outputDestination;
    }

    public String getJsonModel() {
        return jsonModel;
    }

    public void setJsonModel(String jsonModel) {
        this.jsonModel = jsonModel;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InputProducer{");
        sb.append("outputFormat='").append(outputFormat).append('\'');
        sb.append(", outputDestination='").append(outputDestination).append('\'');
        sb.append(", jsonModel='").append(jsonModel).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
