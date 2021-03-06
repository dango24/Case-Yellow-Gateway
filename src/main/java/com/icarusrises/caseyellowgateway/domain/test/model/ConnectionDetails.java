package com.icarusrises.caseyellowgateway.domain.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDetails {

    private int speed;
    private String isp;
    private String infrastructure;
}
