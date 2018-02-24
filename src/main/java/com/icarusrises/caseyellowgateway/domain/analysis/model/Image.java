package com.icarusrises.caseyellowgateway.domain.analysis.model;

public class Image {

    private String content; // Image In Base64;

    public Image() {
    }

    public Image(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "image hash code: " + content.hashCode();
    }
}
