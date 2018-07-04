package com.icarusrises.caseyellowgateway.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.icarusrises.caseyellowgateway.domain.test.model.IdentifierDetails;
import com.icarusrises.caseyellowgateway.domain.test.model.LastUserTest;
import com.icarusrises.caseyellowgateway.domain.test.model.UserLastTest;
import com.icarusrises.caseyellowgateway.domain.test.model.UsersLastTest;
import com.icarusrises.caseyellowgateway.services.central.CentralService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.icarusrises.caseyellowgateway.controllers.CentralController.USER_HEADER;

@Slf4j
@RestController
@RequestMapping("/central/statistics")
public class StatisticsController {

    private CentralService centralService;

    @Autowired
    public StatisticsController(CentralService centralService) {
        this.centralService = centralService;
    }

    @GetMapping("/count-ips")
    public Map<String, Long> countIPs(@RequestHeader(USER_HEADER)String user) {
        log.info(String.format("Received countIPs GET request, from user: %s", user));
        return centralService.countIPs();
    }

    @GetMapping("/identifiers-details")
    public Map<String, IdentifierDetails> identifiersDetails(@RequestHeader(USER_HEADER)String user,
                                                             @RequestParam(value = "filter", required = false)  String filter) {
        log.info(String.format("Received identifiersDetails GET request, from user: %s", user));
        return centralService.createIdentifiersDetails(filter);
    }

    @GetMapping("/identifiers-details/{user}")
    public Map<String, IdentifierDetails> identifiersDetailsByUser(@PathVariable("user")String user,
                                                                   @RequestParam(value = "filter", required = false)  String filter) {
        log.info(String.format("Received identifiersDetails GET request for user: %s", user));
        return centralService.createIdentifiersDetails(user, filter);
    }

    @GetMapping("/user-last-test")
    public UserLastTest userLastTest(@RequestParam("user") String user) {
        log.info(String.format("Received userLastTest GET request, for user: %s", user));
        return centralService.userLastTest(user);
    }

    @GetMapping("/user-last-failed-test")
    public UserLastTest userLastFailedTest(@RequestParam("user") String user) {
        log.info(String.format("Received userLastFailedTest GET request, for user: %s", user));
        return centralService.userLastFailedTest(user);
    }

    @GetMapping("/count-user-tests")
    public Map<String, Long> countUserTests() {
        log.info("Received countIPs GET request");
        return centralService.countUserTests();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/notify-last-tests")
    public void notifyLastTests() {
        log.info(String.format("Received notifyLastTests POST request"));
        centralService.notifyLastTests();
    }

    @GetMapping("/user-mean-rate/{user}")
    public Map<String, String> userMeanRate(@PathVariable("user") String user) {
        log.info(String.format("Received userMeanRate GET request for user %s", user));
        return centralService.getUserMeanRate(user);
    }

    @GetMapping("/users-last-test")
    public UsersLastTestReport usersLastTest(@RequestParam("period") int lastTimeInHours) {
        log.info(String.format("Received usersLastTest GET request with last time in hours %s", lastTimeInHours));
        UsersLastTest usersLastTest = centralService.usersLastTest(lastTimeInHours);

        return new UsersLastTestReport(usersLastTest);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private class UsersLastTestReport {

        @JsonProperty("users_count")
        private int usersCount;

        @JsonProperty("missing_users_count")
        private int missingUsersCount;

        private List<String> usersLastTests;
        private List<String> missingUsers;

        public UsersLastTestReport(UsersLastTest usersLastTest) {
            this.usersCount = usersLastTest.getUsersCount();
            this.missingUsersCount = usersLastTest.getMissingUsersCount();

            this.usersLastTests =
                    usersLastTest.getUsersLastTests()
                                 .stream()
                                 .map(LastUserTest::toString)
                                 .map(userStr -> userStr.substring(0, userStr.indexOf(", phone")))
                                 .collect(Collectors.toList());

            this.missingUsers =
                    usersLastTest.getMissingUsers()
                                 .stream()
                                 .map(LastUserTest::toString)
                                 .map(userStr -> userStr.substring(0, userStr.indexOf(", phone")))
                                 .collect(Collectors.toList());
        }
    }
}
