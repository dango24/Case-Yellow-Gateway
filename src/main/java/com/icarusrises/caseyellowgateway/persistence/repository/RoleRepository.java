package com.icarusrises.caseyellowgateway.persistence.repository;

import com.icarusrises.caseyellowgateway.persistence.model.RoleDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepository extends JpaRepository<RoleDAO, Long> {

    String SET_ACTIVE = "UPDATE RoleDAO r set r.active = :active WHERE r.role = :role";

    @Modifying
    @Transactional
    @Query(SET_ACTIVE)
    void updateRole(@Param("role")String role, @Param("active")boolean active);

    RoleDAO findByRole(String role);
}
