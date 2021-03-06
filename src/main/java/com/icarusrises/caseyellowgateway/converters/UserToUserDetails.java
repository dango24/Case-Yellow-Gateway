package com.icarusrises.caseyellowgateway.converters;

import com.icarusrises.caseyellowgateway.persistence.model.UserDAO;
import com.icarusrises.caseyellowgateway.security.model.UserDetailsImpl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
public class UserToUserDetails implements Converter<UserDAO, UserDetails> {

    @Override
    public UserDetails convert(UserDAO userDAO) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUserName(userDAO.getUserName());
        userDetails.setPassword(userDAO.getEncodedPassword());
        userDetails.setEnabled(userDAO.isEnabled());
        userDetails.setAuthorities(userDAO.getRoles()
                                          .stream()
                                          .map(role -> new SimpleGrantedAuthority(role.getRole()))
                                          .collect(toList()));

        return userDetails;
    }
}
