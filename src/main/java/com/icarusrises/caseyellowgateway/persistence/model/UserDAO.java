package com.icarusrises.caseyellowgateway.persistence.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String userName;

    private String encodedPassword;
    private boolean enabled;
    private long dateCreated;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<RoleDAO> roles;

    public UserDAO() {
        this(null);
    }

    public UserDAO(String userName) {
        this(userName, null);
    }

    public UserDAO(String userName, String encodedPassword) {
        this.userName = userName;
        this.encodedPassword = encodedPassword;
        this.enabled = true;
        this.dateCreated = System.currentTimeMillis();
        this.roles = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<RoleDAO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDAO> roles) {
        this.roles = roles;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "UserDAO{" +
                "userName='" + userName + '\'' +
                ", encodedPassword='" + encodedPassword + '\'' +
                ", enabled=" + enabled +
                ", dateCreated=" + dateCreated +
                ", roles=" + roles +
                '}';
    }
}
