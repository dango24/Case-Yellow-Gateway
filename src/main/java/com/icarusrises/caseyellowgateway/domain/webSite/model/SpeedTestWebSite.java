package com.icarusrises.caseyellowgateway.domain.webSite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Dan on 12/10/2016.
 */

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpeedTestWebSite {

    private boolean succeed;
    private String urlAddress;
    private String speedTestIdentifier;
    private String nonFlashResult;
    private String path;
    private long startMeasuringTimestamp;
    private double downloadRateInMbps; // Mega bit per second
    private double downloadRateInKBps; // Mega bit per second
}
