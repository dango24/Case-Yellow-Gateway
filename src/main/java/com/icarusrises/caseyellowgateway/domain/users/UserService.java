package com.icarusrises.caseyellowgateway.domain.users;

import com.icarusrises.caseyellowgateway.persistence.model.UserDAO;

import java.util.List;

public interface UserService {
    void addUser(String adminToken, UserSignInDetails userSignInDetails);
    List<UserDAO> getAllUsers();
    UserDAO getUser(String userName);
}
