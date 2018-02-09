package com.icarusrises.caseyellowgateway.domain.test.model;

public class UserDetails {

    private String userName;
    private int speed;
    private String isp;
    private String infrastructure;

    public UserDetails() {
    }

    public UserDetails(String user, ConnectionDetails connectionDetails) {
        this(user, connectionDetails.getIsp(), connectionDetails.getInfrastructure(), connectionDetails.getSpeed());
    }

    public UserDetails(String userName, String isp, String infrastructure, int speed) {
        this.userName = userName;
        this.infrastructure = infrastructure;
        this.isp = isp;
        this.speed = speed;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getInfrastructure() {
        return infrastructure;
    }

    public void setInfrastructure(String infrastructure) {
        this.infrastructure = infrastructure;
    }
}
