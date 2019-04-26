package com.icarusrises.caseyellowgateway.controllers;

import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadProperties;
import com.icarusrises.caseyellowgateway.domain.test.model.*;
import com.icarusrises.caseyellowgateway.domain.users.LogData;
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
        return centralService.getNextSpeedTestWebSite(user);
    }

    @GetMapping(value = "/next-urls",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FileDownloadProperties> getFileDownloadMetaData(@RequestHeader(USER_HEADER)String user) {
        log.info(String.format("Received getFileDownloadMetaData GET request from user: %s", user));
        return centralService.getNextUrls(user);
    }


    @GetMapping(value = "/run-classic-test",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean runClassicTest(@RequestHeader(USER_HEADER)String user) {
        log.info(String.format("Received runClassicTest GET request from user: %s", user));
        return centralService.runClassicTest(user);
    }

    @GetMapping(value = "/test-life-cycle",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public int getTestLifeCycle(@RequestHeader(USER_HEADER) String userName) {
        log.info(String.format("Received getTestLifeCycle GET request for user: %s", userName));
        return centralService.getTestLifeCycle(userName);
    }

    @PostMapping(value = "/update-test-life-cycle",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateTestLifeCycle(@RequestHeader(USER_HEADER) String userName) {
        log.info("Received updateTestLifeCycle POST request for user: " + userName);
        centralService.updateTestLifeCycle(userName);
    }

    @GetMapping(value = "/chrome-options-arguments",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getChromeOptionsArguments(@RequestHeader(USER_HEADER)String user) {
        log.info(String.format("Received getChromeOptionsArguments GET request from user: %s", user));
        return centralService.getChromeOptionsArguments(user);
    }

    @GetMapping(value = "/google-vision-key",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public GoogleVisionKey googleVisionKey(@RequestHeader(USER_HEADER)String user) {
        log.info(String.format("Received googleVisionKey GET request, from user: %s", user));
        return centralService.googleVisionKey(user);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/save-test")
    public void saveTest(@RequestHeader(USER_HEADER)String user, @RequestBody Test test) throws IOException {
        log.info(String.format("Received saveTest POST request with test : %s, from user: %s", test, user));
        test.setUser(user);
        centralService.saveTest(test, user);
    }

    @PostMapping("/build-all-tests")
    public void buildAllTests() {
        log.info(String.format("Received buildAllTests POST request with users"));
        centralService.buildAllTests();
    }

    @GetMapping("/all-tests")
    public List<Test> getAllTests(@RequestHeader(USER_HEADER)String user) {
        log.info(String.format("Received getAllTests GET request, from user: %s", user));
        return centralService.getAllTests(user);
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
        return centralService.generatePreSignedUrl(fileKey, user);
    }

    @PostMapping("/failed-test")
    public void failedTest(@RequestHeader(USER_HEADER)String user, @RequestBody FailedTestDetails failedTestDetails) {
        log.info(String.format("Received HttpStatus POST request with failed test: %s, from user: %s", failedTestDetails, user));
        failedTestDetails.setUser(user);
        centralService.failedTest(failedTestDetails, user);
    }

    @PostMapping("/upload-log-data")
    public void uploadLogData(@RequestHeader(USER_HEADER)String user, @RequestBody LogData logData)  {
        log.info(String.format("Received uploadLogData POST request for user: %s, with logData: %s", user, logData));
        centralService.uploadLogData(user, logData);
    }

    @GetMapping("/connection-details")
    private Map<String, List<String>> connectionDetails(@RequestHeader(USER_HEADER)String user) {
        log.info(String.format("Received connectionDetails GET request, from user: %s", user));
        return centralService.getConnectionDetails(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/save-connection-details")
    public void saveConnectionDetails(@RequestHeader(USER_HEADER)String user, @RequestBody ConnectionDetails connectionDetails) {
        log.info(String.format("Received saveConnectionDetails POST request with connectionDetails: %s, from user: %s", connectionDetails, user));
        UserDetails userDetails = new UserDetails(user, connectionDetails);
        centralService.saveConnectionDetails(userDetails, user);
    }

    @PostMapping("/start-test")
    public void startTest(@RequestBody StartTestDetails startTestDetails) {
        log.info(String.format("Received startTest POST request with start test details: %s", startTestDetails));
        centralService.startTest(startTestDetails);
    }

    @PostMapping("/investigate-test-ratio")
    public void investigateSuspiciousTestRatio(@RequestParam("outliar_ratio")String outliarRatio,
                                               @RequestParam("hours")String hours) {
        log.info(String.format("Received investigate-test-ratio POST request with outliarRatio: %s, hours: %s", outliarRatio, hours));
        centralService.investigateSuspiciousTestRatio(outliarRatio, hours);
    }

    @PostMapping("/unanalyzed-tests")
    public void unAnalyzedTests(@RequestParam("period_in_days")int periodInDays, @RequestParam("analyzed_state_code")int analyzedStateCode) {
        log.info(String.format("Received unAnalyzedTests POST request"));
        centralService.unAnalyzedTests(periodInDays, analyzedStateCode);
    }

}