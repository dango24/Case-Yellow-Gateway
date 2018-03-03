package com.icarusrises.caseyellowgateway.controllers;

import com.icarusrises.caseyellowgateway.domain.analysis.model.GoogleVisionRequest;
import com.icarusrises.caseyellowgateway.domain.analysis.model.ImageClassificationStatus;
import com.icarusrises.caseyellowgateway.domain.analysis.model.OcrResponse;
import com.icarusrises.caseyellowgateway.domain.analysis.model.VisionRequest;
import com.icarusrises.caseyellowgateway.services.analysis.AnalysisService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.icarusrises.caseyellowgateway.controllers.CentralController.USER_HEADER;

@RestController
public class AnalysisController {

    private Logger logger = Logger.getLogger(AnalysisController.class);

    private AnalysisService analysisService;

    @Autowired
    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/ocr_request")
    public OcrResponse ocrRequest(@RequestHeader(USER_HEADER)String user, @RequestBody GoogleVisionRequest googleVisionRequest) {
        logger.info(String.format("Received ocrRequest: %s, from user: %s", googleVisionRequest, user));
        return analysisService.ocrRequest(googleVisionRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/classify-image")
    public ImageClassificationStatus classifyImage(@RequestParam("identifier") String identifier, @RequestBody VisionRequest visionRequest) {
        logger.info(String.format("Received classifyImage GET request for image: %s", visionRequest));
        return analysisService.classifyImage(identifier, visionRequest);
    }
}
