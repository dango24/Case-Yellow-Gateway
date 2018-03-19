package com.icarusrises.caseyellowgateway.controllers;

import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadProperties;
import com.icarusrises.caseyellowgateway.domain.test.model.*;
import com.icarusrises.caseyellowgateway.domain.webSite.model.GoogleVisionKey;
import com.icarusrises.caseyellowgateway.domain.webSite.model.SpeedTestMetaData;
import com.icarusrises.caseyellowgateway.services.central.CentralService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/central")
public class CentralController {

    public static final String USER_HEADER = "Case-Yellow-User";

    private CentralService centralService;

    @Autowired
    public CentralController(CentralService centralService) {
        this.centralService = centralService;
    }

    @GetMapping(value = "/next-web-site",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public SpeedTestMetaData getNextSpeedTestWebSite(@RequestHeader(USER_HEADER)String user) {
        log.info(String.format("Received getNextSpeedTestWebSite GET request, from user: %s", user));
        return centralService.getNextSpeedTestWebSite();
    }

    @GetMapping(value = "/next-urls",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FileDownloadProperties> getFileDownloadMetaData(@RequestHeader(USER_HEADER)String user) {
        log.info(String.format("Received getFileDownloadMetaData GET request from user: %s", user));
        return centralService.getNextUrls();
    }

    @GetMapping(value = "/google-vision-key",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public GoogleVisionKey googleVisionKey(@RequestHeader(USER_HEADER)String user) {
        log.info(String.format("Received googleVisionKey GET request, from user: %s", user));
        return centralService.googleVisionKey();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/save-test")
    public void saveTest(@RequestHeader(USER_HEADER)String user, @RequestBody Test test) throws IOException {
        log.info(String.format("Received saveTest POST request with test : %s, from user: %s", test, user));
        test.setUser(user);
        centralService.saveTest(test);
    }

    @GetMapping("/all-tests")
    public List<Test> getAllTests(@RequestHeader(USER_HEADER)String user) {
        log.info(String.format("Received getAllTests GET request, from user: %s", user));
        return centralService.getAllTests();
    }

    @GetMapping(value = "/all-user-tests",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Test> getAllUserTests(@RequestParam("user") String user) {
        log.info(String.format("Received getAllTestsByUser GET request for user: %s", user));
        return centralService.getAllTestsByUser(user);
    }

    @GetMapping("/pre-signed-url")
    public PreSignedUrl generatePreSignedUrl(@RequestHeader(USER_HEADER)String user, @RequestParam("file_key")String fileKey) {
        log.info(String.format("Received generatePreSignedUrl GET request with fileName: %s, from user: %s", fileKey, user));
        return centralService.generatePreSignedUrl(fileKey);
    }

    @PostMapping("/failed-test")
    public void failedTest(@RequestHeader(USER_HEADER)String user, @RequestBody FailedTestDetails failedTestDetails) throws IOException {
        log.info(String.format("Received HttpStatus POST request with failed test: %s, from user: %s", failedTestDetails, user));
        failedTestDetails.setUser(user);
        centralService.failedTest(failedTestDetails);
    }

    @GetMapping("/connection-details")
    private Map<String, List<String>> connectionDetails(@RequestHeader(USER_HEADER)String user) {
        log.info(String.format("Received connectionDetails GET request, from user: %s", user));
        return centralService.getConnectionDetails();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/save-connection-details")
    public void saveConnectionDetails(@RequestHeader(USER_HEADER)String user, @RequestBody ConnectionDetails connectionDetails) {
        log.info(String.format("Received saveConnectionDetails POST request with connectionDetails: %s, from user: %s", connectionDetails, user));
        UserDetails userDetails = new UserDetails(user, connectionDetails);
        centralService.saveConnectionDetails(userDetails);
    }

}