package com.icarusrises.caseyellowgateway.controllers;

import com.icarusrises.caseyellowgateway.domain.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

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

    private static class UserSignInDetails {

        private String userName;
        private String rawPassword;

        public UserSignInDetails() {
        }

        public UserSignInDetails(String userName, String rawPassword) {
            this.userName = userName;
            this.rawPassword = rawPassword;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getRawPassword() {
            return rawPassword;
        }

        public void setRawPassword(String rawPassword) {
            this.rawPassword = rawPassword;
        }
    }
}

