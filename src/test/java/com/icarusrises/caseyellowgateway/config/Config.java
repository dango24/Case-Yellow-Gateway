package com.icarusrises.caseyellowgateway.config;

import com.icarusrises.caseyellowgateway.domain.users.UserService;
import com.icarusrises.caseyellowgateway.persistence.model.UserDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
public class Config {

    @Bean
    @Profile("dev")
    public UserService userService() {
        return new UserService() {
            @Override
            public void addUser(String adminToken, String userName, String rawPassword) {

            }

            @Override
            public List<UserDAO> getAllUsers() {
                return null;
            }
        };
    }
}
