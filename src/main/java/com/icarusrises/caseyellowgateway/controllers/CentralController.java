package com.icarusrises.caseyellowgateway.controllers;

import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadMetaData;
import com.icarusrises.caseyellowgateway.domain.test.model.FailedTestDetails;
import com.icarusrises.caseyellowgateway.domain.test.model.IdentifierDetails;
import com.icarusrises.caseyellowgateway.domain.test.model.PreSignedUrl;
import com.icarusrises.caseyellowgateway.domain.test.model.Test;
import com.icarusrises.caseyellowgateway.domain.webSite.model.GoogleVisionKey;
import com.icarusrises.caseyellowgateway.domain.webSite.model.SpeedTestMetaData;
import com.icarusrises.caseyellowgateway.services.central.CentralService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by writeToFile on 6/25/17.
 */
@RestController
@RequestMapping("/central")
public class CentralController {

    private Logger logger = Logger.getLogger(CentralController.class);

    private CentralService centralService;

    @Autowired
    public CentralController(CentralService centralService) {
        this.centralService = centralService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/next-web-site",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public SpeedTestMetaData getNextSpeedTestWebSite() {
        logger.info("Received getNextSpeedTestWebSite GET request");
        return centralService.getNextSpeedTestWebSite();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/next-urls",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FileDownloadMetaData> getFileDownloadMetaData(@RequestParam("num_of_comparison_per_test") int numOfComparisonPerTest) {
        logger.info("Received getFileDownloadMetaData GET request with num_of_comparison_per_test: " + numOfComparisonPerTest);
        return centralService.getNextUrls(numOfComparisonPerTest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/google-vision-key",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public GoogleVisionKey googleVisionKey() {
        logger.info("Received googleVisionKey GET request");
        return centralService.googleVisionKey();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/save-test")
    public void saveTest(@RequestBody Test test) throws IOException {
        logger.info("Received saveTest POST request with test : " + test);
        centralService.saveTest(test);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all-tests")
    public List<Test> getAllTests() {
        logger.info("Received getAllTests GET request");

        return centralService.getAllTests();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pre-signed-url")
    public PreSignedUrl generatePreSignedUrl(@RequestParam("user_ip")String userIP, @RequestParam("file_name")String fileName) {
        return centralService.generatePreSignedUrl(userIP, fileName);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/failed-test")
    public void failedTest(@RequestBody FailedTestDetails failedTestDetails) throws IOException {
        logger.info("Received HttpStatus POST request with failed test : " + failedTestDetails);
        centralService.failedTest(failedTestDetails);
    }

    @GetMapping("/statistics/count-ips")
    public Map<String, Long> countIPs() {
        logger.info("Received countIPs GET request");
        return centralService.countIPs();
    }

    @GetMapping("/statistics/identifiers-details")
    public Map<String, IdentifierDetails> identifiersDetails() {
        logger.info("Received identifiersDetails GET request");
        return centralService.createIdentifiersDetails();
    }

}