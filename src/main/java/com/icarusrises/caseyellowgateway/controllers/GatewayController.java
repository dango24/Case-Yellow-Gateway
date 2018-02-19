package com.icarusrises.caseyellowgateway.controllers;

import com.icarusrises.caseyellowgateway.domain.users.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    private Logger logger = Logger.getLogger(GatewayController.class);

    private final static String ADMIN_TOKEN = "admin_token";

    private UserService userService;

    @Autowired
    public GatewayController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/add-user")
    public void addUser(@RequestHeader(ADMIN_TOKEN) String adminToken, @RequestBody UserSignInDetails userSignInDetails) {
        logger.info(String.format("Received addUser PUT request, for user: %s", userSignInDetails.getUserName()));

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

