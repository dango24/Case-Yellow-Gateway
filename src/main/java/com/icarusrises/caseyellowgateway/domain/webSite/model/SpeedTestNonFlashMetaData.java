package com.icarusrises.caseyellowgateway.domain.webSite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeedTestNonFlashMetaData {

    private String buttonId;
    private String finishIdentifier;
    private String retrieveResultFromPayloadFloat;
    private String retrieveResultFromPayloadInteger;
    private List<String> finishTextIdentifier;
    private List<String> finishIdentifierMbps;
    private List<String> finishIdentifierKbps;
    private String resultLocation;
    private String resultAttribute;
}
