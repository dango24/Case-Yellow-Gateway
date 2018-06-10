package com.icarusrises.caseyellowgateway.domain.users;

import com.icarusrises.caseyellowgateway.persistence.model.RoleDAO;
import com.icarusrises.caseyellowgateway.persistence.model.UserDAO;
import com.icarusrises.caseyellowgateway.persistence.repository.RoleRepository;
import com.icarusrises.caseyellowgateway.persistence.repository.UserRepository;
import com.timgroup.statsd.StatsDClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Component
@Profile("prod")
public class UserServiceImpl implements UserService {

    private static final String ADMIN_USER = "admin";

    @Value("${tokenAuthenticationKey}")
    private String tokenAuthenticationKey;

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private StatsDClient statsDClient;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           StatsDClient statsDClient) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.statsDClient = statsDClient;
    }

    @PostConstruct
    private void init() {
        if (isNull(userRepository.findByUserName(ADMIN_USER))) {
            addAdminUser();
        }
    }

    @Override
    public void addUser(String adminToken, String userName, String rawPassword) {
        if (!isValidAdminKey(adminToken)) {
            throw new IllegalArgumentException(String.format("Admin token is incorrect"));
        }

        addUser(userName, rawPassword);
    }

    @Override
    public List<UserDAO> getAllUsers() {
        return userRepository.findAll();
    }

    private void addUser(String userName, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        UserDAO userDAO = new UserDAO(userName, encodedPassword);

        RoleDAO userRole = roleRepository.findByRole("USER");
        userDAO.setRoles(Arrays.asList(userRole));

        log.info(String.format("Add user: %s to DB", userName));
        userRepository.save(userDAO);
        statsDClient.increment("user.added");
    }

    private void addAdminUser() {
        String rawPassword = receiveUserInput("admin password");
        String encodedPassword = passwordEncoder.encode(rawPassword);
        UserDAO userDAO = new UserDAO(ADMIN_USER, encodedPassword);

        RoleDAO adminRole = roleRepository.findByRole("ADMIN");
        userDAO.setRoles(Arrays.asList(adminRole));

        log.info("Add Admin user to DB");
        userRepository.save(userDAO);
    }

    public String receiveTokenAuthenticationKeyFromUser() {
        boolean validKey;

        do {
            validKey = isValidAdminKey(tokenAuthenticationKey);

            if (!validKey) {
                System.out.println("Key is incorrect.");
            }

        } while (!validKey);

        return tokenAuthenticationKey;
    }

    private boolean isValidAdminKey(String tokenAuthenticationKey) {
        UserDAO adminUser = userRepository.findByUserName(ADMIN_USER);
        return nonNull(adminUser) && passwordEncoder.matches(tokenAuthenticationKey, adminUser.getEncodedPassword());
    }

    private String receiveUserInput(String output) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter " + output + ": ");

        return scanner.nextLine();
    }

}
