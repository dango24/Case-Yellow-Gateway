package com.icarusrises.caseyellowgateway.persistence.repository;


import com.icarusrises.caseyellowgateway.persistence.model.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDAO, Long> {

    UserDAO findByUserName(String userName);
}
