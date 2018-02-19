package com.icarusrises.caseyellowgateway.domain.users;

public interface UserService {
    void addUser(String adminToken, String userName, String rawPassword);
}
