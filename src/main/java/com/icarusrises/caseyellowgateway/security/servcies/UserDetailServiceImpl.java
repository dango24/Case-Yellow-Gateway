package com.icarusrises.caseyellowgateway.security.servcies;

import com.icarusrises.caseyellowgateway.exceptions.UnknownUserException;
import com.icarusrises.caseyellowgateway.persistence.model.UserDAO;
import com.icarusrises.caseyellowgateway.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private Converter<UserDAO, UserDetails> userUserDetailsConverter;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository, Converter<UserDAO, UserDetails> userUserDetailsConverter) {
        this.userRepository = userRepository;
        this.userUserDetailsConverter = userUserDetailsConverter;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDAO user = userRepository.findByUserName(userName);

        if (isNull(user)) {
            throw new UnknownUserException("User: " + userName + " not exist in the system");
        }

        return userUserDetailsConverter.convert(user);
    }
}
