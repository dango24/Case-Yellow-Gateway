package com.icarusrises.caseyellowgateway.controllers;

import com.icarusrises.caseyellowgateway.domain.users.UserService;
import com.icarusrises.caseyellowgateway.domain.users.UserSignInDetails;
import com.icarusrises.caseyellowgateway.services.analysis.AnalysisService;
import com.icarusrises.caseyellowgateway.services.central.CentralService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Profile("prod")
@RequestMapping("/gateway")
public class GatewayController {

    private final static String ADMIN_TOKEN = "admin_token";

    private UserService userService;
    private CentralService centralService;
    private AnalysisService analysisService;

    @Autowired
    public GatewayController(UserService userService, CentralService centralService, AnalysisService analysisService) {
        this.userService = userService;
        this.centralService = centralService;
        this.analysisService = analysisService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/health")
    public String health() {
        return "Here we go again";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/analysis-health")
    public String analysisHealth() {
        return analysisService.healthCheck();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/central-health")
    public String centralHealth() {
        return centralService.healthCheck();
    }

    @PutMapping("/add-user")
    public void addUser(@RequestHeader(ADMIN_TOKEN) String adminToken, @RequestBody UserSignInDetails userSignInDetails) {
        log.info(String.format("Received addUser PUT request, for user: %s", userSignInDetails.getUserName()));

        if (!validateUserSignInDetails(userSignInDetails)) {
            throw new IllegalArgumentException(String.format("userSignInDetails is incorrect for user: %s" , userSignInDetails.getUserName()));
        }

        userService.addUser(adminToken, userSignInDetails);
    }

    private boolean validateUserSignInDetails(UserSignInDetails userSignInDetails) {
        return StringUtils.isNotEmpty(userSignInDetails.getUserName())  &&
               StringUtils.isNotEmpty(userSignInDetails.getRawPassword());
    }
}

