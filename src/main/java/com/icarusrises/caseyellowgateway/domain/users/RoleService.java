package com.icarusrises.caseyellowgateway.domain.users;

import com.icarusrises.caseyellowgateway.persistence.model.RoleDAO;
import com.icarusrises.caseyellowgateway.persistence.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.Objects.isNull;

@Component
@ConfigurationProperties
public class RoleService {

    private List<String> roles;
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    private void init() {
        deactivateRoles(); // first deactivated all roles
        activateRoles(); // activate only roles from configuration properties
    }

    private void activateRoles() {
        roles.stream() // Add new roles not found in DB
             .filter(role -> isNull(roleRepository.findByRole(role)))
             .forEach(role -> roleRepository.save(new RoleDAO(role)));

        roles.forEach(role -> roleRepository.updateRole(role, true)); // Active all roles from properties
    }

    private void deactivateRoles() {
        roleRepository.findAll()
                      .stream()
                      .map(RoleDAO::getRole)
                      .forEach(role -> roleRepository.updateRole(role, false));
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
