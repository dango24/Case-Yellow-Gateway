package com.icarusrises.caseyellowgateway.controllers;

import com.icarusrises.caseyellowgateway.domain.analysis.googlevision.model.GoogleVisionRequest;
import com.icarusrises.caseyellowgateway.domain.analysis.googlevision.model.OcrResponse;
import com.icarusrises.caseyellowgateway.services.analysis.AnalysisService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalysisController {

    private Logger logger = Logger.getLogger(AnalysisController.class);

    private AnalysisService analysisService;

    @Autowired
    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping("/ocr_request")
    public OcrResponse ocrRequest(@RequestBody GoogleVisionRequest googleVisionRequest) {
        logger.info("Received ocrRequest: " + googleVisionRequest);
        return analysisService.ocrRequest(googleVisionRequest);
    }
}
