package com.icarusrises.caseyellowgateway.controllers;

import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadProperties;
import com.icarusrises.caseyellowgateway.domain.test.model.*;
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

    public static final String USER_HEADER = "Case-Yellow-User";

    private CentralService centralService;

    @Autowired
    public CentralController(CentralService centralService) {
        this.centralService = centralService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/next-web-site",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public SpeedTestMetaData getNextSpeedTestWebSite(@RequestHeader(USER_HEADER)String user) {
        logger.info(String.format("Received getNextSpeedTestWebSite GET request, from user: %s", user));
        return centralService.getNextSpeedTestWebSite();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/next-urls",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FileDownloadProperties> getFileDownloadMetaData(@RequestHeader(USER_HEADER)String user) {
        logger.info(String.format("Received getFileDownloadMetaData GET request from user: %s", user));
        return centralService.getNextUrls();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/google-vision-key",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public GoogleVisionKey googleVisionKey(@RequestHeader(USER_HEADER)String user) {
        logger.info(String.format("Received googleVisionKey GET request, from user: %s", user));
        return centralService.googleVisionKey();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/save-test")
    public void saveTest(@RequestHeader(USER_HEADER)String user, @RequestBody Test test) throws IOException {
        logger.info(String.format("Received saveTest POST request with test : %s, from user: %s", test, user));
        test.setUser(user);
        centralService.saveTest(test);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all-tests")
    public List<Test> getAllTests(@RequestHeader(USER_HEADER)String user) {
        logger.info(String.format("Received getAllTests GET request, from user: %s", user));
        return centralService.getAllTests();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pre-signed-url")
    public PreSignedUrl generatePreSignedUrl(@RequestHeader(USER_HEADER)String user, @RequestParam("user_ip")String userIP, @RequestParam("file_name")String fileName) {
        logger.info(String.format("Received generatePreSignedUrl GET request with fileName: %s, userIP: %s, from user: %s", fileName, userIP, user));
        return centralService.generatePreSignedUrl(userIP, fileName);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/failed-test")
    public void failedTest(@RequestHeader(USER_HEADER)String user, @RequestBody FailedTestDetails failedTestDetails) throws IOException {
        logger.info(String.format("Received HttpStatus POST request with failed test: %s, from user: %s", failedTestDetails, user));
        centralService.failedTest(failedTestDetails);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/connection-details")
    private Map<String, List<String>> connectionDetails(@RequestHeader(USER_HEADER)String user) {
        logger.info(String.format("Received connectionDetails GET request, from user: %s", user));
        return centralService.getConnectionDetails();
    }

    @GetMapping("/statistics/count-ips")
    public Map<String, Long> countIPs(@RequestHeader(USER_HEADER)String user) {
        logger.info(String.format("Received countIPs GET request, from user: %s", user));
        return centralService.countIPs();
    }

    @GetMapping("/statistics/identifiers-details")
    public Map<String, IdentifierDetails> identifiersDetails(@RequestHeader(USER_HEADER)String user) {
        logger.info(String.format("Received identifiersDetails GET request, from user: %s", user));
        return centralService.createIdentifiersDetails();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/save-connection-details")
    public void saveConnectionDetails(@RequestHeader(USER_HEADER)String user, @RequestBody ConnectionDetails connectionDetails) {
        logger.info(String.format("Received saveConnectionDetails POST request with connectionDetails: %s, from user: %s", connectionDetails, user));
        UserDetails userDetails = new UserDetails(user, connectionDetails);
        centralService.saveConnectionDetails(userDetails);
    }


}