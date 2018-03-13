package com.icarusrises.caseyellowgateway.domain.file.model;

import lombok.Data;

@Data
public class FileDownloadProperties {

    private int size;
    private String url;
    private String md5;
    private String identifier;

    public FileDownloadProperties() {
    }

    public FileDownloadProperties(String url) {
        this(null, url, 0, null);
    }

    public FileDownloadProperties(String identifier, String url, int size, String md5) {
        this.url = url;
        this.size = size;
        this.md5 = md5;
        this.identifier = identifier;
    }
}
