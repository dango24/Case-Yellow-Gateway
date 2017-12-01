package com.icarusrises.caseyellowgateway.bootstrap;

import com.icarusrises.caseyellowgateway.persistence.model.RoleDAO;
import com.icarusrises.caseyellowgateway.persistence.model.UserDAO;
import com.icarusrises.caseyellowgateway.persistence.repository.RoleRepository;
import com.icarusrises.caseyellowgateway.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@Profile("prod")
public class ProdBootstrap {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostConstruct
    private void init() {
        String userName = "admin";
        String pass = passwordEncoder.encode("pass");

        UserDAO userDAO = new UserDAO(userName, pass);
        RoleDAO adminRole = roleRepository.findByRole("ADMIN");
        RoleDAO userRole = roleRepository.findByRole("USER");
        userDAO.setRoles(Arrays.asList(adminRole, userRole));

        System.out.println(userDAO);
        userRepository.save(userDAO);

        UserDAO user = userRepository.findByUserName("dan");

        System.out.println(userDAO);
    }

}
