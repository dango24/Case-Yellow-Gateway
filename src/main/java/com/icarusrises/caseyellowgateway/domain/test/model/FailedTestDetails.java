package com.icarusrises.caseyellowgateway.domain.test.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FailedTestDetails {

    private String ip;
    private String path;
    private String user;
    private String identifier;
    private String errorMessage;
    private String clientVersion;
}
