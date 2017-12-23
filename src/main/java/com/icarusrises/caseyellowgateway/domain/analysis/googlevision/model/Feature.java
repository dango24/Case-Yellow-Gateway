package com.icarusrises.caseyellowgateway.domain.analysis.googlevision.model;

public class Feature {

    private String type;
    private String maxResults;

    public Feature() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(String maxResults) {
        this.maxResults = maxResults;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "type='" + type + '\'' +
                ", maxResults='" + maxResults + '\'' +
                '}';
    }
}
