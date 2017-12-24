package com.icarusrises.caseyellowgateway.bootstrap;

import com.icarusrises.caseyellowgateway.persistence.model.RoleDAO;
import com.icarusrises.caseyellowgateway.persistence.model.UserDAO;
import com.icarusrises.caseyellowgateway.persistence.repository.RoleRepository;
import com.icarusrises.caseyellowgateway.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Scanner;

import static java.util.Objects.isNull;

@Component
@Profile("prod")
public class ProdBootstrap {

    private static final String ADMIN_USER = "admin";

    @Value("${tokenAuthenticationKey}")
    private String tokenAuthenticationKey;

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ProdBootstrap(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void init() {
        if (isNull(userRepository.findByUserName(ADMIN_USER))) {
            addAdminUser();
        }
    }

    private void addUser(String userName, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        UserDAO userDAO = new UserDAO(userName, encodedPassword);

        RoleDAO adminRole = roleRepository.findByRole("USER");
        userDAO.setRoles(Arrays.asList(adminRole));

        userRepository.save(userDAO);
    }

    private void addAdminUser() {
        String rawPassword = receiveUserInput("admin password");
        String encodedPassword = passwordEncoder.encode(rawPassword);
        UserDAO userDAO = new UserDAO(ADMIN_USER, encodedPassword);

        RoleDAO adminRole = roleRepository.findByRole("ADMIN");
        userDAO.setRoles(Arrays.asList(adminRole));

        userRepository.save(userDAO);
    }

    public String receiveTokenAuthenticationKeyFromUser() {
        boolean validKey;
        UserDAO adminUser;
//        String tokenAuthenticationKey;

        do {
//            tokenAuthenticationKey = receiveUserInput("token authentication key");
            adminUser = userRepository.findByUserName(ADMIN_USER);
            validKey = passwordEncoder.matches(tokenAuthenticationKey, adminUser.getEncodedPassword());

            if (!validKey) {
                System.out.println("Key is incorrect.");
            }

        } while (!validKey);

        return tokenAuthenticationKey;
    }

    private String receiveUserInput(String output) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter " + output + ": ");

        return scanner.nextLine();
    }

}
