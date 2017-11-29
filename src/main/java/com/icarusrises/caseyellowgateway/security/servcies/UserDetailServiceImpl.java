package com.icarusrises.caseyellowgateway.security.servcies;

import com.icarusrises.caseyellowgateway.persistence.model.UserDAO;
import com.icarusrises.caseyellowgateway.persistence.repository.UserRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private Converter<UserDAO, UserDetails> userUserDetailsConverter;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userUserDetailsConverter.convert(userRepository.findByUserName(userName));
    }
}
