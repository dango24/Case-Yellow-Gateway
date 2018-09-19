package com.icarusrises.caseyellowgateway.controllers;

import com.icarusrises.caseyellowgateway.domain.users.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Profile("prod")
@RequestMapping("/gateway")
public class GatewayController {

    private final static String ADMIN_TOKEN = "admin_token";

    private UserService userService;

    @Autowired
    public GatewayController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/health")
    public String health() {
        return "For fame and glory";
    }

    @PutMapping("/add-user")
    public void addUser(@RequestHeader(ADMIN_TOKEN) String adminToken, @RequestBody UserSignInDetails userSignInDetails) {
        log.info(String.format("Received addUser PUT request, for user: %s", userSignInDetails.getUserName()));

        if (!validUserSignInDetails(userSignInDetails)) {
            throw new IllegalArgumentException(String.format("userSignInDetails is incorrect for user: %s" , userSignInDetails.getUserName()));
        }

        userService.addUser(adminToken, userSignInDetails.getUserName(), userSignInDetails.getRawPassword());
    }

    private boolean validUserSignInDetails(UserSignInDetails userSignInDetails) {
        return StringUtils.isNotEmpty(userSignInDetails.getUserName())  &&
               StringUtils.isNotEmpty(userSignInDetails.getRawPassword());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class UserSignInDetails {

        private String userName;
        private String rawPassword;
    }
}

