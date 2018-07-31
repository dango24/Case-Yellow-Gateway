package com.icarusrises.caseyellowgateway.controllers;

import com.icarusrises.caseyellowgateway.domain.analysis.model.*;
import com.icarusrises.caseyellowgateway.services.analysis.AnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.icarusrises.caseyellowgateway.controllers.CentralController.USER_HEADER;

@Slf4j
@RestController
public class AnalysisController {

    private AnalysisService analysisService;

    @Autowired
    public AnalysisController(AnalysisService analysisServic) {
        this.analysisService = analysisService;
    }

    @PostMapping("/ocr_request")
    public OcrResponse ocrRequest(@RequestHeader(USER_HEADER)String user, @RequestBody GoogleVisionRequest googleVisionRequest) {
        log.info(String.format("Received ocrRequest: %s, from user: %s", googleVisionRequest, user));
        return analysisService.ocrRequest(googleVisionRequest);
    }

    @PostMapping("/classify-image")
    public ImageClassificationResult classifyImage(@RequestHeader(USER_HEADER)String user,
                                                   @RequestParam("identifier") String identifier,
                                                   @RequestBody VisionRequest visionRequest) {

        log.info(String.format("Received classifyImage GET request for image: %s", visionRequest));
        return analysisService.classifyImage(user, identifier, visionRequest);
    }

    @PostMapping("/is-description-exist")
    public DescriptionMatch isDescriptionExist(@RequestHeader(USER_HEADER)String user,
                                               @RequestParam("identifier")String identifier,
                                               @RequestParam("startTest")boolean startTest,
                                               @RequestBody GoogleVisionRequest visionRequest) {

        log.info("Received isDescriptionExist POST request for identifier: " + identifier);
        return analysisService.isDescriptionExist(user, identifier, startTest, visionRequest);
    }

    @PostMapping("/parse-html")
    public HTMLParserResult retrieveResultFromHtml(@RequestHeader(USER_HEADER)String user,
                                                   @RequestParam("identifier")String identifier,
                                                   @RequestBody HTMLParserRequest htmlParserRequest) {

        log.info("Received isDescriptionExist POST request for identifier: " + identifier);
        return analysisService.retrieveResultFromHtml(user, identifier, htmlParserRequest);
    }

    @PostMapping("/start-button-successfully-found")
    public void startButtonSuccessfullyFound(@RequestHeader(USER_HEADER)String user,
                                             @RequestParam("identifier")String identifier,
                                             @RequestParam("x")int x,
                                             @RequestParam("y")int y, @RequestBody VisionRequest visionRequest) {

        log.info("Received startButtonSuccessfullyFound POST request for identifier: " + identifier);
        analysisService.startButtonSuccessfullyFound(user, identifier, x, y, visionRequest);
    }


    @PostMapping("/start-button-failed")
    public void startButtonFailed(@RequestHeader(USER_HEADER)String user,
                                 @RequestParam("identifier")String identifier,
                                 @RequestParam("x")int x,
                                 @RequestParam("y")int y, @RequestBody VisionRequest visionRequest) {

        log.info("Received startButtonFailed POST request for identifier: " + identifier);
        analysisService.startButtonFailed(user, identifier, x, y, visionRequest);
    }
}
