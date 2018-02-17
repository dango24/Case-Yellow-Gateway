package com.icarusrises.caseyellowgateway.controllers;

import com.icarusrises.caseyellowgateway.domain.analysis.googlevision.model.GoogleVisionRequest;
import com.icarusrises.caseyellowgateway.domain.analysis.googlevision.model.OcrResponse;
import com.icarusrises.caseyellowgateway.services.analysis.AnalysisService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import static com.icarusrises.caseyellowgateway.controllers.CentralController.USER_HEADER;

@RestController
public class AnalysisController {

    private Logger logger = Logger.getLogger(AnalysisController.class);

    private AnalysisService analysisService;

    @Autowired
    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping("/ocr_request")
    public OcrResponse ocrRequest(@RequestHeader(USER_HEADER)String user, @RequestBody GoogleVisionRequest googleVisionRequest) {
        logger.info(String.format("Received ocrRequest: %s, from user: %s", googleVisionRequest, user));
        return analysisService.ocrRequest(googleVisionRequest);
    }
}
