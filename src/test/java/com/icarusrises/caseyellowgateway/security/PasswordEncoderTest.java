package com.icarusrises.caseyellowgateway.security;

import com.icarusrises.caseyellowgateway.CaseYellowGatewayApplication;
import com.icarusrises.caseyellowgateway.persistence.model.UserDAO;
import com.icarusrises.caseyellowgateway.persistence.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaseYellowGatewayApplication.class)
public class PasswordEncoderTest {

    private static final String USER_NAME = "dango";
    private static final String RAW_PASSWORD = "EizeEfes";

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    public void encodedLength2() {
        String pass = "00";
        String passEncoded = passwordEncoder.encode(pass);

        assertTrue(passEncoded.length() == 60);
    }

    @Test
    public void encodedLengthPassLength8() {
        String pass = "EizeEfes";
        String passEncoded = passwordEncoder.encode(pass);

        assertTrue(passEncoded.length() == 60);
    }

    @Test
    public void encodedLengthPassLength16() {
        String pass = "EizeEfesEizeEfes";
        String passEncoded = passwordEncoder.encode(pass);

        assertTrue(passEncoded.length() == 60);
    }

    @Test
    public void receiveEncodedPass() {
        String passEncoded = passwordEncoder.encode(RAW_PASSWORD);

        userRepository.save(new UserDAO(USER_NAME, passEncoded));
        assertTrue(passwordEncoder.matches(RAW_PASSWORD, userRepository.findByUserName(USER_NAME).getEncodedPassword()));
    }
}
