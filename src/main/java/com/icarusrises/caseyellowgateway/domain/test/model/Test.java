package com.icarusrises.caseyellowgateway.domain.test.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Dan on 24/10/2016.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Test {

    private String testID;
    private String user;
    private String isp;
    private String clientVersion;
    private String computerIdentifier;
    private Long startTime;
    private Long endTime;
    private SystemInfo systemInfo;
    private String speedTestWebsiteIdentifier;
    private List<ComparisonInfo> comparisonInfoTests;
}
