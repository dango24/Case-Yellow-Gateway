package com.icarusrises.caseyellowgateway.domain.webSite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role {

    private String identifier;
    private Command command;
    private boolean executed;
    private boolean mono;
    private boolean deprecated;

    public Role() {
        this.executed = false;
    }

    public Role(String identifier, Command command, boolean mono) {
        this.identifier = identifier;
        this.command = command;
        this.mono = mono;
        this.executed = false;
    }
}
